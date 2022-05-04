package numista;

import numismat.entity.pieces.DescriptionTitle;
import numismat.entity.pieces.PieceRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.ArrayList;

public class NumistaLoader {

    public static void main(String[] args){
//        NumistaPiece numistaPiece = loadNumistaPiece("https://en.numista.com/catalogue/pieces43.html");
        NumistaPiece numistaPiece = loadNumistaPiece("https://en.numista.com/catalogue/pieces2087.html");

        System.out.println(numistaPiece);
    }

    public static NumistaPiece loadNumistaPiece(String url){
        NumistaPiece numistaPiece = new NumistaPiece();

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        //Name
        numistaPiece.name = doc.selectFirst("#main_title").selectFirst("h1").text();


        //Type
        Elements main_breadcrumb = doc.selectFirst("#main_breadcrumb").children();
        ArrayList<String> mainBreadcrumbArray = new ArrayList<>();
        for(Element span : main_breadcrumb){
            mainBreadcrumbArray.add(span.selectFirst("span[itemprop=\"name\"]").text());
        }

        if(mainBreadcrumbArray.get(1).equals("Coins")){
            numistaPiece.pieceType = NumistaPiece.PieceType.Coin;
        } else if(mainBreadcrumbArray.get(1).equals("Banknotes")){
            numistaPiece.pieceType = NumistaPiece.PieceType.Banknote;
        } else if(mainBreadcrumbArray.get(1).equals("Exonumia")){
            numistaPiece.pieceType = NumistaPiece.PieceType.Exonumia;
        }

        //Features
        Elements ficheCaracteristiques = doc.select("#fiche_caracteristiques");
        for (Element element : ficheCaracteristiques) {
            Elements rows = element.select("tr");
            for (Element row : rows){
                numistaPiece.mainProperties.put(row.select("th").text(), row.select("td").text());
                if(row.select("th").text().equals(NumistaPiece.MainFeatureName.Value.toString())){
                    Element first_td = row.select("td").first();
                    if(first_td != null && first_td.textNodes().size() > 0) {
                        numistaPiece.mainProperties.put(row.select("th").text(), first_td.textNodes().get(0).text());
                    }
                }
            }
        }

        // set Territories

        //todo from 2 till size Russia - Russia

        for(int i = 2; i < mainBreadcrumbArray.size() - 1; i++){
            numistaPiece.addTerritory(mainBreadcrumbArray.get(i));
        }


        //Photo
        Elements fichePhotos = doc.select("#fiche_photo");
        for (Element element : fichePhotos) {
            Elements hrefs = element.select("[href]");
            for (Element href : hrefs){

                if(href.select("img").attr("alt").contains("obverse")){
                    numistaPiece.obversePhotoLink = href.select("a[href]").attr("href");
                } else if(href.select("img").attr("alt").contains("reverse"))
                    numistaPiece.reversePhotoLink = href.select("a[href]").attr("href");
            }
        }

        //Description
        Element ficheDescriptions = doc.selectFirst("#fiche_descriptions");

        String currentTitle = "";
        StringBuilder description = new StringBuilder();
        ArrayList<String> photoLinks = new ArrayList<>();
        StringBuilder mentions = new StringBuilder();

        for (Element element : ficheDescriptions.children()) {
            if(element.className().equals("tooltip")) continue;
            if(element.text().equals("") && !element.tag().equals(Tag.valueOf("a"))) continue;

            if(element.className().equals("mentions")) {
                mentions.append((mentions.length() == 0) ? element.text() : "|" + element.text());
                continue;
            }

            if(element.tag().equals(Tag.valueOf("h3")) || element.equals(ficheDescriptions.children().last())){
                NumistaPiece.DescriptionItem descriptionItem = new NumistaPiece.DescriptionItem();
                descriptionItem.addText(description.toString());
                descriptionItem.photoLinks = photoLinks;
                descriptionItem.mentions = mentions.toString();
                numistaPiece.descriptionHashMap.put(currentTitle, descriptionItem);


                currentTitle = element.text();
                description = new StringBuilder();
                photoLinks = new ArrayList<>();
                mentions = new StringBuilder();
                continue;
            }

            if(currentTitle.equals(DescriptionTitle.MINTS) && element.tag().equals(Tag.valueOf("table")) && element.id().equals("fiche_mint")){
                Elements elements = element.select("tr");
                for(Element elem : elements){
                    description.append(description.length() != 0 ? "\n" + elem.text() : elem.text());
                }
                continue;
            }

            if(currentTitle.equals(DescriptionTitle.COMMENTS)){
                if(element.id().equals("fiche_comments")){
                    description.append(description.length() != 0 ? "\n" + element.text() : element.text());

                    Elements comments = element.children();

                    for(Element comment : comments){
                        if(comment.tag().equals(Tag.valueOf("a"))){
                            photoLinks.add(comment.attr("href"));
                        }
                        if(comment.tag().equals(Tag.valueOf("img"))){
                            photoLinks.add(comment.attr("src"));
                        }
//                        else {
//                            description.append(description.length() != 0 ? "\n" + comment.text() : comment.text());
//                        }
                    }
                }
            } else {
                if(element.tag().equals(Tag.valueOf("a"))){
                    photoLinks.add(element.attr("href"));
                } else {
                    description.append(description.length() != 0 ? "\n" + element.text() : element.text());
                }
            }

        }



        //Collection
        Elements tbodies = doc.select("table.collection").select("tbody");
        for (Element tbody : tbodies){
            if(tbody.attr("style").contains("display:none")){
                continue;
            }

            Element tr = tbody.selectFirst("tr.date_row");
            if(tr != null){
                Elements tds = tr.select("td");
                String date = "";
                String tirage = "";
                String comment = "";
                for(Element td : tds){
                    if(td.className().contains("date")){
                        date = td.text();
                    } else if(td.className().contains("tirage")){
                        tirage = td.text();
                    } else if(td.className().contains("comment")){
                        comment = td.text();
                    }
                }
                numistaPiece.addCollectionItem(date, tirage, comment);
            }
        }

        System.out.println(numistaPiece);
        return numistaPiece;
    }

}

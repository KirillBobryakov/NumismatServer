package numista;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class NumistaLoader {

    public static void main(String[] args){
        NumistaPiece numistaPiece = new NumistaPiece();


        Document doc = null;
        try {
            doc = Jsoup.connect("https://en.numista.com/catalogue/pieces212484.html").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Type
        Elements main_breadcrumb = doc.selectFirst("#main_breadcrumb").children();
        ArrayList<String> mainBreadcrumbArray = new ArrayList<>();
        for(Element span : main_breadcrumb){
            mainBreadcrumbArray.add(span.selectFirst("span[itemprop=\"name\"]").text());
        }

        if(mainBreadcrumbArray.get(1).equals(NumistaPiece.PieceType.Coin)){
            numistaPiece.pieceType = NumistaPiece.PieceType.Coin;
        } else if(mainBreadcrumbArray.get(1).equals(NumistaPiece.PieceType.Banknote)){
            numistaPiece.pieceType = NumistaPiece.PieceType.Banknote;
        } else if(mainBreadcrumbArray.get(1).equals(NumistaPiece.PieceType.Exonumia)){
            numistaPiece.pieceType = NumistaPiece.PieceType.Exonumia;
        }

        //Features
        Elements ficheCaracteristiques = doc.select("#fiche_caracteristiques");
        for (Element element : ficheCaracteristiques) {
            Elements rows = element.select("tr");
            for (Element row : rows){
                numistaPiece.mainProperties.put(row.select("th").text(), row.select("td").text());
            }
        }

        // set Territories

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

        NumistaPiece.DescriptionTitle currentTitle = null;
        StringBuilder description = new StringBuilder();
        ArrayList<String> photoLinks = new ArrayList<>();

        for (Element element : ficheDescriptions.children()) {
            if(currentTitle != null){
                if(currentTitle.equals(NumistaPiece.DescriptionTitle.Comments)){
                    if(element.id().equals("fiche_comments")){
                        description.append(description.length() != 0 ? "\n" + element.text() : element.text());

                        Elements comments = element.children();

                        for(Element comment : comments){
                            if(comment.tag().equals(Tag.valueOf("a"))){
                                photoLinks.add(comment.attr("href"));
                            } else {
                                description.append(description.length() != 0 ? "\n" + comment.text() : comment.text());
                            }
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

            if(element.tag().equals(Tag.valueOf("h3")) || element.equals(ficheDescriptions.children().last())){
                if(currentTitle != null){
                    NumistaPiece.DescriptionItem descriptionItem = new NumistaPiece.DescriptionItem();
                    descriptionItem.addText(description.toString());
                    descriptionItem.photoLinks = photoLinks;
                    numistaPiece.descriptionHashMap.put(currentTitle, descriptionItem);
                }


                if(element.text().equals(NumistaPiece.DescriptionTitle.Commemorative_issue.toString())){
                    currentTitle = NumistaPiece.DescriptionTitle.Commemorative_issue;
                } else if(element.text().equals(NumistaPiece.DescriptionTitle.Obverse.toString())){
                    currentTitle = NumistaPiece.DescriptionTitle.Obverse;
                } else if(element.text().equals(NumistaPiece.DescriptionTitle.Reverse.toString())){
                    currentTitle = NumistaPiece.DescriptionTitle.Reverse;
                } else if(element.text().equals(NumistaPiece.DescriptionTitle.Edge.toString())){
                    currentTitle = NumistaPiece.DescriptionTitle.Edge;
                } else if(element.text().equals(NumistaPiece.DescriptionTitle.Watermark.toString())){
                    currentTitle = NumistaPiece.DescriptionTitle.Watermark;
                } else if(element.text().equals(NumistaPiece.DescriptionTitle.Mints.toString())){
                    currentTitle = NumistaPiece.DescriptionTitle.Mints;
                } else if(element.text().equals(NumistaPiece.DescriptionTitle.Comments.toString())){
                    currentTitle = NumistaPiece.DescriptionTitle.Comments;
                }
                description = new StringBuilder();
                photoLinks = new ArrayList<>();
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
                System.out.println("!: " + tr);

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
    }

}

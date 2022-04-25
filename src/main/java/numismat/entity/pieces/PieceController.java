package numismat.entity.pieces;

import numismat.entity.*;
import numista.NumistaPiece;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/pieces")
public class PieceController {


    private final PieceRepository pieceRepository;
    private final TerritoryRepository territoryRepository;
    private final IssuerRepository issuerRepository;
    private final PeriodOfIssueRepository periodOfIssueRepository;
    private final CurrencyRepository currencyRepository;
    private final DescriptionRepository descriptionRepository;

    public PieceController(PieceRepository pieceRepository,
                           TerritoryRepository territoryRepository,
                           IssuerRepository issuerRepository,
                           PeriodOfIssueRepository periodOfIssueRepository,
                           CurrencyRepository currencyRepository,
                           DescriptionRepository descriptionRepository) {
        this.pieceRepository = pieceRepository;
        this.territoryRepository = territoryRepository;
        this.issuerRepository = issuerRepository;
        this.periodOfIssueRepository = periodOfIssueRepository;
        this.currencyRepository = currencyRepository;
        this.descriptionRepository = descriptionRepository;
    }

    @GetMapping("/loadfromnumista/{from}/{till}")
    public boolean loadFromNumista(@PathVariable int from, @PathVariable int till){

        for (int i = from; i <= till; i++){
            loadFromNumista(i);
        }

        return true;
    }


    @GetMapping("/loadfromnumista/{number}")
    public boolean loadFromNumista(@PathVariable int number){
        String url = "https://en.numista.com/catalogue/pieces"+number+".html";

        NumistaPiece numistaPiece = loadNumistaPiece(url);

        if(numistaPiece == null) {
            System.out.println("NumistaPiece with URL:" + url + " can't be loaded");
            return false;
        }

        Piece piece = null;

        // Set PieceType
        if(numistaPiece.pieceType.equals(NumistaPiece.PieceType.Coin)){
            piece = new PieceCoin();
        } else if(numistaPiece.pieceType.equals(NumistaPiece.PieceType.Banknote)){
            piece = new PieceBanknote();
        } else if(numistaPiece.pieceType.equals(NumistaPiece.PieceType.Exonumia)){
            piece = new PieceExonumia();
        } else {
            piece = new Piece();
        }

        // Set NumistaURL
        piece.setNumistaURL(url);

        // Set Territory
        NumistaPiece.Territory numistaTerritory = numistaPiece.territory;
        Territory lastTerritory = null;
        while (numistaTerritory != null){
            lastTerritory = territoryRepository.findByName(numistaTerritory.name).block();
            if(lastTerritory == null) {
//                lastTerritory = territoryRepository.save(new Territory(numistaTerritory.name)).block();
                lastTerritory = new Territory(numistaTerritory.name);
            }
            numistaTerritory = numistaTerritory.child;
        }

        assert lastTerritory != null;

        piece.setTerritory(lastTerritory);

        // Main Features

        // Looking for Period, Queen, King feature. Must be only one!?
        boolean catchPeriod = false;


        for(String key : new ArrayList<>(numistaPiece.mainProperties.keySet())){
            String value = numistaPiece.mainProperties.get(key);
            if(NumistaPiece.MainFeatureName.Issuer.toString().equals(key)){ //Issuer
                Issuer issuer = issuerRepository.findByName(value).block();
                if(issuer == null){
//                    issuer = issuerRepository.save(new Issuer(value)).block();
                    issuer = new Issuer(value);
                }
                piece.setIssuer(issuer);
            } else if(NumistaPiece.MainFeatureName.Period.toString().equals(key) || //PeriodOfIssue
                    NumistaPiece.MainFeatureName.King.toString().equals(key) ||
                    NumistaPiece.MainFeatureName.Queen.toString().equals(key)) {
                if(catchPeriod) {
                    System.out.println("Find second feature for PeriodOfIssue: " + key);
                } else {
                    PeriodOfIssue periodOfIssue = periodOfIssueRepository.findByName(value).block();
                    if(periodOfIssue == null){
//                        periodOfIssue = periodOfIssueRepository.save(new PeriodOfIssue(value)).block();
                        periodOfIssue = new PeriodOfIssue(value);
                    }
                    piece.setPeriodOfIssue(periodOfIssue);
                    catchPeriod = true;
                }
            } else if(NumistaPiece.MainFeatureName.Currency.toString().equals(key)) { //IssueType
                Currency currency = currencyRepository.findByName(value).block();
                if(currency == null){
//                    currency = currencyRepository.save(new Currency(value)).block();
                    currency = new Currency(value);
                }
                piece.setCurrency(currency);
            } else if(NumistaPiece.MainFeatureName.Type.toString().equals(key)) { //IssueType
                switch (value){
                    case IssueType.STANDARD_CIRCULATION_COIN: piece.setIssueType(IssueType.STANDARD_CIRCULATION_COIN); break;
                    case IssueType.NON_CIRCULATING_COIN: piece.setIssueType(IssueType.NON_CIRCULATING_COIN); break;
                    case IssueType.CIRCULATING_COMMEMORATIVE_COIN: piece.setIssueType(IssueType.CIRCULATING_COMMEMORATIVE_COIN); break;
                    case IssueType.PATTERN: piece.setIssueType(IssueType.PATTERN); break;
                    case IssueType.STANDARD_BANKNOTE: piece.setIssueType(IssueType.STANDARD_BANKNOTE); break;
                    case IssueType.COMMEMORATIVE_NOTE: piece.setIssueType(IssueType.COMMEMORATIVE_NOTE); break;
                    case IssueType.LOCAL_BANKNOTE: piece.setIssueType(IssueType.LOCAL_BANKNOTE); break;
                    default:
                        System.out.println("New IssueType found: " + value);
                        break;
                }
            } else if(NumistaPiece.MainFeatureName.Value.toString().equals(key)){
                piece.setValue(value);
            } else if(NumistaPiece.MainFeatureName.Year.toString().equals(key)){
                piece.setYear(value);
            } else if(NumistaPiece.MainFeatureName.Years.toString().equals(key)){
                piece.setYears(value);
            } else if(NumistaPiece.MainFeatureName.Composition.toString().equals(key)){
                piece.setComposition(value);
            } else if(NumistaPiece.MainFeatureName.Weight.toString().equals(key)){
                piece.setWeight(value);
            } else if(NumistaPiece.MainFeatureName.Diameter.toString().equals(key)){
                piece.setDiameter(value);
            } else if(NumistaPiece.MainFeatureName.Thickness.toString().equals(key)){
                piece.setThickness(value);
            } else if(NumistaPiece.MainFeatureName.Size.toString().equals(key)){
                piece.setSize(value);
            } else if(NumistaPiece.MainFeatureName.Shape.toString().equals(key)){
                piece.setShape(value);
            } else if(NumistaPiece.MainFeatureName.Technique.toString().equals(key)){
                piece.setTechnique(value);
            } else if(NumistaPiece.MainFeatureName.Orientation.toString().equals(key)){
                piece.setOrientation(value);
            } else if(NumistaPiece.MainFeatureName.Demonetized.toString().equals(key)){
                piece.setDemonetized(value);
            } else if(NumistaPiece.MainFeatureName.NumistaNumber.toString().equals(key)){
                piece.setNumistaNumber(value);
            } else if(NumistaPiece.MainFeatureName.References.toString().equals(key)){
                piece.setReferences(value);
            } else {
                System.out.println("Some unknown feature: " + key + " = " + value);
            }
        }


        // Descriptions (Obverse, Revers, Edge, Commemorative issue, Watermark, Mints, Comments

        for(String key : new ArrayList<>(numistaPiece.descriptionHashMap.keySet())){
            NumistaPiece.DescriptionItem value = numistaPiece.descriptionHashMap.get(key);
            Description description;
            if(DescriptionTitle.COMMEMORATIVE_ISSUE.equals(key)){
                description = new Description(DescriptionTitle.COMMEMORATIVE_ISSUE, value.text);
            } else if(DescriptionTitle.OBVERSE.equals(key)){
                description = new Description(DescriptionTitle.OBVERSE, value.text);
                description.addPhoto(new Photo(numistaPiece.obversePhotoLink));
            } else if(DescriptionTitle.REVERSE.equals(key)){
                description = new Description(DescriptionTitle.REVERSE, value.text);
                description.addPhoto(new Photo(numistaPiece.reversePhotoLink));
            } else if(DescriptionTitle.EDGE.equals(key)){
                description = new Description(DescriptionTitle.EDGE, value.text);
            } else if(DescriptionTitle.WATERMARK.equals(key)){
                description = new Description(DescriptionTitle.WATERMARK, value.text);
            } else if(DescriptionTitle.MINTS.equals(key)){
                description = new Description(DescriptionTitle.MINTS, value.text);
            } else if(DescriptionTitle.COMMENTS.equals(key)){
                description = new Description(DescriptionTitle.COMMENTS, value.text);
            } else {
                continue;
            }

            for(String photoLink : value.photoLinks){
                description.addPhoto(new Photo(photoLink));
            }
//            description = descriptionRepository.save(description).block();
            piece.addDescription(description);
        }

        piece = pieceRepository.save(piece).block();

        return true;
    }





    private NumistaPiece loadNumistaPiece(String url){

        NumistaPiece numistaPiece = new NumistaPiece();


        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

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
                if(row.select("th").text().equals("Value")){
                    Element first_td = row.select("td").first();
                    if(first_td != null && first_td.textNodes().size() > 0) {
                        numistaPiece.mainProperties.put(row.select("th").text(), first_td.textNodes().get(0).text());
                    }
                }
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

        String currentTitle = null;
        StringBuilder description = new StringBuilder();
        ArrayList<String> photoLinks = new ArrayList<>();

        for (Element element : ficheDescriptions.children()) {
            if(currentTitle != null){
                if(currentTitle.equals(DescriptionTitle.COMMENTS)){
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

                currentTitle = element.text();
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

        return numistaPiece;
    }

}
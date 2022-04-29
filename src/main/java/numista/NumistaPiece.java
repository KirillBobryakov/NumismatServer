package numista;

import numismat.entity.pieces.DescriptionTitle;
import numismat.entity.pieces.NumistaCollectionItem;
import numismat.entity.pieces.PieceRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class NumistaPiece {

    public enum PieceType {
        Coin,
        Banknote,
        Exonumia
    }
//
//
//    public enum DescriptionTitle {
//        Commemorative_issue("Commemorative issue", 1),
//        Obverse("Obverse", 2),
//        Reverse("Reverse", 3),
//        Edge("Edge", 4),
//        Watermark("Watermark", 5),
//        Mints("Mints", 6),
//        Comments("Comments", 7);
//
//        private final String title;
//        private final int code;
//
//        DescriptionTitle(String s, int c) {
//            this.title = s;
//            this.code = c;
//        }
//
//        @Override
//        public String toString() {
//            return title;
//        }
//
//        public int getCode() {
//            return code;
//        }
//    }

    public enum MainFeatureName {
        Issuer("Issuer"),
        King("King"),
        Queen("Queen"),
        Period("Period"),
        Currency("Currency"),
        Type("Type"),
        Year("Year"),
        Years("Years"),
        Value("Value"),
        Composition("Composition"),
        Weight("Weight"),
        Diameter("Diameter"),
        Thickness("Thickness"),
        Size("Size"),
        Shape("Shape"),
        Technique("Technique"),
        Orientation("Orientation"),
        Demonetized("Demonetized"),
        NumistaNumber("Number"),
        References("References");

        private final String name;

        MainFeatureName(String s) { name = s; }

        @Override
        public String toString() { return name; }
    }

    public String name;
    public PieceType pieceType;
    public Territory territory;
    public HashMap<String, String> mainProperties = new HashMap<>();
    public String obversePhotoLink = "";
    public String reversePhotoLink = "";
    public HashMap<String, DescriptionItem> descriptionHashMap = new HashMap<>();
    public ArrayList<NumistaCollectionItem> collection = new ArrayList<>();



    public void addCollectionItem(String date, String tirage, String comment){
        collection.add(new NumistaCollectionItem(date, tirage, comment));
    }

    public void addTerritory(String territory){
        Territory lastTerritory = getLastTerritory(this.territory);

        if(lastTerritory == null){
            this.territory = new Territory(territory);
        } else {
            lastTerritory.child = new Territory(territory);
        }
    }

    public Territory getLastTerritory(Territory territory){
        if(territory == null) {
            return null;
        } else {
            if(territory.child == null) {
                return territory;
            } else {
                return getLastTerritory(territory.child);
            }
        }
    }



    public static class DescriptionItem {
        public String text;
        public ArrayList<String> photoLinks = new ArrayList<>();

        public void addText(String text){
            this.text = text;
        }
    }


    public static class Territory {
        public String name;
        public Territory child;

        public Territory(String name) {
            this.name = name;
        }
    }




}



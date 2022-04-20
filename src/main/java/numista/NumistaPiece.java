package numista;

import java.util.ArrayList;
import java.util.HashMap;

public class NumistaPiece {

    public enum PieceType {
        Coin,
        Banknote,
        Exonumia
    }

    public enum DescriptionTitle {
        Commemorative_issue("Commemorative issue"),
        Obverse("Obverse"),
        Reverse("Reverse"),
        Edge("Edge"),
        Watermark("Watermark"),
        Mints("Mints"),
        Comments("Comments");

        private final String title;

        DescriptionTitle(String s) {
            title = s;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    public PieceType pieceType;
    public Territory territory;
    public HashMap<String, String> mainProperties = new HashMap<>();
    public String obversePhotoLink = "";
    public String reversePhotoLink = "";
    public HashMap<DescriptionTitle, DescriptionItem> descriptionHashMap = new HashMap<>();
    public ArrayList<CollectionItem> collection = new ArrayList<>();



    public void addCollectionItem(String date, String tirage, String comment){
        collection.add(new CollectionItem(date, tirage, comment));
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

    private static class CollectionItem {
        public String date = "";
        public String tirage = "";
        public String comment = "";

        public CollectionItem(String date, String tirage, String comment) {
            this.date = date;
            this.tirage = tirage;
            this.comment = comment;
        }
    }

    private static class Territory {
        public String name;
        public Territory child;

        public Territory(String name) {
            this.name = name;
        }
    }


}



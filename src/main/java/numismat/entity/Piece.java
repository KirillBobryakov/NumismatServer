package numismat.entity;


import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Node(labels = "PIECE")
public class Piece {

    @Id
    @GeneratedValue
    private Long id;

    private String numistaURL;

    public String getNumistaURL() {
        return numistaURL;
    }

    public void setNumistaURL(String numistaURL) {
        this.numistaURL = numistaURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Relationship(type = "OBVERSE_IS", direction = Relationship.Direction.OUTGOING)
    private Photo obverse;

    @Relationship(type = "REVERSE_IS", direction = Relationship.Direction.OUTGOING)
    private Photo reverse;


    @Relationship(type = "ISSUED_BY", direction = Relationship.Direction.OUTGOING)
    private Issuer issuer;

    @Relationship(type = "ISSUED_IN", direction = Relationship.Direction.OUTGOING)
    private Territory territory;

    @Relationship(type = "HAS_DESCRIPTION", direction = Relationship.Direction.OUTGOING)
    private Set<Description> descriptions;

    @Relationship(type = "ISSUED_FOR", direction = Relationship.Direction.OUTGOING)
    private PeriodOfIssue periodOfIssue;

    private IssueType issueType;

    @Relationship(type = "LIKE_CURRENCY", direction = Relationship.Direction.OUTGOING)
    private Currency currency;

    private ArrayList<Map<String, String>> features;

    public Photo getObverse() {
        return obverse;
    }

    public void setObverse(Photo obverse) {
        this.obverse = obverse;
    }

    public Photo getReverse() {
        return reverse;
    }

    public void setReverse(Photo reverse) {
        this.reverse = reverse;
    }

    public Issuer getIssuer() {
        return issuer;
    }

    public void setIssuer(Issuer issuer) {
        this.issuer = issuer;
    }

    public Territory getTerritory() {
        return territory;
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;
    }

    public Set<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<Description> descriptions) {
        this.descriptions = descriptions;
    }

    public PeriodOfIssue getPeriodOfIssue() {
        return periodOfIssue;
    }

    public void setPeriodOfIssue(PeriodOfIssue periodOfIssue) {
        this.periodOfIssue = periodOfIssue;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public void setIssueType(IssueType issueType) {
        this.issueType = issueType;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public ArrayList<Map<String, String>> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Map<String, String>> features) {
        this.features = features;
    }

    public enum IssueType {
        Standard_Circulation_Coin("Standard circulation coin"),
        Non_Circulating_Coin("Non-circulating coin"),
        Circulating_Commemorative_Coin("Circulating commemorative coin"),
        Pattern("Pattern"),
        Standard_Banknote("Standard banknote"),
        Commemorative_Note("Commemorative note"),
        Local_Banknote("Local banknote");

        private final String type;

        IssueType(String s) {
            type = s;
        }

        @Override
        public String toString() {
            return type;
        }
    }

    public enum FeatureName {
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
        References("References");

        private final String name;

        FeatureName(String s) {
            name = s;
        }

        @Override
        public String toString() {
            return name;
        }
    }


    public void addDescription(Description description){
        if(descriptions == null){
            descriptions = new HashSet<>();
        }
        descriptions.add(description);
    }


}

package numismat.entity.pieces;


import numismat.entity.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node(labels = "PIECE")
public class Piece {

    @Id
    @GeneratedValue
    private Long id;

    private String numistaURL;

    @Relationship(type = "ISSUED_IN", direction = Relationship.Direction.OUTGOING)
    private Territory territory;

    //Issuer
    @Relationship(type = "ISSUED_BY", direction = Relationship.Direction.OUTGOING)
    private Issuer issuer;

    // Period, King, Queen
    @Relationship(type = "ISSUED_FOR", direction = Relationship.Direction.OUTGOING)
    private PeriodOfIssue periodOfIssue;


    //Currency
    @Relationship(type = "LIKE_CURRENCY", direction = Relationship.Direction.OUTGOING)
    private Currency currency;


    private String issueType;
    private String value;
    private String year;
    private String years;
    private String composition;
    private String weight;
    private String diameter;
    private String thickness;
    private String size;
    private String shape;
    private String technique;
    private String orientation;
    private String demonetized;
    private String numistaNumber;
    private String references;

    @Relationship(type = "HAS_DESCRIPTION", direction = Relationship.Direction.OUTGOING)
    private Set<Description> descriptions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumistaURL() {
        return numistaURL;
    }

    public void setNumistaURL(String numistaURL) {
        this.numistaURL = numistaURL;
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

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }


    public void addDescription(Description description){
        if(descriptions == null){
            descriptions = new HashSet<>();
        }
        descriptions.add(description);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getTechnique() {
        return technique;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getDemonetized() {
        return demonetized;
    }

    public void setDemonetized(String demonetized) {
        this.demonetized = demonetized;
    }

    public String getNumistaNumber() {
        return numistaNumber;
    }

    public void setNumistaNumber(String numistaNumber) {
        this.numistaNumber = numistaNumber;
    }

    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }
}

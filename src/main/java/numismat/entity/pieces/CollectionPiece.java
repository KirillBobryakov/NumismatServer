package numismat.entity.pieces;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("COLLECTION_PIECE")
public class CollectionPiece {

    @Id
    @GeneratedValue
    private Long id;

    private String date;

    private String tirage;

    private String comment;

    public CollectionPiece(String date, String tirage, String comment) {
        this.date = date;
        this.tirage = tirage;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTirage() {
        return tirage;
    }

    public void setTirage(String tirage) {
        this.tirage = tirage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

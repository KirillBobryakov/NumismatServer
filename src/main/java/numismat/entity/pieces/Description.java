package numismat.entity.pieces;


import numismat.entity.Photo;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node(labels = "DESCRIPTION")
public class Description {

    @Id
    @GeneratedValue
    private Long id;


    private String title;
    private String text;
    private String mentions;

    public Description(String title, String text) {
        this.title = title;
        this.text = text;
    }

    @Relationship(type = "HAS_PHOTO", direction = Relationship.Direction.OUTGOING)
    private Set<Photo> photoSet;

    public void addPhoto(Photo photo){
        if (photoSet == null) {
            photoSet = new HashSet<>();
        }
        if(photo != null) {
            photoSet.add(photo);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMentions() {
        return mentions;
    }

    public void setMentions(String mentions) {
        this.mentions = mentions;
    }

    public Set<Photo> getPhotoSet() {
        return photoSet;
    }

    public void setPhotoSet(Set<Photo> photoSet) {
        this.photoSet = photoSet;
    }
}

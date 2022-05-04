package numismat.entity;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node(labels = "PHOTO")
public class Photo {


    @Id
    @GeneratedValue
    private Long id;

    // For example numista.com
    private String source;

    private String link;

//    private String name;
    private String localPath;

//    public Photo(String link) {
//        this.link = link;
//    }

//    public Photo(String source, String link, String localPath) {
//        this.source = source;
//        this.link = link;
//        this.localPath = localPath;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}

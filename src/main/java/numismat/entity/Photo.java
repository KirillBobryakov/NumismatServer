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

    private String name;
    private String localPath;

}

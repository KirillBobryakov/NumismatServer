package numismat.entity;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node(labels = "CURRENCY")
public class Currency {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

}

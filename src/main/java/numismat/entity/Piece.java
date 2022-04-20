package numismat.entity;


import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node(labels = "PIECE")
public class Piece {

    @Id
    @GeneratedValue
    private Long id;


    @Relationship(type = "ISSUED_BY", direction = Relationship.Direction.OUTGOING)
    private Issuer issuer;

    @Relationship(type = "ISSUED_IN", direction = Relationship.Direction.OUTGOING)
    private Territory territory;


}

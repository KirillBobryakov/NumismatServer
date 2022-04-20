package numismat.entity;


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

    @Relationship(type = "OBVERSE_IS", direction = Relationship.Direction.OUTGOING)
    private Photo obverse;
    private Photo reverse;


    @Relationship(type = "ISSUED_BY", direction = Relationship.Direction.OUTGOING)
    private Issuer issuer;

    @Relationship(type = "ISSUED_IN", direction = Relationship.Direction.OUTGOING)
    private Territory territory;

    @Relationship(type = "HAS_DESCRIPTION", direction = Relationship.Direction.OUTGOING)
    private Set<Description> descriptions;

    @Relationship(type = "ISSUED_FOR", direction = Relationship.Direction.OUTGOING)
    private PeriodOfIssue periodOfIssue;





    public void addDescription(Description description){
        if(descriptions == null){
            descriptions = new HashSet<>();
        }
        descriptions.add(description);
    }


}

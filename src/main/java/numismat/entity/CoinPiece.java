package numismat.entity;


import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node(labels = "COIN")
public class CoinPiece extends Piece{

    @Relationship(type = "EDGE_IS", direction = Relationship.Direction.OUTGOING)
    private Photo edge;


}

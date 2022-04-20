package numismat.entity;


import org.springframework.data.neo4j.core.schema.Node;

@Node(labels = "COIN")
public class CoinPiece extends Piece{


}

package numismat.entity.pieces;

import numismat.entity.pieces.Piece;
import org.springframework.data.neo4j.core.schema.Node;

@Node(labels = "BANKNOTE")
public class PieceBanknote extends Piece {

}

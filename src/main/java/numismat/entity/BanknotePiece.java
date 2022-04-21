package numismat.entity;

import org.springframework.data.neo4j.core.schema.Node;

@Node(labels = "BANKNOTE")
public class BanknotePiece extends Piece{

    private String size;

}

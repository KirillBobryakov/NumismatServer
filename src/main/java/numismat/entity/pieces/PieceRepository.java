package numismat.entity.pieces;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;

public interface PieceRepository extends ReactiveNeo4jRepository<Piece, Long> {



}

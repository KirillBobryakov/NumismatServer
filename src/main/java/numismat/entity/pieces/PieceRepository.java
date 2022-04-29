package numismat.entity.pieces;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Mono;

public interface PieceRepository extends ReactiveNeo4jRepository<Piece, Long> {


    Mono<Piece> findByNumistaURL(String numistaURL);

}

package numismat.entity.pieces;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Mono;

public interface CollectionPieceRepository extends ReactiveNeo4jRepository<CollectionPiece, Long> {

    Mono<CollectionPiece> findByDateAndComment(String date, String comment);
}

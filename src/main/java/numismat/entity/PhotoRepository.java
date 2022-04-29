package numismat.entity;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Mono;

public interface PhotoRepository extends ReactiveNeo4jRepository<Photo, Long> {

    Mono<Photo> findByLink(String link);
}

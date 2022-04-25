package numismat.entity;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Mono;

public interface TerritoryRepository extends ReactiveNeo4jRepository<Territory, Long> {

    Mono<Territory> findByName(String name);

}

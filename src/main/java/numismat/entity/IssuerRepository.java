package numismat.entity;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Mono;

public interface IssuerRepository extends ReactiveNeo4jRepository<Issuer, Long> {

    Mono<Issuer> findByName(String name);
}

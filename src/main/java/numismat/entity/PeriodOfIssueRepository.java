package numismat.entity;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Mono;

public interface PeriodOfIssueRepository extends ReactiveNeo4jRepository<PeriodOfIssue, Long> {

    Mono<PeriodOfIssue> findByName(String name);

}

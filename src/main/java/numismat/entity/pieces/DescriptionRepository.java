package numismat.entity.pieces;


import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;

public interface DescriptionRepository extends ReactiveNeo4jRepository<Description, Long> {



}

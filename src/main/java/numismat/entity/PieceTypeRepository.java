package numismat.entity;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PieceTypeRepository extends Neo4jRepository<PieceType, Long> {

    PieceType findByType(String type);


}

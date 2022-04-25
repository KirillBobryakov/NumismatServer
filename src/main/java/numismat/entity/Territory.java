package numismat.entity;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node(labels = "TERRITORY")
public class Territory {


    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Territory(String name) {
        this.name = name;
    }



    @Relationship(type = "LOCATED_IN", direction = Relationship.Direction.OUTGOING)
    private Territory parentTerritory;

    @Relationship(type = "LOCATED_IN", direction = Relationship.Direction.INCOMING)
    private Set<Territory> detailedTerritories;

    public void addDetailedTerritory(Territory detailedTerritory){
        if(detailedTerritories == null){
            detailedTerritories = new HashSet<>();
        }
        detailedTerritories.add(detailedTerritory);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Territory getParentTerritory() {
        return parentTerritory;
    }

    public void setParentTerritory(Territory parentTerritory) {
        this.parentTerritory = parentTerritory;
    }

    public Set<Territory> getDetailedTerritories() {
        return detailedTerritories;
    }

    public void setDetailedTerritories(Set<Territory> detailedTerritories) {
        this.detailedTerritories = detailedTerritories;
    }
}

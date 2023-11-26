package homework.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@NamedEntityGraph(
        name = "character-entity-graph-with-factions",
        attributeNodes = {
                @NamedAttributeNode("weapon"),
                @NamedAttributeNode("name"),
                @NamedAttributeNode("powerLevel"),
                @NamedAttributeNode(value = "factions")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class GameCharacter extends AbstractNamedEntity {

    @ManyToOne
    private Weapon weapon;

    private Integer powerLevel;

    @ManyToMany
    @ToString.Exclude
    private Set<Faction> factions;

    public GameCharacter(Long id, String name, Weapon weapon, Integer powerLevel) {
        this.id = id;
        this.name = name;
        this.weapon = weapon;
        this.powerLevel = powerLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameCharacter that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

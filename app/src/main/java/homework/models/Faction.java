package homework.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Faction extends AbstractNamedEntity {

    private String credo;

    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "factions")
    private Set<GameCharacter> characters;

    public Faction(Long id, String name, String credo) {
        this.id = id;
        this.name = name;
        this.credo = credo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faction faction)) return false;
        return Objects.equals(id, faction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

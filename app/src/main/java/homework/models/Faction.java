package homework.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "faction")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Faction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faction_id")
    private Long id;

    @Column(name = "faction_name")
    private String name;

    @Column(name = "faction_credo")
    private String credo;

    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "factions")
    private Set<GameCharacter> characters;

    public Faction(Long id, String name, String credo) {
        this(id, name, credo, null);
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

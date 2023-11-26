package homework.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Weapon extends AbstractNamedEntity {

    private Integer damage;

    @OneToMany(mappedBy = "weapon")
    @JsonIgnore
    private Set<GameCharacter> characters;

    public Weapon(Long id, String name, Integer damage) {
        this.id = id;
        this.name = name;
        this.damage = damage;
    }
}

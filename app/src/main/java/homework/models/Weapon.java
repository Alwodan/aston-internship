package homework.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "weapon")
public class Weapon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weapon_id")
    private Long id;

    @Column(name = "weapon_name")
    private String name;

    @Column(name = "weapon_damage")
    private Integer damage;

    @OneToMany(mappedBy = "weapon")
    @JsonIgnore
    private List<GameCharacter> characters;

    public Weapon(String name, Integer damage) {
        this.name = name;
        this.damage = damage;
    }

    public Weapon(Long id, String name, Integer damage) {
        this.id = id;
        this.name = name;
        this.damage = damage;
    }
}

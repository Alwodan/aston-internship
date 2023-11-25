package homework.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "gamecharacter")
public class GameCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "character_weapon_id", nullable = false)
    private Weapon weapon;

    @Column(name = "character_name")
    private String name;

    @Column(name = "character_powerlevel")
    private Integer powerLevel;

    @ManyToMany
    @JoinTable(
            name = "character_factions",
            joinColumns = { @JoinColumn(name = "char_id") },
            inverseJoinColumns = { @JoinColumn(name = "fac_id")}
    )
    @ToString.Exclude
    private List<Faction> factions;
}

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
@Table(name = "faction")
public class Faction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "faction_name")
    private String name;
    @ManyToMany(mappedBy = "factions")
    @ToString.Exclude
    private List<GameCharacter> characters;
}

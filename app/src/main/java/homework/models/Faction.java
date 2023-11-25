package homework.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "faction_id")
    private Long id;

    @Column(name = "faction_name")
    private String name;

    @Column(name = "faction_credo")
    private String credo;

    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "factions")
    private List<GameCharacter> characters;

    public Faction(Long id, String name, String credo) {
        this(id, name, credo, null);
    }
}

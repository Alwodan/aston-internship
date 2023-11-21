package homework.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameCharacter {
    private Long id;
    private Long weaponId;
    private String name;
    private Integer powerLevel;
    private List<Long> factionIds;

    public GameCharacter(Long id, Long weaponId, String name, Integer powerLevel) {
        this(id, weaponId, name, powerLevel, null);
    }
}

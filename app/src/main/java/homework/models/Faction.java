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
public class Faction {
    private Long id;
    private String name;
    private List<Long> memberIds;

    public Faction(Long id, String name) {
        this(id, name, null);
    }
}

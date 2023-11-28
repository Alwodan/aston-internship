package homework.controllers.rest;

import homework.dao.Dao;
import homework.dao.GameCharacterDao;
import homework.models.Faction;
import homework.models.GameCharacter;
import homework.models.Weapon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
public class GameCharacterController {
    @Autowired
    GameCharacterDao dao;
    @Autowired
    Dao<Weapon> weaponDao;

    @GetMapping
    public List<GameCharacter> getAll() {
        return dao.getAll();
    }

    @GetMapping("/{id}")
    public GameCharacter getById(@PathVariable("id") String id) {
        return dao.getById(Long.valueOf(id));
    }

    @PostMapping
    public GameCharacter save(@RequestBody GameCharacter newCharacter) {
        GameCharacter saved = dao.save(newCharacter);
        return dao.getById(saved.getId());
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") String id, @RequestBody GameCharacter updatedCharacter) {
        updatedCharacter.setId(Long.valueOf(id));
        dao.update(updatedCharacter);
        return "if id exists, character was updated";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id,
                         @RequestParam(name = "factionId", required = false) String factionId) {
        if (factionId == null) {
            dao.delete(Long.valueOf(id));
            return "if id exists, character was deleted";
        } else {
            dao.leaveFaction(Long.valueOf(id), Long.valueOf(factionId));
            return "if ids exist, character left the faction";
        }
    }

    @PostMapping("/{id}")
    public String enterFaction(@PathVariable("id") String id,
                               @RequestParam(name = "factionId") String factionId) {
        dao.enterFaction(Long.valueOf(id), Long.valueOf(factionId));
        return "if ids exist, character entered the faction";
    }
}

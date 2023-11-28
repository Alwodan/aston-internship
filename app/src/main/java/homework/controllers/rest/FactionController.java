package homework.controllers.rest;

import homework.dao.Dao;
import homework.models.Faction;
import homework.models.Weapon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/factions")
public class FactionController {
    @Autowired
    Dao<Faction> dao;

    @GetMapping
    public List<Faction> getAll() {
        return dao.getAll();
    }

    @GetMapping("/{id}")
    public Faction getById(@PathVariable("id") String id) {
        return dao.getById(Long.valueOf(id));
    }

    @PostMapping
    public Faction save(@RequestBody Faction newFaction) {
        return dao.save(newFaction);
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") String id, @RequestBody Faction updatedFaction) {
        updatedFaction.setId(Long.valueOf(id));
        dao.update(updatedFaction);
        return "if id exists, faction was updated";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        dao.delete(Long.valueOf(id));
        return "if id exists, faction was deleted";
    }
}

package homework.controllers.rest;

import homework.dao.Dao;
import homework.models.Weapon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weapons")
public class WeaponController {
    @Autowired
    Dao<Weapon> dao;

    @GetMapping
    public List<Weapon> getAll() {
        return dao.getAll();
    }

    @GetMapping("/{id}")
    public Weapon getById(@PathVariable("id") String id) {
        return dao.getById(Long.valueOf(id));
    }

    @PostMapping
    public Weapon save(@RequestBody Weapon newWeapon) {
        return dao.save(newWeapon);
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") String id, @RequestBody Weapon updatedWeapon) {
        updatedWeapon.setId(Long.valueOf(id));
        dao.update(updatedWeapon);
        return "if id exists, weapon was updated";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        dao.delete(Long.valueOf(id));
        return "if id exists, weapon was deleted";
    }
}

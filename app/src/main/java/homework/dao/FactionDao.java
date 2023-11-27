package homework.dao;

import homework.models.Faction;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component("factionDao")
public class FactionDao implements Dao<Faction> {
    @Autowired
    private final SessionFactory sf;

    @Override
    public List<Faction> getAll() {
        List<Faction> result = new ArrayList<>();
        sf.inTransaction(session -> {
            result.addAll(session.createSelectionQuery("from Faction", Faction.class)
                    .getResultList());
        });
        return result;
    }

    @Override
    public Faction getById(Long id) {
        Faction result = new Faction();
        sf.inTransaction(session -> {
            Faction dbObject = session.get(Faction.class, id);
            if (dbObject != null) {
                result.setName(dbObject.getName());
                result.setCredo(dbObject.getCredo());
                result.setId(dbObject.getId());
            }
        });
        return result.getId() == null ? null : result;
    }

    @Override
    public Faction save(Faction obj) {
        sf.inTransaction(session -> {
            session.persist(obj);
        });
        return obj;
    }

    @Override
    public void update(Faction faction) {
        sf.inTransaction(session -> {
            Faction dbObject = session.get(Faction.class, faction.getId());
            if (dbObject != null) {
                if (faction.getName() != null) {
                    dbObject.setName(faction.getName());
                }
                if (faction.getCredo() != null) {
                    dbObject.setCredo(faction.getCredo());
                }
            }
        });
    }

    @Override
    public void delete(Long id) {
        sf.inTransaction(session -> {
            Faction dbObject = session.get(Faction.class, id);
            session.remove(dbObject);
        });
    }
}

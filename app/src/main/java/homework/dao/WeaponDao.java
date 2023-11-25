package homework.dao;

import homework.models.Weapon;
import homework.utils.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class WeaponDao implements Dao<Weapon> {
    SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    public List<Weapon> getAll() {
        List<Weapon> result = new ArrayList<>();
        sf.inTransaction(session -> {
            result.addAll(session.createSelectionQuery("from Weapon", Weapon.class)
                    .getResultList());
        });
        return result;
    }

    @Override
    public Weapon getById(Long id) {
        Weapon result = new Weapon();
        sf.inTransaction(session -> {
             Weapon dbObject = session.get(Weapon.class, id);
             if (dbObject != null) {
                 result.setName(dbObject.getName());
                 result.setDamage(dbObject.getDamage());
                 result.setId(dbObject.getId());
             }
        });
        return result.getId() == null ? null : result;
    }

    @Override
    public Weapon save(Weapon obj) {
        sf.inTransaction(session -> {
            session.persist(obj);
        });
        return obj;
    }

    @Override
    public void update(Weapon weapon) {
        sf.inTransaction(session -> {
            Weapon dbObject = session.get(Weapon.class, weapon.getId());
            if (dbObject != null) {
                if (weapon.getName() != null) {
                    dbObject.setName(weapon.getName());
                }
                if (weapon.getDamage() != null) {
                    dbObject.setDamage(weapon.getDamage());
                }
            }
        });
    }

    @Override
    public void delete(Long id) {
        sf.inTransaction(session -> {
            Weapon dbObject = session.get(Weapon.class, id);
            session.remove(dbObject);
        });
    }
}

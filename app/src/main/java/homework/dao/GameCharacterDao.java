package homework.dao;

import homework.models.Faction;
import homework.models.GameCharacter;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class GameCharacterDao implements Dao<GameCharacter> {
    private final SessionFactory sf;

    @Override
    public List<GameCharacter> getAll() {
        List<GameCharacter> result = new ArrayList<>();
        sf.inTransaction(session -> {
            result.addAll(session.createSelectionQuery("from GameCharacter", GameCharacter.class)
                    .getResultList());
        });
        return result;
    }

    @Override
    public GameCharacter getById(Long id) {
        GameCharacter result = new GameCharacter();
        sf.inTransaction(session -> {
            GameCharacter dbObject = session.get(GameCharacter.class, id);
            if (dbObject != null) {
                result.setName(dbObject.getName());
                result.setWeapon(dbObject.getWeapon());
                result.setFactions(dbObject.getFactions());
                result.setPowerLevel(dbObject.getPowerLevel());
                result.setId(dbObject.getId());
            }
        });
        return result.getId() == null ? null : result;
    }

    @Override
    public GameCharacter save(GameCharacter obj) {
        sf.inTransaction(session -> {
            session.persist(obj);
        });
        return obj;
    }

    @Override
    public void update(GameCharacter character) {
        sf.inTransaction(session -> {
            GameCharacter dbObject = session.get(GameCharacter.class, character.getId());
            if (dbObject != null) {
                if (character.getName() != null) {
                    dbObject.setName(character.getName());
                }
                if (character.getPowerLevel() != null) {
                    dbObject.setPowerLevel(character.getPowerLevel());
                }
                if (character.getWeapon() != null) {
                    dbObject.setWeapon(character.getWeapon());
                }
            }
        });
    }

    @Override
    public void delete(Long id) {
        sf.inTransaction(session -> {
            GameCharacter dbObject = session.get(GameCharacter.class, id);
            session.remove(dbObject);
        });
    }

    public void enterFaction(Long characterId, Long factionId) {
        sf.inTransaction(session -> {
            GameCharacter characterFromDb = session.get(GameCharacter.class, characterId);
            Faction factionFromDb = session.get(Faction.class, factionId);
            if (characterFromDb != null && factionFromDb != null) {
                characterFromDb.getFactions().add(factionFromDb);
            }
        });
    }

    public void leaveFaction(Long characterId, Long factionId) {
        sf.inTransaction(session -> {
            GameCharacter characterFromDb = session.get(GameCharacter.class, characterId);
            Faction factionFromDb = session.get(Faction.class, factionId);
            if (characterFromDb != null && factionFromDb != null) {
                characterFromDb.getFactions().remove(factionFromDb);
            }
        });
    }
}

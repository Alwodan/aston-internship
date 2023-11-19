package homework.dao;

import homework.models.Faction;
import homework.models.GameCharacter;
import homework.models.Weapon;
import homework.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FactionDao implements Dao<Faction> {

    public List<Faction> getAll() {
        try (Connection con = DatabaseConnection.retrieveConnection();
             PreparedStatement mainStatement = con.prepareStatement("SELECT * FROM faction ORDER BY faction_id ASC")) {
            List<Faction> result = new ArrayList<>();

            ResultSet rs = mainStatement.executeQuery();
            while (rs.next()) {
                List<Long> ids = new ArrayList<>();

                try (PreparedStatement membersStatement = con.prepareStatement("SELECT char_id FROM character_factions WHERE fac_id = ?")) {
                    membersStatement.setLong(1, rs.getLong("faction_id"));
                    ResultSet rsMembers = membersStatement.executeQuery();
                    while (rsMembers.next()) {
                        ids.add(rsMembers.getLong(1));
                    }
                }

                result.add(new Faction(rs.getLong("faction_id"), rs.getString("faction_name"), ids));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Faction getById(Long id) {
        try (Connection con = DatabaseConnection.retrieveConnection();
             PreparedStatement mainStatement = con.prepareStatement("SELECT * FROM faction WHERE faction_id = ?");
             PreparedStatement membersStatement = con.prepareStatement("SELECT char_id FROM character_factions WHERE fac_id = ?")) {
            mainStatement.setLong(1, id);
            ResultSet rs = mainStatement.executeQuery();
            membersStatement.setLong(1, id);
            ResultSet rsMembers = membersStatement.executeQuery();
            if (rs.next()) {
                List<Long> ids = new ArrayList<>();
                while (rsMembers.next()) {
                    ids.add(rsMembers.getLong(1));
                }
                return new Faction(id, rs.getString("faction_name"), ids);
            } else {
                throw new RuntimeException("No faction with such id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long save(Faction faction) {
        try (Connection con = DatabaseConnection.retrieveConnection();
             PreparedStatement pr = con.prepareStatement("INSERT INTO faction (faction_name) VALUES (?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, faction.getName());
            pr.execute();
            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Faction faction) {
        if (faction.getId() == null) {
            throw new RuntimeException();
        }
        String sql = "UPDATE faction SET faction_name = COALESCE(?, faction_name) WHERE faction_id = ?";
        try (Connection con = DatabaseConnection.retrieveConnection();
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, faction.getName());
            pr.setLong(2, faction.getId());
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        String sql = "DELETE FROM faction WHERE faction_id = ?";
        try (Connection con = DatabaseConnection.retrieveConnection();
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setLong(1, id);
            return pr.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

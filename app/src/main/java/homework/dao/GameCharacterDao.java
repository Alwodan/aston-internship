package homework.dao;

import homework.models.Faction;
import homework.models.GameCharacter;
import homework.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameCharacterDao implements Dao<GameCharacter> {

    @Override
    public List<GameCharacter> getAll() {
        try (Connection con = DatabaseConnection.retrieveConnection();
             PreparedStatement mainStatement = con.prepareStatement("SELECT * FROM gameCharacter ORDER BY character_id ASC")) {
            List<GameCharacter> result = new ArrayList<>();

            ResultSet rs = mainStatement.executeQuery();
            while (rs.next()) {
                List<Long> ids = new ArrayList<>();

                try (PreparedStatement factionsStatement = con.prepareStatement("SELECT fac_id FROM character_factions WHERE char_id = ?")) {
                    factionsStatement.setLong(1, rs.getLong("character_id"));
                    ResultSet rsFactions = factionsStatement.executeQuery();
                    while (rsFactions.next()) {
                        ids.add(rsFactions.getLong(1));
                    }
                }
                result.add(new GameCharacter(rs.getLong("character_id"), rs.getLong("character_weapon_id"),
                        rs.getString("character_name"), rs.getInt("character_powerlevel"), ids));
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameCharacter getById(Long id) {
        try (Connection con = DatabaseConnection.retrieveConnection();
             PreparedStatement mainStatement = con.prepareStatement("SELECT * FROM gameCharacter WHERE character_id = ?");
             PreparedStatement factionsStatement = con.prepareStatement("SELECT fac_id FROM character_factions WHERE char_id = ?")) {
            mainStatement.setLong(1, id);
            ResultSet rs = mainStatement.executeQuery();
            factionsStatement.setLong(1, id);
            ResultSet rsFaction = factionsStatement.executeQuery();
            if (rs.next()) {
                List<Long> ids = new ArrayList<>();
                while (rsFaction.next()) {
                    ids.add(rsFaction.getLong(1));
                }
                return new GameCharacter(id, rs.getLong("character_weapon_id"),
                        rs.getString("character_name"), rs.getInt("character_powerlevel"), ids);
            } else {
                throw new RuntimeException("No character with such id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long save(GameCharacter gameCharacter) {
        if (gameCharacter.getWeaponId() == null) {
            return null;
        }
        try (Connection con = DatabaseConnection.retrieveConnection();
             PreparedStatement pr = con.prepareStatement("INSERT INTO gameCharacter (character_name, character_powerlevel, character_weapon_id) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, gameCharacter.getName());
            pr.setObject(2, gameCharacter.getPowerLevel());
            pr.setLong(3, gameCharacter.getWeaponId());
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
    public void update(GameCharacter gameCharacter) {
        if (gameCharacter.getId() == null) {
            throw new RuntimeException();
        }
        String sql = "UPDATE gameCharacter SET " +
                "character_name = COALESCE(?, character_name), character_powerlevel = COALESCE(?, character_powerlevel), " +
                "character_weapon_id = COALESCE(?, character_weapon_id) WHERE character_id = ?";
        try (Connection con = DatabaseConnection.retrieveConnection();
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, gameCharacter.getName());
            pr.setObject(2, gameCharacter.getPowerLevel());
            pr.setObject(3, gameCharacter.getWeaponId());
            pr.setLong(4, gameCharacter.getId());
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
        String sql = "DELETE FROM gameCharacter WHERE character_id = ?";
        try (Connection con = DatabaseConnection.retrieveConnection();
             PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setLong(1, id);
            return pr.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

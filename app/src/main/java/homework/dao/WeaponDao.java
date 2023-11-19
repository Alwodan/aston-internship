package homework.dao;

import homework.models.Weapon;
import homework.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WeaponDao implements Dao<Weapon> {
    public List<Weapon> getAll() {
        try (Connection con = DatabaseConnection.retrieveConnection();
             PreparedStatement pr = con.prepareStatement("SELECT * FROM weapon ORDER BY weapon_id ASC")) {
            List<Weapon> result = new ArrayList<>();
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                result.add(new Weapon(rs.getLong("weapon_id"),
                        rs.getString("weapon_name"), rs.getInt("weapon_damage")));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Weapon getById(Long id) {
        try (Connection con = DatabaseConnection.retrieveConnection();
        PreparedStatement pr = con.prepareStatement("SELECT * FROM weapon WHERE weapon_id = ?")) {
            pr.setLong(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                return new Weapon(id, rs.getString("weapon_name"), rs.getInt("weapon_damage"));
            } else {
                throw new RuntimeException("No weapon with such id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Long save(Weapon weapon) {
        try (Connection con = DatabaseConnection.retrieveConnection();
        PreparedStatement pr = con.prepareStatement("INSERT INTO weapon (weapon_name, weapon_damage) VALUES (?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, weapon.getName());
            pr.setInt(2, weapon.getDamage());
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

    public void update(Weapon weapon) {
        if (weapon.getId() == null) {
            throw new RuntimeException();
        }
        String sql = "UPDATE weapon SET weapon_name = COALESCE(?, weapon_name), weapon_damage = COALESCE(?, weapon_damage) WHERE weapon_id = ?";
        try (Connection con = DatabaseConnection.retrieveConnection();
        PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setString(1, weapon.getName());
            pr.setObject(2, weapon.getDamage());
            pr.setLong(3, weapon.getId());
            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        String sql = "DELETE FROM weapon WHERE weapon_id = ?";
        try (Connection con = DatabaseConnection.retrieveConnection();
        PreparedStatement pr = con.prepareStatement(sql)) {
            pr.setLong(1, id);
            return pr.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

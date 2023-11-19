package homework;

import homework.models.GameCharacter;
import homework.utils.DatabaseConnection;

import java.sql.*;

public class App {
    public static void main(String[] args) throws SQLException {
        try (Connection con = DatabaseConnection.retrieveConnection()) {
            PreparedStatement pr = con.prepareStatement("INSERT INTO weapon (weapon_name, weapon_damage) VALUES ('dagger', 25)");
            pr.execute();
            pr.close();
        }
    }
}

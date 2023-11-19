package homework.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection retrieveConnection() {
        try {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/aston", "postgres", "postgres");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

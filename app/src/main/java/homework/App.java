package homework;

import java.sql.*;

public class App {
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test");

        String sql = "CREATE TABLE gamers (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), rank VARCHAR(255))";
        Statement statement = conn.createStatement();
        statement.execute(sql);
        statement.close();

        sql = "INSERT INTO gamers (username, rank) VALUES ('mercenary', '4000')";
        Statement statement2 = conn.createStatement();
        statement2.executeUpdate(sql);
        statement2.close();

        sql = "SELECT * FROM gamers";
        var statement3 = conn.createStatement();
        ResultSet resultSet = statement3.executeQuery(sql);

        while (resultSet.next()) {
            System.out.println(resultSet.getString("username"));
            System.out.println(resultSet.getString("rank"));
        }
        statement3.close();

        conn.close();
    }
}

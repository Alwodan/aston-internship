package homework;

import homework.models.GameCharacter;

import java.sql.*;

public class App {
    public static void main(String[] args) throws SQLException {
        GameCharacter ch = new GameCharacter();
        ch.setId(1L);
        ch.setName("Ironclad");
        ch.setPowerLevel(1000);
    }
}

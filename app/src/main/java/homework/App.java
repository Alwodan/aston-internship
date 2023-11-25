package homework;

import homework.models.GameCharacter;
import homework.models.Weapon;
import homework.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.*;

public class App {
    public static void main(String[] args) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        sf.inTransaction(session -> {
            session.createSelectionQuery("from GameCharacter", GameCharacter.class)
                    .getResultList()
                    .forEach(character -> System.out.println("Character is in " + character.getFactions()));
        });
    }
}

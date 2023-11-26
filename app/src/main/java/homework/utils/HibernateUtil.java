package homework.utils;

import homework.models.AbstractNamedEntity;
import homework.models.Faction;
import homework.models.GameCharacter;
import homework.models.Weapon;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .build();

                Metadata metadata = new MetadataSources(serviceRegistry)
                        .addAnnotatedClass(AbstractNamedEntity.class)
                        .addAnnotatedClass(Weapon.class)
                        .addAnnotatedClass(GameCharacter.class)
                        .addAnnotatedClass(Faction.class)
                        .buildMetadata();

                sessionFactory = metadata.buildSessionFactory();
            } catch (Exception e) {
                System.err.println("CRAZY!");
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}

package homework.config;

import homework.models.AbstractNamedEntity;
import homework.models.Faction;
import homework.models.GameCharacter;
import homework.models.Weapon;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = {"homework"})
public class JavaConfig {
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SessionFactory sessionFactory() {
        try {
            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .build();

            Metadata metadata = new MetadataSources(serviceRegistry)
                    .addAnnotatedClass(AbstractNamedEntity.class)
                    .addAnnotatedClass(Weapon.class)
                    .addAnnotatedClass(GameCharacter.class)
                    .addAnnotatedClass(Faction.class)
                    .buildMetadata();

            return metadata.buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Something scary happened when initializing the sessionFactory");
            e.printStackTrace();
            throw new ExceptionInInitializerError();
        }
    }
}

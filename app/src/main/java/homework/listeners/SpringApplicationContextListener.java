package homework.listeners;

import homework.config.JavaConfig;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebListener
public class SpringApplicationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(JavaConfig.class);

        sce.getServletContext().setAttribute("applicationContext", ctx);
    }
}

package jdbc.util;

import jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.metamodel.Metadata;
import org.hibernate.metamodel.MetadataSources;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Util {
    private static String driver = "org.postgresql.Driver";
    private static String database = "coretask";
    private static String user = "postgres";
    private static String password = "postgres";

    public static Connection getJDBCConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/" + database, user, password);
    }

    public static SessionFactory getHibernateFactory(){
        Map<String, String> settings = new HashMap<>();
        settings.put(Environment.DRIVER, driver);
        settings.put(Environment.URL, "jdbc:postgresql://127.0.0.1:5432/" + database);
        settings.put(Environment.USER, user);
        settings.put(Environment.PASS, password);
        settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.HBM2DDL_AUTO, "update");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(settings).build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(User.class);
        Metadata metadata = metadataSources.buildMetadata();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        return sessionFactory;
    }
}

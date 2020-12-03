package jdbc.dao;

import jdbc.model.User;
import jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory factory;

    private String createUserTable = "CREATE TABLE public.coreusers\n" +
            "    (id bigserial NOT NULL,\n" +
            "    name character varying,\n" +
            "    last_name character varying,\n" +
            "    age integer,\n" +
            "    PRIMARY KEY (id))";

    private String dropUserTable = "drop table public.coreusers";

    private String insertUser = "INSERT INTO public.coreusers \n" +
            "(name, last_name, age)\n" +
            "VALUES (:name, :last_name, :age)";

    private String removeUser = "DELETE from public.coreusers WHERE id = :id";

    private String getAllUser = "select * from public.coreusers";

    private String clearUser = "delete from public.coreusers";

    public UserDaoHibernateImpl() {
        factory = Util.getHibernateFactory();
    }

    @Override
    public void createUsersTable() {
        executeQuery(createUserTable);
    }

    @Override
    public void dropUsersTable() {
        executeQuery(dropUserTable);
    }

    @Override
    public void cleanUsersTable() {
        executeQuery(clearUser);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createSQLQuery(insertUser);
            query.setParameter("name", name);
            query.setParameter("last_name", lastName);
            query.setParameter("age", age);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.getMessage());
        }finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createSQLQuery(removeUser);
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.getMessage());
        }finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createSQLQuery(getAllUser).addEntity(User.class);
            result = (List<User>) query.list();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }
        return result;
    }

    private void executeQuery(String dropUserTable) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createSQLQuery(dropUserTable).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (null != transaction){
                transaction.rollback();
            }
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }
    }
}

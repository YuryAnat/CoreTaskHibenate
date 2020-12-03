package jdbc.dao;

import jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void createUsersTable() throws SQLException;

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();
}

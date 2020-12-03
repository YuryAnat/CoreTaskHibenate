package jdbc.dao;

import jdbc.model.User;
import jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    private Connection connection = null;

    private String createUserTable = "CREATE TABLE public.coreusers\n" +
            "    (id bigserial NOT NULL,\n" +
            "    name character varying,\n" +
            "    last_name character varying,\n" +
            "    age integer,\n" +
            "    PRIMARY KEY (id))";

    private String dropUserTable = "drop table public.coreusers;";

    private String insertUser = "INSERT INTO public.coreusers(\n" +
            "name, last_name, age)\n" +
            "VALUES (?, ?, ?)";

    private String removeUser = "DELETE FROM public.coreusers\n" +
            "WHERE id = ?";

    private String getAllUser = "SELECT id, name, last_name, age\n" +
            "FROM public.coreusers";

    private String clearUser = "DELETE FROM public.coreusers";

    public UserDaoJDBCImpl() {
        try {
            connection = Util.getJDBCConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
        }
    }

    public void createUsersTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(createUserTable);
    }

    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute(dropUserTable);
        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertUser);
            preparedStatement.setObject(1, name);
            preparedStatement.setObject(2, lastName);
            preparedStatement.setObject(3, age);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(removeUser);
            preparedStatement.setObject(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getAllUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                result.add(user);
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
        return result;
    }

    public void cleanUsersTable() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(clearUser);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
    }
}
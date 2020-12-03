package jdbc.service;

import jdbc.dao.UserDao;
import jdbc.dao.UserDaoHibernateImpl;
import jdbc.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class UserServiceImpl implements UserService {
    private final static Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
    private UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDaoHibernateImpl();
    }



    public void createUsersTable() {
        try {
            userDao.createUsersTable();
        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();

    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}

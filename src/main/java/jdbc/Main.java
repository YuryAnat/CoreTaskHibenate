package jdbc;

import jdbc.model.User;
import jdbc.service.UserService;
import jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        service.createUsersTable();

        User user1 = new User("U1", "L1", (byte) 18);
        User user2 = new User("U2", "L2", (byte) 19);
        User user3 = new User("U3", "L3", (byte) 20);
        User user4 = new User("U4", "L4", (byte) 21);

        service.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        service.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        service.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        service.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        service.removeUserById(1);

        List<User> allUsers = service.getAllUsers();
        for (User user : allUsers) {
            System.out.println(user);
        }
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}

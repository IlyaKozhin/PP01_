package service;

import dao.UserDAO;
import model.User;

import javax.jws.soap.SOAPBinding;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    private static UserService userService;

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    private UserService() {
    }
    private static Connection getMysqlConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            //DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());
            StringBuilder url = new StringBuilder();
            url.
                    append("jdbc:mysql://").        //db type
                    append("localhost:").           //host name
                    append("3306/").                //port
                    append("db_example?").          //db name
                    append("user=root&").          //login
                    append("password=123");       //password
            System.out.println("URL: " + url + "\n");
            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    private static UserDAO getUserDAO() {
        return new UserDAO(getMysqlConnection());
    }

    public void cleanUp() throws SQLException {
        UserDAO dao = getUserDAO();
        dao.dropTable();
    }

    public void createTable() throws SQLException {
        UserDAO dao = getUserDAO();
        dao.createTable();
    }
    public User getUserById(long id) throws SQLException {
            return getUserDAO().getUserById(id);
    }
    public List<User> getAllUsers() throws SQLException {
        return getUserDAO().getUsers();
    }
    public void addUser(User user) throws SQLException {
        getUserDAO().addUser(user);
    }
    public void deleteUser(Long id) throws SQLException {
        getUserDAO().deleteUser(id);
    }
    public void updateUser(User user) throws SQLException {
        getUserDAO().updateUser(user);
    }
}

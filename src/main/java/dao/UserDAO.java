package dao;

import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection mysqlConnection) {
        connection = mysqlConnection;
    }

    public void createTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("create table if not exists user (id bigint auto_increment, name varchar(256), password varchar(256), primary key (id))");
        stmt.close();
    }

    public void dropTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DROP TABLE IF EXISTS user");
        stmt.close();
    }

    public List<User> getUsers() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("select * from user");
        ResultSet result = stmt.getResultSet();
        List<User> list = new ArrayList<>();
        while (result.next()) {
            list.add(new User(result.getLong(1), result.getString(2), result.getString(3)));
        }
        result.close();
        stmt.close();
        return list;
    }
    public User getUserById(long id) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("select * from user where id='" + id + "'");
        ResultSet result = stmt.getResultSet();
        result.next();
        User user = new User(result.getLong(1), result.getString(2), result.getString(3));
        result.close();
        stmt.close();
        return user;
    }
    public void updateUser(User user) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("replace into user (id,name,password) values (" + user.getId() + ",'" + user.getName() + "','" + user.getPassword() + "')");
        stmt.close();
    }
    public void addUser(User user) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("insert into user (name,password) values ('" + user.getName() + "','" + user.getPassword() + "')");
        stmt.close();
    }
    public void deleteUser(long id) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("delete from user where id='" + id + "'");
        stmt.close();
    }
}

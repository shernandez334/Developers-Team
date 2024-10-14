package org.example.dao;

import org.example.entities.Player;
import org.example.entities.User;
import org.example.exceptions.ExistingEmailException;
import org.example.entities.Admin;
import org.example.mysql.QueryResult;
import org.example.services.NotificationsService;

import java.sql.*;

import static org.example.mysql.MySqlHelper.*;

public class UserDaoMySql implements UserDao {

    @Override
    public User saveUser(User user) throws ExistingEmailException {
        String sql = String.format("INSERT INTO user (name, email, password, role) VALUES('%s', '%s', '%s', '%s');",
                user.getName(), user.getEmail(), user.getPassword(), user instanceof Player ? "player" : "admin");
        try{
            int userId = executeInsertStatementAndGetId(sql);
            user.setId(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (user instanceof Player) {
            new NotificationsService().subscribe((Player) user);
        }
        return user;
    }

    @Override
    public User getUser(String email, String password) {
        User user;
        String sql = String.format("SELECT user_id, name, role FROM user WHERE email = '%s' AND password = '%s';",
                email, password);
        try (QueryResult queryResult = createStatementAndExecuteQuery(sql)){
            ResultSet result = queryResult.getResultSet();
            if (!result.next()){
                user = null;
            } else if (result.getString("role").equalsIgnoreCase("player")){
                user = new Player(result.getInt("user_id"), result.getString("name"),
                        password, email);
                new NotificationsService().loadNotificationsFromDatabase((Player) user);
            } else {
                user = new Admin(result.getInt("user_id"), result.getString("name"),
                        password, email);
            }
        return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
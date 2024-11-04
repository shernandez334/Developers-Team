package org.example.dao;

import org.example.database.FactoryProvider;
import org.example.entities.Player;
import org.example.entities.User;
import org.example.exceptions.ExistingEmailException;
import org.example.entities.Admin;
import org.example.exceptions.MySqlException;
import org.example.exceptions.WrongCredentialsException;
import org.example.mysql.QueryResult;
import org.example.services.NotificationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

import static org.example.mysql.MySqlHelper.*;

public class UserDaoMySql implements UserDao {

    private static final Logger log = LoggerFactory.getLogger(UserDaoMySql.class);

    @Override
    public User saveUser(User user) throws MySqlException, ExistingEmailException {
        String sql = String.format("INSERT INTO user (name, email, password, role) VALUES('%s', '%s', '%s', '%s');",
                user.getName(), user.getEmail(), user.getPassword(), user instanceof Player ? "player" : "admin");
        try{
            int userId = executeInsertStatementAndGetId(sql);
            user.setId(userId);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new ExistingEmailException("There is already a user with this email.");
            }
            log.error("Unexpected SQLException when saving a User({}, {}, {}, {}).", user.getName(),
                    user.getPassword().replaceAll("(?<=.)(?=.+)(?=.)", "*"), user.getEmail(),
                    user.getClass().getSimpleName());
            throw new MySqlException("Database error: problem saving the user data.");
        }
        if (user instanceof Player) {
            new NotificationsService(FactoryProvider.getInstance().getDbFactory()).addSubscriber((Player) user);
        }
        return user;
    }

    @Override
    public User getUser(String email, String password) throws WrongCredentialsException {
        String sql = String.format("SELECT user_id, name, role FROM user WHERE email = '%s' AND password = '%s';",
                email, password);
        try (QueryResult queryResult = createStatementAndExecuteQuery(sql)){
            ResultSet result = queryResult.getResultSet();
            if (!result.next()){
                throw new WrongCredentialsException("No user found with such name and password.");
            }
            if (result.getString("role").equalsIgnoreCase("player")){
                return new Player(result.getInt("user_id"), result.getString("name"),
                        password, email);
            }else {
                return new Admin(result.getInt("user_id"), result.getString("name"),
                        password, email);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
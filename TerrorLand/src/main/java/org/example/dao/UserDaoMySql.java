package org.example.dao;

import org.example.entities.PlayerEntity;
import org.example.entities.UserEntity;
import org.example.exceptions.ExistingEmailException;
import org.example.exceptions.MySqlEmptyResultSetException;
import org.example.entities.AdminEntity;

import java.sql.*;

import static org.example.dao.GenericMethodsMySQL.*;

public class UserDaoMySql implements UserDao {
    @Override
    public UserEntity saveUser(UserEntity user) throws ExistingEmailException {
        String sql = String.format("INSERT INTO user (name, email, password, role) VALUES('%s', '%s', '%s', '%s');",
                user.getName(), user.getEmail(), user.getPassword(), user instanceof PlayerEntity ? "player" : "admin");
        try {
            createStatementAndExecute(sql);
            user.setId(getLastInsertedId());
            if (user instanceof PlayerEntity) {
                ((PlayerEntity) user).subscribeToNotifications();
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new ExistingEmailException(e);
        }
        return user;
    }

    @Override
    public UserEntity getUser(String email, String password) {
        UserEntity user;
        String sql = String.format("SELECT user_id, name, role FROM user WHERE email = '%s' AND password = '%s';",
                email, password);
        try (ResultSet result = createStatementAndExecuteQuery(sql)){
            if (!result.next()){
                user = null;
            } else if (result.getString("role").equalsIgnoreCase("player")){
                user = new PlayerEntity(result.getInt("user_id"), result.getString("name"),
                        password, email);
            }else {
                user = new AdminEntity(result.getInt("user_id"), result.getString("name"),
                        password, email);
            }
        return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

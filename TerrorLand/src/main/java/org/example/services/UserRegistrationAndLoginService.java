package org.example.services;

import com.google.common.hash.Hashing;
import org.example.database.DatabaseFactory;
import org.example.database.FactoryProvider;
import org.example.enums.UserRole;
import org.example.entities.Admin;
import org.example.entities.Player;
import org.example.entities.User;
import org.example.exceptions.ExistingEmailException;
import org.example.exceptions.FormatException;
import org.example.exceptions.MySqlException;
import org.example.exceptions.WrongCredentialsException;

import java.nio.charset.StandardCharsets;


public class UserRegistrationAndLoginService {

    private final DatabaseFactory databaseFactory;

    public UserRegistrationAndLoginService(DatabaseFactory databaseFactory){
        this.databaseFactory = databaseFactory;
    }


    public String validateUserName(String userName) throws FormatException {
        if (userName.matches(".*\\s.*")) {
            throw new FormatException("The username cannot contain blank spaces.");
        }
        if (userName.length() < 4) {
            throw new FormatException("The username must be at least 4 characters long.");
        }
        if (userName.length() > 20) {
            throw new FormatException("The username cannot have more than 20 characters.");
        }
        if (userName.matches(".*[^\\p{Alnum}]+.*")) {
            throw new FormatException("The username can contain only alphanumeric characters.");
        }
        return userName;
    }

    public String validatePasswordAndEncrypt(String password) throws FormatException {
        password = validatePassword(password);
        return Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }

    private String validatePassword(String password) throws FormatException {
        if (password.matches(".*\\s.*")) {
            throw new FormatException("The password cannot contain blank spaces.");
        }
        if (password.length() < 4) {
            throw new FormatException("The password must be at least 4 characters long.");
        }
        if (password.length() > 16) {
            throw new FormatException("The password cannot have more than 16 characters.");
        }
        return password;
    }

    public String validateEmail(String email) throws FormatException {
        if (email.matches(".*\\s.*")) {
            throw new FormatException("Invalid email: contains blank spaces.");
        }
        if (!email.matches("^.*@.*$")) {
            throw new FormatException("Invalid email: missing '@' character.");
        }
        if (!email.matches("^[\\p{Alnum}!#$%&'*+-/=?^_`{|}~]+@.*$")) {
            throw new FormatException("Invalid email: invalid local-part.");
        }
        if (!email.matches("^[^@]*@\\p{Alnum}+\\.\\p{Alnum}+$")) {
            throw new FormatException("Invalid email: invalid domain name.");
        }
        return email;
    }

    public User registerUser(String userName, String password, String email, UserRole role)
            throws MySqlException, ExistingEmailException {
        User user = switch (role){
            case UserRole.PLAYER -> new Player(userName, password, email);
            case UserRole.ADMIN -> new Admin(userName, password, email);
        };
        user = saveUserInDatabaseAndSetId(user);
        return user;
    }

    private User saveUserInDatabaseAndSetId(User user) throws MySqlException, ExistingEmailException {
        return databaseFactory.createUserDao().saveUser(user);
    }

    public User getUserFromCredentials(String email, String password) throws WrongCredentialsException {
        User user = databaseFactory.createUserDao().getUser(email, password);
        if (user instanceof Player){
            new NotificationsService(FactoryProvider.getInstance().getDbFactory())
                    .refreshNotificationsFromDatabase((Player) user);
            RewardsService.getInstance().refreshRewardsFromDatabase((Player) user);
        }
        return user;
    }
}

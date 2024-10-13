package org.example.services;

import org.example.dao.FactoryProvider;
import org.example.enums.UserRole;
import org.example.entities.Admin;
import org.example.entities.Player;
import org.example.entities.User;
import org.example.exceptions.ExistingEmailException;
import org.example.exceptions.FormatException;
import org.example.exceptions.MySqlException;

public class UserRegistrationService {

    private final FactoryProvider factoryProvider;

    public UserRegistrationService(FactoryProvider factoryProvider){
        this.factoryProvider = factoryProvider;
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

    public String validatePassword(String password) throws FormatException {
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
        return factoryProvider.get().createUserDao().saveUser(user);
    }
}

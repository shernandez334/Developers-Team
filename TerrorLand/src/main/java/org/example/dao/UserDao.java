package org.example.dao;

import org.example.entities.User;
import org.example.exceptions.ExistingEmailException;
import org.example.exceptions.MySqlException;
import org.example.exceptions.WrongCredentialsException;

public interface UserDao {

    User saveUser(User user) throws MySqlException, ExistingEmailException;
    User getUser(String name, String password) throws WrongCredentialsException;

}

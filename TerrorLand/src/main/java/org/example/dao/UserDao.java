package org.example.dao;

import org.example.entities.User;
import org.example.exceptions.ExistingEmailException;
import org.example.exceptions.MySqlException;

public interface UserDao {

    User saveUser(User user) throws MySqlException, ExistingEmailException;
    User getUser(String name, String password);

}

package org.example.dao;

import org.example.entities.User;
import org.example.exceptions.ExistingEmailException;

public interface UserDao {
    User saveUser(User user) throws ExistingEmailException;
    User getUser(String name, String password);
}

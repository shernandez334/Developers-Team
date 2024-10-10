package org.example.dao;

import org.example.entities.UserEntity;
import org.example.exceptions.ExistingEmailException;

public interface UserDao {

    UserEntity saveUser(UserEntity user) throws ExistingEmailException;
    UserEntity getUser(String name, String password);

}

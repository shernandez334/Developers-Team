package org.example.services;

import org.example.dao.UserDaoMySql;
import org.example.entities.UserEntity;
import org.example.util.IO;

public class UserLoginService {

    public UserEntity run(){
        UserEntity user;
        String password = "";
        String email = "";
        do{
            if (!email.isEmpty()){
                System.out.println("Error: not a valid email address.");
            }
            email = IO.readString("email: ");
        }while(email.matches(".*\\s.*") || !email.matches(".+@.+\\..+"));
        //Regex: has blank spaces. 2nd Regex: contains somenthing + @ + something + . +something
        do{
            if (!password.isEmpty()){
                System.out.println("Error: input a valid password.");
            }
            password = IO.readString("password: ");
        }while(password.matches(".*\\s.*") || password.length()<4); //Regex: has blank spaces
        user = new UserDaoMySql().getUser(email, password);
        return user;
    }

}

package org.example.services;

import org.example.dao.DatabaseFactory;
import org.example.exceptions.ExistingEmailException;
import org.example.entities.Admin;
import org.example.entities.Player;
import org.example.entities.User;
import org.example.util.IOHelper;
import org.example.util.MenuHelper;

public class UserRegistrationService {

    public User run(){
        User user;
        String userName = "";
        String password = "";
        String email = "";

        do{
            if (!userName.isEmpty()){
                System.out.println("Error: " +
                        "The username cannot contain blank spaces and must be between 4 and 16 characters long.");
            }
            userName = IOHelper.readString("username: ");
        }while(userName.matches(".*\\s.*") || userName.length()<4 || userName.length()>16);
        //Regex: has blank spaces
        do{
            if (!password.isEmpty()){
                System.out.println("Error: " +
                        "The password cannot contain blank spaces and must be between 4 and 16 characters long.");
            }
            password = IOHelper.readString("password: ");
        }while(password.matches(".*\\s.*") || password.length()<4 || password.length()>16);
        //Regex: has blank spaces
        do{
            if (!email.isEmpty()){
                System.out.println("Error: not a valid email address.");
            }
            email = IOHelper.readString("email: ");
        }while(email.matches(".*\\s.*") || !email.matches(".+@.+\\..+")); //Regex: has blank spaces

        int option = MenuHelper.readSelection("Select your role:", ">", "1. Player", "2. Admin");
        user = switch (option) {
            case 1 -> new Player(userName, password, email);
            case 2 -> new Admin(userName, password, email);
            default -> null;
        };
        assert user != null;
        try {
            if (DatabaseFactory.get().createUserDao().saveUser(user) != null){
                System.out.println("User created successfully, you can log in now!");
            } else {
                System.out.println("The user was not created...");
            }
        } catch (ExistingEmailException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }


}

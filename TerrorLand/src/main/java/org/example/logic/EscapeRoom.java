package org.example.logic;

import org.example.database.Database;
import org.example.database.MySQL;
import org.example.exceptions.MySqlCredentialsException;
import org.example.util.IO;
import org.example.util.Menu;
import org.example.util.Properties;

public class EscapeRoom {

    private static User user;

    static{
        user = null;
    }

    public void run() {
        try {
            initialSetup();
        } catch (MySqlCredentialsException e) {
            System.out.println(e.getMessage());
            return;
        }
        do{
            login();
        }while (user == null);
    }

    private void initialSetup() throws MySqlCredentialsException {
        Properties.createFileIfMissing();
        Database db = new MySQL();
        db.createIfMissing();
    }

    private void login(){
        String userName = "";
        String password = "";
        String email = "";
        int option = Menu.readSelection("Welcome!", ">", "1. Login", "2. Register");
        switch (option){
            case 1:
                System.out.println("Input username and password.");
                break;
            case 2:
                do{
                    if (!userName.isEmpty()){
                        System.out.println("Error: " +
                                "Username can't contain blank spaces and must have a length between 4 and 10.");
                    }
                    userName = IO.readString("username: ");
                }while(userName.matches(".*\\s.*") || userName.length()<4 || userName.length()>10);
                do{
                    if (!password.isEmpty()){
                        System.out.println("Error: " +
                                "Username can't contain blank spaces and must have a length between 4 and 10.");
                    }
                    password = IO.readString("password: ");
                }while(password.matches(".*\\s.*") || password.length()<4 || password.length()>10);
                do{
                    if (!email.isEmpty()){
                        System.out.println("Error: not a valid email address.");
                    }
                    email = IO.readString("email: ");
                }while(email.matches(".*\\s.*") || !email.matches(".+@.+\\..+") || email.length()>10);
                break;

        }
    }
}

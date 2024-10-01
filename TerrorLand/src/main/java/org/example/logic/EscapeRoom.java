package org.example.logic;

import org.example.database.Database;
import org.example.database.MySQL;
import org.example.exceptions.ExistingEmailException;
import org.example.exceptions.MySqlCredentialsException;
import org.example.util.IO;
import org.example.util.Menu;
import org.example.util.Properties;

public class EscapeRoom {
    private String name;
    private static User user;
    private static boolean quit;

    static{
        user = null;
        quit = false;
    }

    public EscapeRoom(String name){
        this.name = name;
    }

    public void run() {
        try {
            initialSetup();
        } catch (MySqlCredentialsException e) {
            System.out.println(e.getMessage());
            return;
        }
        do{
            if (EscapeRoom.user == null){
                EscapeRoom.user = loginMenu();
            } else if (EscapeRoom.user instanceof Player){
                playerMenu();
            }else {
                adminMenu();
            }
        }while (!EscapeRoom.quit);
        System.out.println("bye");
    }

    private void adminMenu() {
        int option = Menu.readSelection("Welcome Administrator! Select an option.", ">",
                "1. Create Element", "2. Delete Element", "3. Logout");
        switch (option) {
            case 3 -> EscapeRoom.user = null;
        }
    }

    private void playerMenu() {
        int option = Menu.readSelection("Welcome Player! Select an option.", ">",
                "1. Play Room", "2. Buy a Ticket", "3. Logout");
        switch (option) {
            case 3 -> EscapeRoom.user = null;
        }
    }

    private void initialSetup() throws MySqlCredentialsException {
        Properties.createFileIfMissing();
        Database db = new MySQL();
        db.createIfMissing();
    }

    private User loginMenu() {
        User user = null;
        int option = Menu.readSelection("Welcome to the login menu! Select an option.", ">",
                "1. Login", "2. Register", "3. Quit");
        switch (option){
            case 1 -> {
                user = login();
                System.out.println(user == null ? "Wrong credentials." : "You successfully logged as " + user.getName());
            }
            case 2 -> registerUser();
            case 3 -> EscapeRoom.quit = true;
        }
        return user;
    }

    private void registerUser(){
        User user;
        String userName = "";
        String password = "";
        String email = "";

        do{
            if (!userName.isEmpty()){
                System.out.println("Error: " +
                        "The username cannot contain blank spaces and must be between 4 and 16 characters long.");
            }
            userName = IO.readString("username: ");
        }while(userName.matches(".*\\s.*") || userName.length()<4 || userName.length()>16);
        do{
            if (!password.isEmpty()){
                System.out.println("Error: " +
                        "The password cannot contain blank spaces and must be between 4 and 16 characters long.");
            }
            password = IO.readString("password: ");
        }while(password.matches(".*\\s.*") || password.length()<4 || password.length()>16);
        do{
            if (!email.isEmpty()){
                System.out.println("Error: not a valid email address.");
            }
            email = IO.readString("email: ");
        }while(email.matches(".*\\s.*") || !email.matches(".+@.+\\..+"));

        int option = Menu.readSelection("Select your role:", ">", "1. Player", "2. Admin");
        user = switch (option) {
            case 1 -> new Player(userName, password, email);
            case 2 -> new Admin(userName, password, email);
            default -> null;
        };
        assert user != null;
        try {
            if (new MySQL().addUser(user)){
                System.out.println("User created successfully, you can log in now!");
            } else {
                System.out.println("The user was not created...");
            }
        } catch (ExistingEmailException e) {
            System.out.println(e.getMessage());
        }
    }

    private User login(){
        User user;
        String password = "";
        String email = "";
        do{
            if (!email.isEmpty()){
                System.out.println("Error: not a valid email address.");
            }
            email = IO.readString("email: ");
        }while(email.matches(".*\\s.*") || !email.matches(".+@.+\\..+"));
        do{
            if (!password.isEmpty()){
                System.out.println("Error: input a valid password.");
            }
            password = IO.readString("password: ");
        }while(password.matches(".*\\s.*") || password.length()<4);
        user = MySQL.getUser(email, password);
        return user;
    }
}

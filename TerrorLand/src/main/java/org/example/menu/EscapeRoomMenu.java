package org.example.menu;

import org.example.database.Database;
import org.example.database.MySQL;
import org.example.exceptions.ExistingEmailException;
import org.example.exceptions.MySqlCredentialsException;
import org.example.model.Admin;
import org.example.model.Player;
import org.example.model.Ticket;
import org.example.model.User;
import org.example.util.IO;
import org.example.util.Menu;
import org.example.util.Properties;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class EscapeRoomMenu {

    private static User user;
    private static boolean quit;

    static{
        user = null;
        quit = false;
    }

    public void run() {
        try {
            initialSetup();
        } catch (MySqlCredentialsException e) {
            System.out.println(e.getMessage());
            return;
        }
        do{
            if (EscapeRoomMenu.user == null){
                EscapeRoomMenu.user = loginMenu();
            } else if (EscapeRoomMenu.user instanceof Player){
                playerMenu();
            }else {
                adminMenu();
            }
        }while (!EscapeRoomMenu.quit);
        System.out.println("bye");
    }

    private void adminMenu() {
        Admin admin = (Admin) user;
        int option = Menu.readSelection("Welcome Administrator! Select an option.", ">",
                "1. Create Element", "2. Delete Element", "3. Set ticket price", "4. Get total income",
                "5. Send Notification" ,"6. Logout");
        switch (option) {
            case 3 -> setTicketPriceMenu();
            case 4 -> System.out.printf("The total income is %.2f€.%n", MySQL.getTotalIncome());
            case 5 -> admin.NotifyAll(MySQL.getSubscribers(), IO.readString("Insert the message: "));
            case 6 -> EscapeRoomMenu.user = null;
        }
    }

    private void setTicketPriceMenu() {
        System.out.printf("Each ticket costs %.2f.%n", Ticket.getPurchasePrice());
        String price = "";
        do {
            if (!price.isEmpty()) System.out.println("Wrong format, values between 0 and 99 accepted.");
            price = IO.readString("New price: ").replace(',', '.');
        }while(!Pattern.matches("^\\d{1,2}(\\.\\d{1,2})?$", price));
        Ticket.setPurchasePrice(BigDecimal.valueOf(Double.parseDouble(price)));
    }

    private void playerMenu() {
        Player player = (Player) user;
        System.out.printf("Welcome %s! You've got %d tickets.%n", player.getName(), player.getTotalTickets());
        int option = Menu.readSelection("Select an option.", ">",
                "1. Play Room", "2. Buy a Ticket",
                "3. Read notifications " + player.getNotificationWarning(),
                "4. " + (player.isSubscribed() ? "Stop receiving notifications" : "Receive notifications"),
                "5. Logout");
        switch (option) {
            case 1 -> System.out.println(player.cashTicket() ? "You played a room!" : "Get some tickets first!");
            case 2 -> buyTicketMenu();
            case 3 -> player.readNotifications();
            case 4 -> {
                if (player.isSubscribed()) {
                    player.unsubscribe();
                    System.out.println("You are no longer subscribed to the notifications.");
                }else {
                    player.subscribe();
                    System.out.println("You have subscribed successfully.");
                }
            }
            case 5 -> EscapeRoomMenu.user = null;
        }
    }

    private void buyTicketMenu(){
        System.out.printf("Each ticket costs %.2f.%n", Ticket.getPurchasePrice());
        ((Player) user).purchaseTickets(IO.readInt("How Many Tickets do you wish to buy?\n>"));
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
            case 3 -> EscapeRoomMenu.quit = true;
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
            if (new MySQL().createUser(user)){
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

package org.example.menu;

import org.example.dao.ElementDaoMySql;
import org.example.dao.FactoryProvider;
import org.example.enums.UserRole;
import org.example.exceptions.ExistingEmailException;
import org.example.exceptions.FormatException;
import org.example.exceptions.MySqlException;
import org.example.exceptions.MySqlNotValidCredentialsException;
import org.example.entities.Admin;
import org.example.entities.Player;
import org.example.entities.User;
import org.example.services.*;
import org.example.util.IOHelper;
import org.example.util.MenuHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;


public class MainMenu {
    private final ElementDaoMySql elementDaoMySql = new ElementDaoMySql();

    private static User user;
    private static boolean quit;
    private static final Logger log = LoggerFactory.getLogger(MainMenu.class);

    static{
        user = null;
        quit = false;
    }

    public void run() {
        try {
            new InitializeResourcesService().run();
        } catch (MySqlNotValidCredentialsException | SQLException e) {
            System.out.println(e.getMessage());
            return;
        }
        do{
            if (user == null){
                loginOrRegisterMenu();
            } else if (user instanceof Player){
                playerMenu();
            }else {
                adminMenu();
            }
        }while (!quit);
        System.out.println("bye");
    }

    private void loginOrRegisterMenu() {
        User user = null;
        int option = MenuHelper.readSelection("Welcome to the login menu! Select an option.", ">",
                "1. Login", "2. Register", "3. Quit");
        switch (option){
            case 1 -> {
                user = userLoginDialog();
                System.out.println(user == null ? "Wrong credentials." : "You successfully logged as " + user.getName());
            }
            case 2 -> userRegistrationDialog();
            case 3 -> quit = true;
        }
        MainMenu.user = user;
    }



    private void adminMenu() {
        Admin admin = (Admin) user;
        int option = MenuHelper.readSelection("Welcome Administrator! Select an option.", ">",
                "1. Create Element", "2. Delete Element", "3. Set ticket price", "4. Get total income",
                "5. Send Notification" ,"6. Logout");
        switch (option) {
            case 1 -> elementDaoMySql.createAnElement();
            case 2 -> elementDaoMySql.deleteAnElement();
            case 3 -> new TicketsService().setTicketPrice();
            case 4 -> System.out.printf("The total income is %.2f€.%n", new TicketsService().getTotalIncome());
            case 5 -> new NotificationsService(FactoryProvider.getInstance())
                    .notifySubscribers(IOHelper.readString("Insert the message: "));
            case 6 -> MainMenu.user = null;
        }
    }

    private void playerMenu() {
        Player player = (Player) user;
        System.out.printf("Welcome %s! You've got %d tickets.%n", player.getName(), player.getTicketsSize());
        int option = MenuHelper.readSelection("Select an option.", ">",
                "1. Play Room", "2. Buy a Ticket",
                "3. Read notifications " + player.getNotificationWarning(),
                "4. " + (player.isSubscribed() ? "Stop receiving notifications" : "Receive notifications"),
                "5. Logout");
        switch (option) {
            case 1 -> new RoomPlayService().play(player);
            case 2 -> new TicketsService().buyTickets(player);
            case 3 -> new NotificationsService(FactoryProvider.getInstance()).readNotifications(player);
            case 4 -> {
                if (player.isSubscribed()) {
                    new NotificationsService(FactoryProvider.getInstance()).removeSubscriber(player);
                    System.out.println("You are no longer subscribed to the notifications.");
                }else {
                    new NotificationsService(FactoryProvider.getInstance()).addSubscriber(player);
                    System.out.println("You have subscribed successfully.");
                }
            }
            case 5 -> MainMenu.user = null;
        }
    }

    private void userRegistrationDialog() {
        UserRegistrationService service = new UserRegistrationService(FactoryProvider.getInstance());
        String userName;
        String password;
        String email;
        UserRole role;

        while (true) {
            try {
                userName = service.validateUserName(IOHelper.readString("Username: "));
                break;
            } catch (FormatException e) {
                System.out.println(e.getMessage());
            }
        }
        while (true) {
            try {
                password = service.validatePasswordAndEncrypt(IOHelper.readString("Password: "));
                break;
            } catch (FormatException e) {
                System.out.println(e.getMessage());
            }
        }
        while (true) {
            try {
                email = service.validateEmail(IOHelper.readString("Email: "));
                break;
            } catch (FormatException e) {
                System.out.println(e.getMessage());
            }
        }
        int option = MenuHelper.readSelection("Select your role:", ">", "1. Player", "2. Admin");
        role = switch (option) {
            case 1 -> UserRole.PLAYER;
            case 2 -> UserRole.ADMIN;
            default -> throw new IllegalStateException("Error: role selection dialog allowed invalid selection.");
        };
        try {
            MainMenu.user = service.registerUser(userName, password, email, role);
            System.out.println("User created successfully, you can log in now!");
        } catch (ExistingEmailException e) {
            System.out.println("Couldn't complete the register: there is already a user with this email.");
            MainMenu.user = null;
        } catch (MySqlException e) {
            System.out.println("Quitting the app: unexpected DB error.");
            log.error(e.getMessage(), e);
            quit = true;
        }
    }

    private User userLoginDialog() {
        UserRegistrationService service = new UserRegistrationService(FactoryProvider.getInstance());
        String password;
        String email;

        while (true) {
            try {
                email = service.validateEmail(IOHelper.readString("Email: "));
                break;
            } catch (FormatException e) {
                System.out.println("Input a valid email.");
            }
        }
        while (true) {
            try {
                password = service.validatePasswordAndEncrypt(IOHelper.readString("Password: "));
                break;
            } catch (FormatException e) {
                System.out.println("Password not valid, try again.");
            }
        }
        return service.getUserFromCredentials(email, password);
    }


}

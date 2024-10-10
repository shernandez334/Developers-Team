package org.example.menu;

import org.example.dao.AdminDaoMySql;
import org.example.dao.ElementDaoMySql;
import org.example.dao.NotificationDaoMySql;
import org.example.database.DeprecatedMySQL;
import org.example.exceptions.MySqlNotValidCredentialsException;
import org.example.entities.AdminEntity;
import org.example.entities.PlayerEntity;
import org.example.entities.UserEntity;
import org.example.services.*;
import org.example.util.IO;
import org.example.util.MenuCreator;

import java.sql.SQLException;


public class MainMenu {
    private final ElementDaoMySql elementDaoMySql = new ElementDaoMySql();

    private static UserEntity user;
    private static boolean quit;

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
            } else if (user instanceof PlayerEntity){
                playerMenu();
            }else {
                adminMenu();
            }
        }while (!quit);
        System.out.println("bye");
    }

    private void loginOrRegisterMenu() {
        UserEntity user = null;
        int option = MenuCreator.readSelection("Welcome to the login menu! Select an option.", ">",
                "1. Login", "2. Register", "3. Quit");
        switch (option){
            case 1 -> {
                user = new UserLoginService().run();
                System.out.println(user == null ? "Wrong credentials." : "You successfully logged as " + user.getName());
            }
            case 2 -> new UserRegistrationService().run();
            case 3 -> quit = true;
        }
        MainMenu.user = user;
    }

    private void adminMenu() {
        AdminEntity admin = (AdminEntity) user;
        int option = MenuCreator.readSelection("Welcome Administrator! Select an option.", ">",
                "1. Create Element", "2. Delete Element", "3. Set ticket price", "4. Get total income",
                "5. Send Notification" ,"6. Logout");
        switch (option) {
            case 1 -> elementDaoMySql.createAnElement();
            case 2 -> elementDaoMySql.deleteAnElement();
            case 3 -> new TicketsService().setTicketPrice();
            case 4 -> System.out.printf("The total income is %.2f€.%n", admin.getTotalIncome());
            case 5 -> admin.NotifyAll(new NotificationDaoMySql().getSubscribers(), IO.readString("Insert the message: "));
            case 6 -> MainMenu.user = null;
        }
    }

    private void playerMenu() {
        PlayerEntity player = (PlayerEntity) user;
        System.out.printf("Welcome %s! You've got %d tickets.%n", player.getName(), player.getTotalTickets());
        int option = MenuCreator.readSelection("Select an option.", ">",
                "1. Play Room", "2. Buy a Ticket",
                "3. Read notifications " + player.getNotificationWarning(),
                "4. " + (player.isSubscribed() ? "Stop receiving notifications" : "Receive notifications"),
                "5. Logout");
        switch (option) {
            case 1 -> System.out.println(player.cashTicket() ? "You played a room!" : "Get some tickets first!");
            case 2 -> new TicketsService().buyTickets(player);
            case 3 -> new NotificationsService().readNotifications(player);
            case 4 -> {
                if (player.isSubscribed()) {
                    player.unsubscribeFromNotifications();
                    System.out.println("You are no longer subscribed to the notifications.");
                }else {
                    player.subscribeToNotifications();
                    System.out.println("You have subscribed successfully.");
                }
            }
            case 5 -> MainMenu.user = null;
        }
    }


}

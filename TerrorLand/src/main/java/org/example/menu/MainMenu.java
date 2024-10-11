package org.example.menu;

import org.example.exceptions.MySqlNotValidCredentialsException;
import org.example.entities.Admin;
import org.example.entities.Player;
import org.example.entities.User;
import org.example.services.*;
import org.example.util.IOHelper;
import org.example.util.MenuHelper;

import java.sql.SQLException;


public class MainMenu {

    private static User user;
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
                user = new UserLoginService().run();
                System.out.println(user == null ? "Wrong credentials." : "You successfully logged as " + user.getName());
            }
            case 2 -> new UserRegistrationService().run();
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
            case 1 -> elementMySql.createAnElement();
            //case 2 -> elementDaoMySql.deleteAnElement();
            case 3 -> new TicketsService().setTicketPrice();
            case 4 -> System.out.printf("The total income is %.2f€.%n", new TicketsService().getTotalIncome());
            case 5 -> new NotificationsService().notifySubscribers(IOHelper.readString("Insert the message: "));
            case 6 -> MainMenu.user = null;
        }
    }

    private void playerMenu() {
        Player player = (Player) user;
        System.out.printf("Welcome %s! You've got %d tickets.%n", player.getName(), player.getTotalTickets());
        int option = MenuHelper.readSelection("Select an option.", ">",
                "1. Play Room", "2. Buy a Ticket",
                "3. Read notifications " + player.getNotificationWarning(),
                "4. " + (player.isSubscribed() ? "Stop receiving notifications" : "Receive notifications"),
                "5. Logout");
        switch (option) {
            case 1 -> new RoomPlayService().play(player);
            case 2 -> new TicketsService().buyTickets(player);
            case 3 -> new NotificationsService().readNotifications(player);
            case 4 -> {
                if (player.isSubscribed()) {
                    new NotificationsService().unsubscribe(player);
                    System.out.println("You are no longer subscribed to the notifications.");
                }else {
                    new NotificationsService().subscribe(player);
                    System.out.println("You have subscribed successfully.");
                }
            }
            case 5 -> MainMenu.user = null;
        }
    }


}

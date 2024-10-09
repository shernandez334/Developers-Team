package org.example.model;

import org.example.database.MySQL;
import org.example.database.Retrievable;
import org.example.util.IO;

import java.util.ArrayList;
import java.util.ListIterator;

public class Player extends User implements Retrievable {

    private final ArrayList<Ticket> tickets;
    private final ArrayList<Notification> notifications;
    private Boolean isSubscribed;

    {
        tickets = new  ArrayList<>();
        notifications = new ArrayList<>();
    }

    public Player(String name, String password, String email) {
        super(name, password, email);
    }

    public Player(String name, String email, int id) {
        super(name, email, id);
        loadTicketsFromDatabase();
        this.isSubscribed = MySQL.isSubscribed(super.getId());
        loadNotificationsFromDatabase();
    }

    public Boolean isSubscribed() {
        return this.isSubscribed;
    }

    public void purchaseTickets(int quantity){
        for (int i = 0; i < quantity; i++){
            Ticket.createTicket(this);
        }
        tickets.clear();
        loadTicketsFromDatabase();
    }

    private void loadTicketsFromDatabase(){
        this.tickets.addAll(MySQL.getTickets(this, true));
    }

    public int getTotalTickets(){
        return this.tickets.size();
    }

    public boolean cashTicket(){
        if (tickets.isEmpty()) return false;
        Ticket ticket = tickets.removeFirst();
        ticket.cash();
        return true;
    }

    public void unsubscribe() {
        MySQL.unsubscribePlayer(super.getId());
        this.isSubscribed = false;
    }

    public void subscribe() {
        MySQL.subscribePlayer(super.getId());
        this.isSubscribed = true;
    }

    public void readNotifications() {
        loadNotificationsFromDatabase();
        if (notifications.isEmpty()){
            System.out.println("You have no messages.");
            return;
        }
        ListIterator<Notification> item = this.notifications.listIterator();
        char option = ' ';
        Notification notification = item.next();
        boolean backed = false;
        do {
            if (option == 'p' && item.previousIndex() > 0){
                item.previous();
                notification = item.previous();
                backed = true;
            }else if (option == 'n' && item.hasNext()){
                notification = item.next();
            }else if (option == 'd'){
                notification.deleteFromDatabase();
                item.remove();
                if (notifications.isEmpty()) {
                    System.out.println("You have no messages left.");
                    return;
                } else if (item.hasNext()){
                    notification = item.next();
                } else if (item.previousIndex() >= 0) {
                    notification = item.previous();
                    backed = true;
                }
            }
            if (backed){
                item.next();
                backed = false;
            }
            System.out.printf("%n%s%n", IO.indentText(notification.getMessage(), "**  "));
            System.out.printf("Previous(p)  -- %d/%d --  Next(n), Delete(d), Quit(q)%n",
                    item.nextIndex(), notifications.size());
            do{
                option = IO.readChar(">");
            }while (!(option == 'p' || option == 'n' || option == 'd' || option == 'q'));
        } while(option != 'q' && !notifications.isEmpty());
    }

    public void loadNotificationsFromDatabase(){
        this.notifications.clear();
        this.notifications.addAll(MySQL.retrieveNotifications(super.getId()));
    }

    public String getNotificationWarning(){
        return this.notifications.isEmpty() ? "(no messages)" : String.format("(%d Total)", this.notifications.size());
    }
}

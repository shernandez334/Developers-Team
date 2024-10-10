package org.example.entities;

import org.example.dao.PlayerDaoMySql;

import java.util.ArrayList;
import java.util.List;

public class Player extends User {

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

    public Player(int id, String name, String password, String email) {
        super(id, name, password, email);
        this.tickets.addAll(new PlayerDaoMySql().retrieveTickets(this, true));
        this.isSubscribed = new PlayerDaoMySql().isSubscribed(this);
    }

    public Boolean isSubscribed() {
        return this.isSubscribed;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public int getTotalTickets(){
        return this.tickets.size();
    }

    public void setSubscribed(Boolean subscribed) {
        isSubscribed = subscribed;
    }

    public void setNotifications(List<Notification> notifications){
        this.notifications.clear();
        this.notifications.addAll(notifications);
    }

    public String getNotificationWarning(){
        return this.notifications.isEmpty() ? "(no messages)" : String.format("(%d Total)", this.notifications.size());
    }

    public boolean hasNoNotifications() {
        return this.notifications.isEmpty();
    }

    public List<Notification> getNotifications() {
        return this.notifications;
    }

    public int notificationsSize() {
        return this.notifications.size();
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }
}

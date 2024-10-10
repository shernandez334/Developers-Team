package org.example.entities;

import org.example.dao.PlayerDaoMySql;

import java.util.ArrayList;
import java.util.List;

public class PlayerEntity extends UserEntity {

    private final ArrayList<TicketEntity> tickets;
    private final ArrayList<NotificationEntity> notifications;
    private Boolean isSubscribed;

    {
        tickets = new  ArrayList<>();
        notifications = new ArrayList<>();
    }

    public PlayerEntity(String name, String password, String email) {
        super(name, password, email);
    }

    public PlayerEntity(int id, String name, String password, String email) {
        super(id, name, password, email);
        this.tickets.addAll(new PlayerDaoMySql().retrieveTickets(this, true));
        this.isSubscribed = new PlayerDaoMySql().isSubscribed(this);
        loadNotificationsFromDatabase();
    }

    public Boolean isSubscribed() {
        return this.isSubscribed;
    }

    public int getTotalTickets(){
        return this.tickets.size();
    }

    public boolean cashTicket(){
        if (tickets.isEmpty()) return false;
        TicketEntity ticket = tickets.removeFirst();
        ticket.cash();
        return true;
    }

    public void unsubscribeFromNotifications() {
        new PlayerDaoMySql().unsubscribePlayer(this);
        this.isSubscribed = false;
    }

    public void subscribeToNotifications() {
        new PlayerDaoMySql().subscribePlayer(this);
        this.isSubscribed = true;
    }

    public void loadNotificationsFromDatabase(){
        this.notifications.clear();
        this.notifications.addAll(new PlayerDaoMySql().retrieveNotifications(super.getId()));
    }

    public String getNotificationWarning(){
        return this.notifications.isEmpty() ? "(no messages)" : String.format("(%d Total)", this.notifications.size());
    }

    public void retrieveTickets() {
        tickets.clear();
        tickets.addAll(new PlayerDaoMySql().retrieveTickets(this, true));
    }

    public boolean hasNoNotifications() {
        return this.notifications.isEmpty();
    }

    public List<NotificationEntity> getNotifications() {
        return this.notifications;
    }

    public int notificationsSize() {
        return this.notifications.size();
    }

    public void addTicket(TicketEntity ticket) {
        tickets.add(ticket);
    }
}

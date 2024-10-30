package org.example.entities;

import org.example.dao.PlayerDaoMySql;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player extends User {

    private final ArrayList<Ticket> tickets;
    private final ArrayList<Notification> notifications;
    private Set<Reward> rewards;
    private Boolean isSubscribed;

    {
        tickets = new  ArrayList<>();
        notifications = new ArrayList<>();
        rewards = new HashSet<>();
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

    public int getTicketsSize(){
        return this.tickets.size();
    }

    public void setSubscribed(Boolean subscribed) {
        isSubscribed = subscribed;
    }

    public void setNotifications(List<Notification> notifications){
        this.notifications.clear();
        this.notifications.addAll(notifications);
    }

    public void addNotification(Notification notification){
        this.notifications.add(notification);
    }

    public String getNotificationWarning(){
        return this.notifications.isEmpty() ? "(no messages)" : String.format("(%d Total)", this.notifications.size());
    }

    public List<Notification> getNotifications() {
        return this.notifications;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public Set<Reward> getRewards() {
        return this.rewards;
    }

    public void addReward(Reward reward){
        this.rewards.add(reward);
    }

    public void setRewards(Set<Reward> rewards) {
        this.rewards = rewards;
    }
}

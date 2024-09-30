package org.example.logic;

import org.example.database.MySQL;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Player extends User implements Retrievable{

    private final ArrayList<Ticket> tickets;
    private Boolean isSubscribed;

    {
        tickets = new  ArrayList<>();
    }


    public Player(String name, String password, String email) {
        super(name, password, email);
    }
    public Player(String name, String email, int id) {
        super(name, email, id);
        loadTicketsFromDatabase();
        this.isSubscribed = MySQL.isSubscribed(super.getId());
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

    public String readNotifications() {
        return String.join("\n", MySQL.getNotifications(super.getId()));
    }
}

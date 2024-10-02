package org.example.model;

import org.example.dao.PropertiesDaoMySql;
import org.example.database.MySQL;

import java.math.BigDecimal;

public class Ticket {
    private static BigDecimal purchasePrice;
    private final int id;
    private BigDecimal price;

    static{
        Ticket.purchasePrice = BigDecimal.valueOf(9.99);
    }

    public Ticket(int ticketId) {
        this.id = ticketId;
    }

    public static BigDecimal getPurchasePrice() {
        return (new PropertiesDaoMySql()).getTicketPrice();
    }

    public static void setPurchasePrice(String price) {
        (new PropertiesDaoMySql()).setTicketPrice(price);
    }

    public static void createTicket(User user){
        MySQL.createTicket(user, Ticket.purchasePrice);
    }

    public void cash(){
        MySQL.cashTicket(this.id);
    }
}

package org.example.logic;

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
        return purchasePrice;
    }

    public static void setPurchasePrice(BigDecimal purchasePrice) {
        Ticket.purchasePrice = purchasePrice;
    }

}

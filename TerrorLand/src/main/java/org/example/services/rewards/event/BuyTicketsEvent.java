package org.example.services.rewards.event;

public class BuyTicketsEvent implements Event {

    private int quantity;

    public BuyTicketsEvent(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}

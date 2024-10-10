package org.example.entities;

import java.math.BigDecimal;

public class Ticket {
    private int id;
    private BigDecimal price;

    public Ticket(BigDecimal price) {
        this.price = price;
    }

    public Ticket(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

}

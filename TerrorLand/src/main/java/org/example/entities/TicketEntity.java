package org.example.entities;

import org.example.dao.TicketDaoMySql;

import java.math.BigDecimal;

public class TicketEntity {
    private int id;
    private BigDecimal price;

    public TicketEntity(BigDecimal price) {
        this.price = price;
    }

    public TicketEntity(int id) {
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

    public TicketEntity createTicket(PlayerEntity player){
        new TicketDaoMySql().saveTicket(this, player);
        return this;
    }

    public void cash(){
        new TicketDaoMySql().cashTicket(this);
    }
}

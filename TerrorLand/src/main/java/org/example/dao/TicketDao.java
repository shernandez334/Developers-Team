package org.example.dao;

import org.example.entities.Player;
import org.example.entities.Ticket;

import java.math.BigDecimal;

public interface TicketDao {
    void saveTicket(Ticket ticket, Player player);

    void cashTicket(Ticket ticket);

    BigDecimal getTotalIncome();
}

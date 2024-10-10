package org.example.dao;

import org.example.entities.PlayerEntity;
import org.example.entities.TicketEntity;

import java.sql.SQLIntegrityConstraintViolationException;

import static org.example.dao.GenericMethodsMySQL.*;

public class TicketDaoMySql {

    public void saveTicket(TicketEntity ticket, PlayerEntity player) {
        String sql = String.format("INSERT INTO ticket (user_id, price, cashed) VALUES('%s', '%s', '%s');",
                player.getId(), ticket.getPrice(), 0);
        try {
            ticket.setId(executeInsertStatementAndGetId(sql));
        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void cashTicket(TicketEntity ticket) {
        String sql = String.format("UPDATE ticket SET cashed = 1 WHERE ticket_id = %d;", ticket.getId());
        try {
            createStatementAndExecute(sql);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }
    }

}

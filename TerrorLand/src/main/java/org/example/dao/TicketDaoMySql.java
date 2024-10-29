package org.example.dao;

import org.example.entities.Player;
import org.example.entities.Ticket;
import org.example.exceptions.MySqlEmptyResultSetException;

import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.example.mysql.MySqlHelper.*;

public class TicketDaoMySql implements TicketDao {

    @Override
    public void saveTicket(Ticket ticket, Player player) {
        String sql = String.format("INSERT INTO ticket (user_id, price, cashed) VALUES('%s', '%s', '%s');",
                player.getId(), ticket.getPrice(), 0);
        try {
            ticket.setId(executeInsertStatementAndGetId(sql));
        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void cashTicket(Ticket ticket) {
        String sql = String.format("UPDATE ticket SET cashed = 1 WHERE ticket_id = %d;", ticket.getId());
        try {
            createStatementAndExecute(sql);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigDecimal getTotalIncome() {
        String sql = "SELECT SUM(price) FROM ticket;";
        try {
            BigDecimal totalIncome = retrieveSingleValueFromDatabase(sql, BigDecimal.class);
            if (totalIncome == null) return BigDecimal.valueOf(0);
            return totalIncome;
        } catch (MySqlEmptyResultSetException e) {
            throw new RuntimeException(String.format("The query '%s' didn't yield a result.%n", sql));
        }
    }

}

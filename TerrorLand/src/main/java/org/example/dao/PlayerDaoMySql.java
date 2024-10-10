package org.example.dao;

import org.example.entities.NotificationEntity;
import org.example.entities.PlayerEntity;
import org.example.entities.TicketEntity;
import org.example.exceptions.MySqlEmptyResultSetException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.example.dao.GenericMethodsMySQL.*;

public class PlayerDaoMySql {

    public void subscribePlayer(PlayerEntity player){
        try {
            createStatementAndExecute(String.format("INSERT INTO subscription (user_id) VALUES (%d);", player.getId()));
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }
    }

    public void unsubscribePlayer(PlayerEntity player){
        try {
            createStatementAndExecute(String.format("DELETE FROM subscription WHERE user_id = %d;", player.getId()));
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isSubscribed(PlayerEntity player){
        String sql = String.format("SELECT user_id FROM subscription WHERE user_id = %d;", player.getId());
        try{
            retrieveSingleValueFromDatabase(sql, Integer.class);
            return true;
        }catch (MySqlEmptyResultSetException e){
            return false;
        }
    }

    public List<TicketEntity> retrieveTickets(PlayerEntity player, boolean onlyNotCashed) {
        List<TicketEntity> tickets = new ArrayList<>();
        String sql = String.format("SELECT ticket_id, price, cashed FROM ticket WHERE user_id = '%d';", player.getId());

        try (ResultSet result = createStatementAndExecuteQuery(sql)){
            while (result.next()){
                if (onlyNotCashed){
                    if(!result.getBoolean("cashed")){
                        int ticketId = result.getInt("ticket_id");
                        tickets.add(new TicketEntity(ticketId));
                    }
                }else {
                    tickets.add(new TicketEntity(result.getInt("ticket_id")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    public List<NotificationEntity> retrieveNotifications(int playerId) {
        List<NotificationEntity> response = new ArrayList<>();
        List<List<Object>> items = retrieveMultipleColumnsFromDatabase(
                String.format("SELECT notification_id, message FROM notification WHERE user_id = %d;", playerId),
                new String[] {"Integer", "String"});
        items.forEach(e -> response.add(new NotificationEntity((int) e.getFirst(), playerId, (String) e.get(1))));
        return response;
    }

}

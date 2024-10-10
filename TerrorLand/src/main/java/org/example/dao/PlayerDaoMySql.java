package org.example.dao;

import org.example.entities.Notification;
import org.example.entities.Player;
import org.example.entities.Ticket;
import org.example.exceptions.MySqlEmptyResultSetException;
import org.example.mysql.QueryResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.mysql.MySqlHelper.*;

public class PlayerDaoMySql implements PlayerDao {

    @Override
    public void subscribePlayer(Player player){
        try {
            createStatementAndExecute(String.format("INSERT INTO subscription (user_id) VALUES (%d);", player.getId()));
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unsubscribePlayer(Player player){
        try {
            createStatementAndExecute(String.format("DELETE FROM subscription WHERE user_id = %d;", player.getId()));
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isSubscribed(Player player){
        String sql = String.format("SELECT user_id FROM subscription WHERE user_id = %d;", player.getId());
        try{
            retrieveSingleValueFromDatabase(sql, Integer.class);
            return true;
        }catch (MySqlEmptyResultSetException e){
            return false;
        }
    }

    @Override
    public List<Ticket> retrieveTickets(Player player, boolean onlyNotCashed) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = String.format("SELECT ticket_id, price, cashed FROM ticket WHERE user_id = '%d';", player.getId());

        try (QueryResult queryResult = createStatementAndExecuteQuery(sql)){
            ResultSet resultSet = queryResult.getResultSet();
            while (resultSet.next()){
                if (onlyNotCashed){
                    if(!resultSet.getBoolean("cashed")){
                        int ticketId = resultSet.getInt("ticket_id");
                        tickets.add(new Ticket(ticketId));
                    }
                }else {
                    tickets.add(new Ticket(resultSet.getInt("ticket_id")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    @Override
    public List<Notification> retrieveNotifications(int playerId) {
        List<Notification> response = new ArrayList<>();
        List<List<Object>> items = retrieveMultipleColumnsFromDatabase(
                String.format("SELECT notification_id, message FROM notification WHERE user_id = %d;", playerId),
                new String[] {"Integer", "String"});
        items.forEach(e -> response.add(new Notification((int) e.getFirst(), playerId, (String) e.get(1))));
        return response;
    }

}

package org.example.dao;

import org.example.entities.Notification;
import org.example.entities.Player;
import org.example.entities.Ticket;

import java.util.List;

public interface PlayerDao {
    void subscribePlayer(Player player);

    void unsubscribePlayer(Player player);

    boolean isSubscribed(Player player);

    List<Ticket> retrieveTickets(Player player, boolean onlyNotCashed);

    List<Notification> retrieveNotifications(int playerId);

}

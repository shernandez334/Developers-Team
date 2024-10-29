package org.example.dao;

import org.example.entities.Notification;
import org.example.entities.Player;
import org.example.entities.Reward;
import org.example.entities.Ticket;

import java.util.List;
import java.util.Set;

public interface PlayerDao {
    void subscribePlayer(Player player);

    void unsubscribePlayer(Player player);

    boolean isSubscribed(Player player);

    List<Ticket> retrieveTickets(Player player, boolean onlyNotCashed);

    List<Notification> retrieveNotifications(int playerId);

    Set<Reward> retrieveRewards(int playerId);
}

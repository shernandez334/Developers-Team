package org.example.services.rewards;

import org.example.entities.Player;
import org.example.services.rewards.event.Event;

public class Request {

    Player player;
    Event event;

    public Request(Player player, Event event) {
        this.player = player;
        this.event = event;
    }

    public Player getPlayer() {
        return player;
    }

    public Event getEvent() {
        return event;
    }
}

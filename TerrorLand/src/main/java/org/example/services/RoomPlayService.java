package org.example.services;

import org.example.entities.Player;

public class RoomPlayService {

    public void play(Player player) {
        System.out.println(new TicketsService().cashTicket(player) ? "You played a room!" : "Get some tickets first!");
    }
}

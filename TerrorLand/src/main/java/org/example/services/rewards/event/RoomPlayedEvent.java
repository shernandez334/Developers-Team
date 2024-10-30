package org.example.services.rewards.event;

import org.example.entities.Room;

public class RoomPlayedEvent implements Event {
    boolean success;
    Room room;

    public RoomPlayedEvent(boolean success, Room room) {
        this.success = success;
        this.room = room;
    }

    public boolean isSuccess() {
        return success;
    }

    public Room getRoom() {
        return room;
    }
}

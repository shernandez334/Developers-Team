package org.example.factory;

import org.example.entities.Room;

public interface RoomFactory {
    Room createElementRoom();
    public int getCurrentRoomId();
}

package org.example.factory;

import org.example.entities.Room;
import org.example.enums.Difficulty;
import org.example.enums.RoomStatus;

public interface RoomFactory {
    Room createElementRoom();
    int getCurrentRoomId();
    public void deleteRoom();
    public void retrieveRoom();
    public void updateRoomStatus(int roomId, int newRoomStatus);
}

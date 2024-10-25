package org.example.factory;

import org.example.enums.RoomStatus;

public interface DisplayFactory {
    public void displayActiveRooms();
    public void displayDeletedRooms();
    public void displayAllRooms(RoomStatus roomStatus);
    public void displayActiveRoomElements();
    public void displayDeletedRoomElements();
    public void displayRoomElements(RoomStatus roomStatus);
    public void displayElements(int roomId, RoomStatus roomStatus);
    public boolean askToContinue();
}

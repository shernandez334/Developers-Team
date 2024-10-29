package org.example.entities;

public class RoomHasElement {
    private int room_id;
    private int element_id;
    private int quantity;

    public RoomHasElement(int room_id, int element_id, int quantity){
        this.room_id = room_id;
        this.element_id = element_id;
        this.quantity = quantity;
    }

    public int getRoomId(){
        return this.room_id;
    }
    public int getElementId(){
        return this.element_id;
    }
    public int getQuantity(){
        return this.quantity;
    }
}

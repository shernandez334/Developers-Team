package org.example.logic;

import org.example.database.MySQL;

import java.util.ArrayList;

public class Admin extends User{
    public Admin(String name, String password, String email) {
        super(name, password, email);
    }
    public Admin(String name, String email, int id) {
        super(name, email, id);
    }

    public void NotifyUser(Notification notification){
        MySQL.insertIntoDatabase(notification);
    }

    public void NotifyAll(ArrayList<Integer> subscribers, String message){
        for (Integer subscriberId : subscribers){
            NotifyUser(new Notification(subscriberId, message));
        }
    }
}

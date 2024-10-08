package org.example.model;

import org.example.database.MySQL;
import org.example.database.Notifier;

import java.util.ArrayList;

public class Admin extends User implements Notifier {
    public Admin(String name, String password, String email) {
        super(name, password, email);
    }
    public Admin(String name, String email, int id) {
        super(name, email, id);
    }

    @Override
    public void NotifyUser(Notification notification){
        MySQL.insertIntoDatabase(notification);
    }

    @Override
    public void NotifyAll(ArrayList<Integer> subscribers, String message){
        for (Integer subscriberId : subscribers){
            NotifyUser(new Notification(subscriberId, message));
        }
    }
}

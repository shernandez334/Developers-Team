package org.example.database;

import org.example.model.Notification;

import java.util.ArrayList;

public interface Notifier {
    void NotifyUser(Notification notification);
    void NotifyAll(ArrayList<Integer> subscribers, String message);
}

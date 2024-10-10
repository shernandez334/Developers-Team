package org.example.database;

import org.example.entities.NotificationEntity;

import java.util.ArrayList;
import java.util.List;

public interface Notifier {
    void NotifyUser(NotificationEntity notification);
    void NotifyAll(List<Integer> subscribers, String message);
}

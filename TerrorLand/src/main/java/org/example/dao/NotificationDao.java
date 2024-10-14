package org.example.dao;

import org.example.entities.Notification;

import java.util.List;

public interface NotificationDao {
    List<Integer> getSubscribers();

    void saveNotification(Notification notification);

    void deleteNotification(Notification notification);
}

package org.example.services;

import org.example.dao.DatabaseFactory;
import org.example.entities.Player;
import org.example.entities.Notification;
import org.example.util.IO;

import java.util.List;
import java.util.ListIterator;

public class NotificationsService {

    public void readNotifications(Player player) {
        loadNotificationsFromDatabase(player);
        if (player.hasNoNotifications()){
            System.out.println("You have no messages.");
            return;
        }
        ListIterator<Notification> item = player.getNotifications().listIterator();
        char option = ' ';
        Notification notification = item.next();
        boolean backed = false;
        do {
            if (option == 'p' && item.previousIndex() > 0){
                item.previous();
                notification = item.previous();
                backed = true;
            }else if (option == 'n' && item.hasNext()){
                notification = item.next();
            }else if (option == 'd'){
                delete(notification);
                item.remove();
                if (player.hasNoNotifications()) {
                    System.out.println("You have no messages left.");
                    return;
                } else if (item.hasNext()){
                    notification = item.next();
                } else if (item.previousIndex() >= 0) {
                    notification = item.previous();
                    backed = true;
                }
            }
            if (backed){
                item.next();
                backed = false;
            }
            System.out.printf("%n%s%n", IO.indentText(notification.getMessage(), "**  "));
            System.out.printf("Previous(p)  -- %d/%d --  Next(n), Delete(d), Quit(q)%n",
                    item.nextIndex(), player.notificationsSize());
            do{
                option = IO.readChar(">");
            }while (!(option == 'p' || option == 'n' || option == 'd' || option == 'q'));
        } while(option != 'q' && player.hasNoNotifications());
    }

    private void delete(Notification notification) {
        DatabaseFactory.get().createNotificationDao().deleteNotification(notification);
    }

    public void notifySubscribers(String message) {
        List<Integer> subscribers = DatabaseFactory.get().createNotificationDao().getSubscribers();
        for (Integer subscriberId : subscribers){
            sendNotification(new Notification(subscriberId, message));
        }
    }

    public void sendNotification(Notification notification){
        DatabaseFactory.get().createNotificationDao().storeNotification(notification);
    }

    public void unsubscribe(Player player) {
        DatabaseFactory.get().createPlayerDao().unsubscribePlayer(player);
        player.setSubscribed(false);
    }

    public void subscribe(Player player) {
        DatabaseFactory.get().createPlayerDao().subscribePlayer(player);
        player.setSubscribed(true);
    }

    public void loadNotificationsFromDatabase(Player player){
        player.setNotifications(DatabaseFactory.get().createPlayerDao().retrieveNotifications(player.getId()));
    }

}

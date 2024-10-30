package org.example.services;

import org.example.database.DatabaseFactory;
import org.example.entities.Player;
import org.example.entities.Notification;
import org.example.services.rewards.Request;
import org.example.services.rewards.event.MailRecievedEvent;
import org.example.util.IOHelper;

import java.util.List;

public class NotificationsService {

    private final DatabaseFactory databaseFactory;

    public NotificationsService(DatabaseFactory databaseFactory){
        this.databaseFactory = databaseFactory;
    }

    public void readNotifications(Player player) {
        refreshNotificationsFromDatabase(player);
        List<Notification> notifications = player.getNotifications();
        if (notifications.isEmpty()) {
            System.out.println("You have no messages.");
            return;
        }
        int i = 0;
        char option = ' ';
        do {
            printNotification(notifications, i);
            do{
                option = IOHelper.readChar(">");
            }while (!(option == 'p' || option == 'n' || option == 'd' || option == 'q'));
            if (option == 'p' && i > 0){
                i--;
            } else if (option == 'n' && i < notifications.size() - 1){
                i++;
            } else if (option == 'd'){
                deleteNotification(notifications.get(i));
                notifications.remove(i);
                if (notifications.isEmpty()){
                    System.out.println("You have no messages left.");
                    return;
                }
                if (i > notifications.size() -1) i--;
            }
        }while (option != 'q');
    }

    private void printNotification(List<Notification> notifications, int i) {
        System.out.println(new StringBuilder().repeat(" * ", 20));
        System.out.printf("%n%s%n", IOHelper.indentText(notifications.get(i).getMessage(), "**  "));
        System.out.printf("Previous(p)  -- %d/%d --  Next(n), Delete(d), Quit(q)%n",
                i + 1, notifications.size());
    }

    private void deleteNotification(Notification notification) {
        databaseFactory.createNotificationDao().deleteNotification(notification);
    }

    public void notifySubscribers(String message) {
        List<Integer> subscribersIds = databaseFactory.createNotificationDao().getSubscribersIds();
        for (Integer subscriberId : subscribersIds){
            notifyPlayer(subscriberId, message);
        }
    }

    private Notification notifyPlayer(int playerId, String message){
        return databaseFactory.createNotificationDao().saveNotification(new Notification(playerId, message));
    }

    public void notifyAndUpdatePlayer(Player player, String message){
        Notification notification = notifyPlayer(player.getId(), message);
        player.addNotification(notification);
        RewardsService.getInstance().launchRewardChain(new Request(player, new MailRecievedEvent()));
    }

    public void removeSubscriber(Player player) {
        databaseFactory.createPlayerDao().unsubscribePlayer(player);
        player.setSubscribed(false);
    }

    public void addSubscriber(Player player) {
        databaseFactory.createPlayerDao().subscribePlayer(player);
        player.setSubscribed(true);
    }

    public void refreshNotificationsFromDatabase(Player player){
        player.setNotifications(databaseFactory.createPlayerDao().retrieveNotifications(player.getId()));
    }
}

package org.example.services;

import org.example.entities.PlayerEntity;
import org.example.entities.NotificationEntity;
import org.example.util.IO;

import java.util.ListIterator;

public class NotificationsService {

    public void readNotifications(PlayerEntity player) {
        player.loadNotificationsFromDatabase();
        if (player.hasNoNotifications()){
            System.out.println("You have no messages.");
            return;
        }
        ListIterator<NotificationEntity> item = player.getNotifications().listIterator();
        char option = ' ';
        NotificationEntity notification = item.next();
        boolean backed = false;
        do {
            if (option == 'p' && item.previousIndex() > 0){
                item.previous();
                notification = item.previous();
                backed = true;
            }else if (option == 'n' && item.hasNext()){
                notification = item.next();
            }else if (option == 'd'){
                notification.delete();
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

}

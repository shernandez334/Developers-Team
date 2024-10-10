package org.example.entities;

import org.example.dao.AdminDaoMySql;
import org.example.dao.NotificationDaoMySql;
import org.example.database.Notifier;
import java.math.BigDecimal;
import java.util.List;

public class AdminEntity extends UserEntity implements Notifier {
    public AdminEntity(String name, String password, String email) {
        super(name, password, email);
    }

    public AdminEntity(int id, String name, String password, String email) {
        super(id, name, password, email);
    }

    @Override
    public void NotifyUser(NotificationEntity notification){
        new NotificationDaoMySql().storeNotification(notification);
    }

    @Override
    public void NotifyAll(List<Integer> subscribers, String message){
        for (Integer subscriberId : subscribers){
            NotifyUser(new NotificationEntity(subscriberId, message));
        }
    }

    public BigDecimal getTotalIncome(){
        return new AdminDaoMySql().getTotalIncome();
    }
}

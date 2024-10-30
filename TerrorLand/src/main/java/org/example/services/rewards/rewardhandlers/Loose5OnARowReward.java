package org.example.services.rewards.rewardhandlers;

import org.example.dao.UserPlaysRoomDao;
import org.example.services.rewards.Request;
import org.example.services.rewards.event.RoomPlayedEvent;

public class Loose5OnARowReward extends PatternReward {


    public Loose5OnARowReward(int id) {
        super(id);
    }

    @Override
    protected void handleLogic(Request request) {
        if (request.getEvent() instanceof RoomPlayedEvent){
            UserPlaysRoomDao dao = super.getDatabaseFactory().createUserPlaysRoomDao();
            if(dao.getTotalPlays(request.getPlayer()) > 4 && dao.getSuccessfulPlays(request.getPlayer(), 5) == 0){
                super.grantReward(request.getPlayer());
            }
        }
    }
}

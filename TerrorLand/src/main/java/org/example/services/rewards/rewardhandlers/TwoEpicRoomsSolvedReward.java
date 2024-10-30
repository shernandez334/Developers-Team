package org.example.services.rewards.rewardhandlers;

import org.example.dao.UserPlaysRoomDao;
import org.example.enums.Difficulty;
import org.example.services.rewards.Request;
import org.example.services.rewards.event.RoomPlayedEvent;

public class TwoEpicRoomsSolvedReward extends BaseReward {


    public TwoEpicRoomsSolvedReward(int id) {
        super(id);
    }

    @Override
    protected void handleLogic(Request request) {
        if (request.getEvent() instanceof RoomPlayedEvent){
            UserPlaysRoomDao dao = super.getDatabaseFactory().createUserPlaysRoomDao();
            if(dao.getTotalPlays(request.getPlayer(), Difficulty.EPIC, true) > 1){
                super.grantReward(request.getPlayer());
            }
        }
    }
}

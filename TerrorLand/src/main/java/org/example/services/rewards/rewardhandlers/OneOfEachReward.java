package org.example.services.rewards.rewardhandlers;

import org.example.dao.UserPlaysRoomDao;
import org.example.enums.Difficulty;
import org.example.services.rewards.Request;
import org.example.services.rewards.event.RoomPlayedEvent;

public class OneOfEachReward extends BaseReward {


    public OneOfEachReward(int id) {
        super(id);
    }

    @Override
    protected void handleLogic(Request request) {
        if (request.getEvent() instanceof RoomPlayedEvent){
            UserPlaysRoomDao dao = super.getDatabaseFactory().createUserPlaysRoomDao();
            if(dao.getTotalPlays(request.getPlayer(), Difficulty.EASY) > 0 &&
                    dao.getTotalPlays(request.getPlayer(), Difficulty.MEDIUM) > 0 &&
                    dao.getTotalPlays(request.getPlayer(), Difficulty.HARD) > 0 &&
                    dao.getTotalPlays(request.getPlayer(), Difficulty.EPIC) > 0){
                super.grantReward(request.getPlayer());
            }
        }
    }
}

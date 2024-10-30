package org.example.services.rewards.rewardhandlers;

import org.example.dao.UserPlaysRoomDao;
import org.example.services.rewards.Request;
import org.example.services.rewards.event.RoomPlayedEvent;

public class CompleteAtThirdAttemptReward extends BaseReward {


    public CompleteAtThirdAttemptReward(int id) {
        super(id);
    }

    @Override
    protected void handleLogic(Request request) {
        if (request.getEvent() instanceof RoomPlayedEvent){
            UserPlaysRoomDao dao = super.getDatabaseFactory().createUserPlaysRoomDao();
            if(((RoomPlayedEvent) request.getEvent()).isSuccess() &&
                    dao.getTotalPlays(request.getPlayer()) > 2 &&
                    dao.getSuccessfulPlays(request.getPlayer(),
                            ((RoomPlayedEvent) request.getEvent()).getRoom().getRoomId(), 3) == 1){
                super.grantReward(request.getPlayer());
            }
        }
    }
}

package org.example.services.rewards.rewardhandlers;

import org.example.services.rewards.Request;
import org.example.services.rewards.event.RoomPlayedEvent;

public class ThreeRoomsPlayedReward extends PatternReward {


    public ThreeRoomsPlayedReward(int id) {
        super(id);
    }

    @Override
    protected void handleLogic(Request request) {
        if (request.getEvent() instanceof RoomPlayedEvent){
            if(super.getDatabaseFactory().createUserPlaysRoomDao().getTotalPlays(request.getPlayer()) >= 3){
                super.grantReward(request.getPlayer());
            }
        }
    }
}

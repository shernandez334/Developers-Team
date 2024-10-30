package org.example.services.rewards.rewardhandlers;

import org.example.services.rewards.Request;
import org.example.services.rewards.event.RoomPlayedEvent;

public class PlayedFirstRoomReward extends BaseReward {


    public PlayedFirstRoomReward(int id) {
        super(id);
    }

    @Override
    protected void handleLogic(Request request) {
        if (request.getEvent() instanceof RoomPlayedEvent){
            super.grantReward(request.getPlayer());
        }
    }
}

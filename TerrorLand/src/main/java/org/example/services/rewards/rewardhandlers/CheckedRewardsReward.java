package org.example.services.rewards.rewardhandlers;

import org.example.services.rewards.Request;
import org.example.services.rewards.event.CheckedRewardsEvent;

public class CheckedRewardsReward extends PatternReward {


    public CheckedRewardsReward(int id) {
        super(id);
    }

    @Override
    protected void handleLogic(Request request) {
        if (request.getEvent() instanceof CheckedRewardsEvent) {
            super.grantReward(request.getPlayer());
        }
    }
}

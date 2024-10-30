package org.example.services.rewards.rewardhandlers;

import org.example.services.rewards.Request;
import org.example.services.rewards.event.CreateCertificateEvent;

public class CreateCertificateReward extends BaseReward {


    public CreateCertificateReward(int id) {
        super(id);
    }

    @Override
    protected void handleLogic(Request request) {
        if (request.getEvent() instanceof CreateCertificateEvent) {
            super.grantReward(request.getPlayer());
        }
    }
}

package org.example.services.rewards.rewardhandlers;

import org.example.services.rewards.Request;
import org.example.services.rewards.event.MailRecievedEvent;

public class InboxWith5MessagesReward extends PatternReward {


    public InboxWith5MessagesReward(int id) {
        super(id);
    }

    @Override
    protected void handleLogic(Request request) {
        if (request.getEvent() instanceof MailRecievedEvent){
            if (request.getPlayer().getNotifications().size() >= 5) {
                super.grantReward(request.getPlayer());
            }
        }
    }
}

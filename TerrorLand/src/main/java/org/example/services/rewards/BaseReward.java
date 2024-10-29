package org.example.services.rewards;

import org.example.entities.Player;

public abstract class BaseReward implements RewardHandler{

    int id;
    RewardHandler next;

    public BaseReward(int id){
        this.id = id;
    }

    @Override
    public RewardHandler setNext(RewardHandler rewardHandler) {
        this.next = rewardHandler;
        return rewardHandler;
    }

    @Override
    public void handleNext(Request request){
        if (this.next != null) next.handle(request);
    }

    public abstract void handle(Request request);
}

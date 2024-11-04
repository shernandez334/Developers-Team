package org.example.services.rewards;

public interface RewardHandler {

    RewardHandler setNext(RewardHandler rewardHandler);
    void handleNext(Request request);
    void handle(Request request);

}

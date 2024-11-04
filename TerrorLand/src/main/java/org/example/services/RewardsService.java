package org.example.services;

import org.example.database.DatabaseFactory;
import org.example.entities.Player;
import org.example.services.rewards.*;
import org.example.services.rewards.event.CheckedRewardsEvent;
import org.example.services.rewards.rewardhandlers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RewardsService {

    private final Logger log = LoggerFactory.getLogger(RewardsService.class);
    private RewardHandler firstReward;
    private DatabaseFactory databaseFactory;
    private static RewardsService instance;

    private RewardsService() {
    }

    public static RewardsService getInstance(){
        if (instance == null) instance = new RewardsService();
        return instance;
    }

    public void setChainOfResponsibility(DatabaseFactory databaseFactory){
        this.databaseFactory = databaseFactory;
        log.debug("Configuring rewards chain of responsibility.");
        this.firstReward = new PlayedFirstRoomReward(1);
        this.firstReward.setNext(new OneOfEachDifficultyReward(2))
                .setNext(new ThreeRoomsPlayedReward(3))
                .setNext(new TwoEpicRoomsSolvedReward(4))
                .setNext(new CreateCertificateReward(5))
                .setNext(new InboxWith5MessagesReward(6))
                .setNext(new CompleteAtThirdAttemptReward(7))
                .setNext(new Loose5OnARowReward(8))
                .setNext(new BuyManyTicketsReward(9))
                .setNext(new CheckedRewardsReward(10));
    }

    public void launchRewardChain(Request request){
        log.debug("Starting rewards check chain.");
        firstReward.handle(request);
        log.debug("Completed rewards check chain.");
    }


    public void refreshRewardsFromDatabase(Player player) {
        player.setRewards(databaseFactory.createRewardDao().getPlayerRewards(player.getId()));
    }

    public void seeRewards(Player player) {
        launchRewardChain(new Request(player, new CheckedRewardsEvent()));
        System.out.println("Your rewards:");
        player.getRewards().forEach(reward -> System.out.printf("- %s - %20s (%s)%n", reward.getBanner(), reward.getName(),
                reward.getDescription()));
    }
}

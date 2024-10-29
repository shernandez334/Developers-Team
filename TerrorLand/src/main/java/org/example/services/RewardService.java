package org.example.services;

import org.example.dao.DatabaseFactory;
import org.example.entities.Player;
import org.example.services.rewards.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RewardService {

    private static final Logger log = LoggerFactory.getLogger(RewardService.class);
    private RewardHandler firstReward;
    private final DatabaseFactory databaseFactory;

    public RewardService(DatabaseFactory databaseFactory) {
        this.databaseFactory = databaseFactory;
    }

    public void configureChainOfResponsibility(){
        log.debug("Configuring rewards chain of responsibility.");
        this.firstReward = new PlayedFirstRoomReward(1)
                .setNext(new OneOfEachReward(2))
                .setNext(new ThreeRoomsPlayedReward(3))
                .setNext(new TwoEpicRoomsSolvedReward(4))
                .setNext(new CreateCertificateReward(5));
    }

    public void startRewardChain(Request request){
        log.debug("Starting rewards check chain.");
        firstReward.handle(request);
        log.debug("Completed rewards check chain.");
    }


    public void refreshRewardsFromDatabase(Player player) {
        player.setRewards(databaseFactory.createPlayerDao().retrieveRewards(player.getId()));
    }
}

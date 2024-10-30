package org.example.services.rewards.rewardhandlers;

import org.example.database.DatabaseFactory;
import org.example.database.FactoryProvider;
import org.example.entities.Player;
import org.example.entities.Reward;
import org.example.services.NotificationsService;
import org.example.services.rewards.Request;
import org.example.services.rewards.RewardHandler;

public abstract class PatternReward implements RewardHandler {

    private int id;
    private RewardHandler next;
    private DatabaseFactory databaseFactory;

    public PatternReward(int id) {
        this.id = id;
        this.databaseFactory = FactoryProvider.getInstance().getDbFactory();
    }

    public DatabaseFactory getDatabaseFactory() {
        return databaseFactory;
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

    @Override
    public void handle(Request request){
        if (!request.getPlayer().getRewards().contains(new Reward(id))){
            handleLogic(request);
        }
        handleNext(request);
    }

    protected abstract void handleLogic(Request request);

    protected void grantReward(Player player){
        Reward reward = getReward(this.id);
        player.addReward(reward);
        databaseFactory.createRewardDao().saveReward(reward, player);
        new NotificationsService(databaseFactory)
                .notifyAndUpdateSubscriber(player, "You have earned a badge!");
    }

    private Reward getReward(int rewardId){
        return databaseFactory.createRewardDao().getReward(rewardId);
    }

}

package org.example.dao;

import org.example.entities.Player;
import org.example.entities.Reward;

import java.util.Set;

public interface RewardDao {

    void saveReward(Reward reward, Player player);

    Reward getReward(int rewardId);

    Set<Reward> getPlayerRewards(int id);
}

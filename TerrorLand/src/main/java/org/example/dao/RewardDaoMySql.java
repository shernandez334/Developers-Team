package org.example.dao;

import org.example.entities.Notification;
import org.example.entities.Player;
import org.example.entities.Reward;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.example.mysql.MySqlHelper.*;

public class RewardDaoMySql implements RewardDao {

    @Override
    public void saveReward(Reward reward, Player player) {
        String sql = String.format("INSERT INTO user_has_reward (user_id, reward_id) VALUES (%d, '%d');",
                player.getId(), reward.getId());
        try {
            createStatementAndExecute(sql);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Reward getReward(int rewardId) {
        Set<Reward> response = new HashSet<>();
        List<List<Object>> items = retrieveMultipleColumnsFromDatabase(
                String.format("SELECT name, description, banner "+
                        "FROM reward " +
                        "WHERE reward_id = %d;", rewardId),
                new String[] {String.class.getName(), String.class.getName(), String.class.getName()});
        List<Object> e = items.getFirst();
        return new Reward(rewardId, (String) e.getFirst(), (String) e.get(1), (String) e.get(2), null);
    }

    @Override
    public Set<Reward> getPlayerRewards(int playerId) {
        Set<Reward> response = new HashSet<>();
        List<List<Object>> items = retrieveMultipleColumnsFromDatabase(
                String.format("SELECT r.reward_id, r.name, r.description, r.banner, u.date " +
                        "FROM reward r JOIN user_has_reward u USING (reward_id) " +
                        "WHERE user_id = %d;", playerId),
                new String[] {Integer.class.getName(), String.class.getName(), String.class.getName(),
                        String.class.getName(), LocalDateTime.class.getName()});
        items.forEach(e -> response.add(new Reward((int) e.getFirst(), (String) e.get(1), (String) e.get(2),
                (String) e.get(3), (LocalDateTime) e.get(4))));
        return response;
    }
}

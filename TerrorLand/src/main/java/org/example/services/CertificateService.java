package org.example.services;

import org.example.dao.DatabaseFactory;
import org.example.entities.Player;
import org.example.entities.UserPlaysRoomDto;
import org.example.services.rewards.Request;
import org.example.services.rewards.event.CreateCertificateEvent;

import java.util.Set;

public class CertificateService {

    private final DatabaseFactory databaseFactory;

    public CertificateService(DatabaseFactory databaseFactory) {
        this.databaseFactory = databaseFactory;
    }

    public void getCertificate(Player player) {
        String template1 = """
                TerrorLand certifies that **%s**:
                Has played %d rooms with a %.2f%% rate of success.
                """;
        String template12 = """
                TerrorLand certifies that **%s**:
                Has no registered plays.
                """;
        String template2 = "Room %d: %d plays.%n";
        UserPlaysRoomDto userPlaysRoomDto = databaseFactory.createUserPlaysRoomDao().getPlays(player);
        Set<Integer> roomsPlayed = userPlaysRoomDto.getAttempts().keySet();
        int totalPlays = roomsPlayed.stream().mapToInt(p -> userPlaysRoomDto.getAttempts().get(p).size()).sum();
        int totalSuccessfulPlays = roomsPlayed.stream()
                .mapToInt(p -> (int) userPlaysRoomDto
                        .getAttempts().get(p).stream()
                        .filter(a -> a.isSuccess())
                        .count())
                .sum();
        StringBuilder response = new StringBuilder();
        if (totalPlays > 0) {
            response.append(String.format(template1,
                    player.getName(), totalPlays, totalSuccessfulPlays * 100.0 / totalPlays));
            roomsPlayed.forEach(p -> response.append(
                    String.format(template2, p, userPlaysRoomDto.getAttempts().get(p).size())));
        }else {
            response.append(String.format(template12, player.getName()));
        }
        System.out.println("Briefly you will receive a notification with your certificate. Check your inbox!");
        NotificationsService notificationsService = new NotificationsService(databaseFactory);
        notificationsService.notifyAndUpdateSubscriber(player, response.toString());
        RewardService.getInstance().launchRewardChain(new Request(player, new CreateCertificateEvent()));
    }
}

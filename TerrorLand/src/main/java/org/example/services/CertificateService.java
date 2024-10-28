package org.example.services;

import org.example.dao.DatabaseFactory;
import org.example.entities.Player;
import org.example.entities.UserPlaysRoomDto;

import java.util.Set;

public class CertificateService {

    private final DatabaseFactory databaseFactory;

    public CertificateService(DatabaseFactory databaseFactory) {
        this.databaseFactory = databaseFactory;
    }

    public void getCertificate(Player player) {
        String template = """
                TerrorLand certifies that %s:
                Has played %d rooms with a %.2f%% rate of success.%n
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
        StringBuilder response = new StringBuilder(String.format(template,
                player.getName(), totalPlays, totalSuccessfulPlays * 100.0 /totalPlays));
        roomsPlayed.forEach(p -> response.append(
                String.format(template2, p, userPlaysRoomDto.getAttempts().get(p).size())));
        System.out.println("Briefly you will receive a notification with your certificate. Check your inbox!");
        new NotificationsService(databaseFactory).notifySubscriber(player.getId(), response.toString());
    }
}

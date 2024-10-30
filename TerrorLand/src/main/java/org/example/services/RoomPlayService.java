package org.example.services;

import org.example.dao.*;
import org.example.entities.Element;
import org.example.entities.Player;
import org.example.entities.Room;
import org.example.enums.ConfigurableProperty;
import org.example.enums.Properties;
import org.example.enums.RoomStatus;
import org.example.exceptions.IdNotFoundException;
import org.example.services.rewards.Request;
import org.example.services.rewards.event.RoomPlayedEvent;
import org.example.util.IOHelper;

import java.util.List;

public class RoomPlayService {

    private final DatabaseFactory databaseFactory;
    //TODO injection RoomDao and UserPlaysRoomDao
    public RoomPlayService(DatabaseFactory databaseFactory){
        this.databaseFactory = databaseFactory;
    }

    public void play(Player player) {

        if (player.getTicketsSize() < 1) {
            System.out.println("Get some tickets first!");
            return;
        }

        try {
            Room room = roomSelectionDialog();

            new TicketsService(databaseFactory).cashTicket(player);

            boolean solved = playRoom(room);

            if (solved) {
                System.out.println("Congrats, you have solved the room in time!");
                addPlayerRoomSolved(player, room);
            }else {
                System.out.println("Time is up! Try again later.");
                addPlayerRoomNotSolved(player, room);
            }

            RewardService.getInstance().launchRewardChain(new Request(player, new RoomPlayedEvent(solved, room)));

        } catch (IdNotFoundException e) {
            System.out.println("There is no room with such id.");
        }


    }


    private Room roomSelectionDialog() throws IdNotFoundException {
        final DisplayDao DISPLAY = new DisplayDao();
        DISPLAY.displayAllRooms(RoomStatus.ACTIVE);
        int roomId = IOHelper.readInt("Select the room to play: ");
        Room room= new RoomDao().getRoomFromId(roomId);
        if (room.getDeleted() == 1) throw new IdNotFoundException("Deleted rooms are not available to play.");
        return room;
    }

    private List<String> getElementsList(Room room) {
        List<Element> elements = new RoomDao().retrieveRoomElements(room);
        return elements.stream().map(Element::getName).toList();
    }

    private boolean playRoom(Room room) {
        List<String> elements= getElementsList(room);
        int countdown = Integer.parseInt(Properties.getProperty(ConfigurableProperty.PLAY_TIME));
        boolean solved = false;
        while (countdown > 0 && !solved){
            if (!elements.isEmpty()){
                System.out.println("You inspect a " + elements.get((int) (Math.random() * elements.size())) + ".");
            }else {
                System.out.println("You inspect the room but it looks empty");
            }
            solved = IOHelper.readYorN("Select (Y) to solve the room, (N) to keep playing.");
            countdown--;
        }
        return solved;
    }

    private void addPlayerRoomSolved(Player player, Room room) {
        UserPlaysRoomDao userPlaysRoomDao = new UserPlaysRoomDaoMySql();
        userPlaysRoomDao.savePlay(player, room, true);
    }

    private void addPlayerRoomNotSolved(Player player, Room room) {
        UserPlaysRoomDao userPlaysRoomDao = new UserPlaysRoomDaoMySql();
        userPlaysRoomDao.savePlay(player, room, false);
    }
}

package org.example.services;

import org.example.dao.DatabaseFactory;
import org.example.entities.Player;
import org.example.entities.Room;
import org.example.enums.ConfigurableProperty;
import org.example.enums.Difficulty;
import org.example.enums.Properties;
import org.example.util.IOHelper;

import java.util.ArrayList;
import java.util.List;

public class RoomPlayService {

    private final DatabaseFactory databaseFactory;

    public RoomPlayService(DatabaseFactory databaseFactory){
        this.databaseFactory = databaseFactory;
    }

    public void play(Player player) {

        if (! new TicketsService(databaseFactory).cashTicket(player)) {
            System.out.println("Get some tickets first!");
            return;
        }

        Room room = roomSelectionDialog();
        List<String> elements= getElementsList(room);

        boolean solved = playRoom(room, elements, Integer.parseInt(Properties.getProperty(ConfigurableProperty.PLAY_TIME)));

        if (solved) {
            System.out.println("Congrats, you have solved the room in time!");
            addPlayerRoomSolved(player, room);
        }else {
            System.out.println("Time is up! Try again later.");
            addPlayerRoomNotSolved(player, room);
        }
    }



    //TODO show available room and user selects one by id
    private Room roomSelectionDialog() {
        return new Room(1, "MockRoom", Difficulty.EASY, 0);
    }

    //TODO get elements present in room
    private List<String> getElementsList(Room room) {
        return new ArrayList<>(List.of(new String[] {"MockClue1", "MockDeco1", "MockDeco2"}));
    }

    private boolean playRoom(Room room, List<String> elements, int countdown) {
        boolean solved = false;
        while (countdown > 0 && !solved){
            System.out.println("You inspect a " + elements.get((int) (Math.random() * elements.size())) + ".");
            solved = IOHelper.readYorN("Select (Y) to solve the room, (N) to keep playing.");
            countdown--;
        }
        return solved;
    }

    //TODO add registry in Db player_plays_room ( solved)
    private void addPlayerRoomSolved(Player player, Room room) {
    }

    //TODO add registry in Db player_plays_room (not solved)
    private void addPlayerRoomNotSolved(Player player, Room room) {
    }
}

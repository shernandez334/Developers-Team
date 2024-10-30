package org.example.AdminMenu;

import org.example.database.FactoryProvider;
import org.example.dao.RoomDao;
import org.example.entities.Clue;
import org.example.entities.Decoration;
import org.example.entities.Room;
import org.example.dao.DatabaseInputDaoSQL;
import org.example.dao.ElementDaoSql;
import org.example.services.NotificationsService;
import org.example.util.IOHelper;
import org.example.util.MenuHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElementCreator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomManagerMenu.class);
    private static final ElementDaoSql ELEMCREATOR = new ElementDaoSql();
    private static final RoomDao ROOMCREATOR = new RoomDao();
    private static final DatabaseInputDaoSQL DATABASEINPUT = new DatabaseInputDaoSQL();


    public void createRoomHasElements() {
        int op;
        Room room = ROOMCREATOR.createElementRoom();
        do {
            op = MenuHelper.readSelection("You must create at least one element: ",
                    ">", "1. Decoration", "2. Clue", "3. Exit");

            switch (op) {
                case 1 -> addDecorationToRoomHasElement(room);
                case 2 -> addClueToRoomHasElement(room);
                case 3 -> op = checkIfRoomHasElement(room);
            }
        } while (op != 3);
        notifyRoomCreation(room);
        room.reset();
    }

    private void notifyRoomCreation(Room room) {
        new NotificationsService(FactoryProvider.getInstance().getDbFactory()).notifySubscribers(String.format(
            """
            Dear Escape Room Enthusiasts,
            We’re excited to announce the launch of our brand-new
            virtual escape room, “%s” 🧙‍♂️✨
            Play this %s room and solve puzzles, uncover secrets, and
            navigate through a magical room filled with challenges!
            """
                , room.getNameRoom(), room.getDifficulty().toString().toLowerCase()
        ));
    }

    public void addDecorationToRoomHasElement(Room room){
        Decoration decor = ELEMCREATOR.createDecoration();
        DATABASEINPUT.inputDecorationIntoTable(decor.getElementId(), decor.getMaterial());
        int quantity = IOHelper.readInt("Quantity: ");
        DATABASEINPUT.inputRoomHasElementIntoTable(room.getRoomId(), decor.getElementId(), quantity);
    }

    public void addClueToRoomHasElement(Room room){
        Clue clue = ELEMCREATOR.createClue();
        DATABASEINPUT.inputClueIntoTable(clue.getElementId(), clue.getTheme());
        int quantity = IOHelper.readInt("Quantity: ");
        DATABASEINPUT.inputRoomHasElementIntoTable(room.getRoomId(), clue.getElementId(), quantity);
    }

    public int checkIfRoomHasElement(Room room) {
        int roomId = room.getRoomId();
        int op = 3;
        boolean hasElement = DATABASEINPUT.doesRoomHaveElements(roomId);
        if (!hasElement) {
            LOGGER.warn("No elements found in this room. You have to assign an element to the room...");
            op = 0;
        }
        return op;
    }
}
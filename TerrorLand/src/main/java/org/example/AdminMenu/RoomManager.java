package org.example.AdminMenu;

import org.example.util.MenuHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoomManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomManager.class);
    private static final ElementDisplay DISPLAY = new ElementDisplay();
    private static final ElementCreator CREATOR = new ElementCreator();
    private static final RoomStatusManager VISIBILITY = new RoomStatusManager();

    public void manageRooms(){
        int op = 0;
        do{
            op = MenuHelper.readSelection("Choose an option: ", ">",
                    "1. Display Rooms",
                            "2. Create Room",
                            "3. Delete Room",
                            "4. Retrieve Deleted Room",
                            "5. Return to Admin Menu");
            switch (op){
                case 1 -> DISPLAY.displayRooms(0);
                case 2 -> CREATOR.createRoomHasElements();
                case 3 -> VISIBILITY.modifyRoomStatus(0, 1);
                case 4 -> VISIBILITY.modifyRoomStatus(1, 0);
                case 5 -> LOGGER.info("Returning to the Admin Menu");
            }
        } while(op != 5);
    }
}


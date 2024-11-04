package org.example.AdminMenu;

import org.example.dao.DisplayDao;
import org.example.dao.RoomDao;
import org.example.dao.StockDao;
import org.example.util.MenuHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoomManagerMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomManagerMenu.class);
    private static final DisplayDao DISPLAY = new DisplayDao();
    private static final ElementCreator CREATOR = new ElementCreator();
    private static final RoomDao ROOM_DAO = new RoomDao();
    private static final StockDao STOCK_DAO = new StockDao();

    public void manageRooms(){
        int op = 0;
        do{
            op = MenuHelper.readSelection("Choose an option: ", ">",
                    "1. Display Rooms",
                            "2. Display Elements",
                            "3. Create Room",
                            "4. Delete Room",
                            "5. Retrieve Deleted Room",
                            "6. Show Inventory Value",
                            "7. Return to Admin Menu");
            switch (op){
                case 1 -> DISPLAY.displayActiveRooms();
                case 2 -> DISPLAY.displayActiveRoomElements();
                case 3 -> CREATOR.createRoomHasElements();
                case 4 -> ROOM_DAO.deleteRoom();
                case 5 -> ROOM_DAO.retrieveRoom();
                case 6 -> STOCK_DAO.displayTotalPriceOfAllElementsInRooms();
                case 7 -> LOGGER.info("Returning to the Admin Menu");
            }
        } while(op != 7);
    }
}
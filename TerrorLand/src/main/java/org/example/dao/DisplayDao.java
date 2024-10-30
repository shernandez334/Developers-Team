package org.example.dao;

import org.example.enums.RoomStatus;
import org.example.factory.DisplayFactory;
import org.example.util.IOHelper;
import org.example.util.OutputBuilder;
import org.example.mysql.Queries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.mysql.MySqlHelper.getConnection;

public class DisplayDao implements DisplayFactory{
    private static final Logger LOGGER = LoggerFactory.getLogger(DisplayDao.class);
    private static final OutputBuilder OUTPUT_BUILDER = new OutputBuilder();
    private static StringBuilder displayOutput = new StringBuilder();

    public void displayActiveRooms(){
        displayAllRooms(RoomStatus.ACTIVE);
    }

    public void displayDeletedRooms(){
        displayAllRooms(RoomStatus.DELETED);
    }

    public void displayAllRooms(RoomStatus roomStatus) {
        String roomsQuery = Queries.buildDisplayAllRoomsQuery() + (roomStatus.equals(RoomStatus.ACTIVE)
                ? "WHERE r.deleted = 0;" : "WHERE r.deleted = 1;");
        try (Connection conn = getConnection("escape_room");
             PreparedStatement psmt = conn.prepareStatement(roomsQuery)){
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                displayOutput.setLength(0);
                String roomDetails = OUTPUT_BUILDER.buildRoomOutput(rs);
                System.out.println(roomDetails);
            }
        } catch (SQLException e) {
            LOGGER.error("The room display couldn't be shown: {}",e.getMessage());
        }
    }

    public void displayActiveRoomElements(){
        displayRoomElements(RoomStatus.ACTIVE);
    }

    public void displayDeletedRoomElements(){
        displayRoomElements(RoomStatus.DELETED);
    }

    public void displayRoomElements(RoomStatus roomStatus){
        int room_id;
        do {
            displayAllRooms(roomStatus);
            room_id = IOHelper.readInt("Enter the room ID for the items you wish to view.\n" +
                    "To Exit, type 0: \n");
            if (room_id != 0){
                displayElements(room_id, roomStatus);
                if (!askToContinue()){
                    room_id = 0;
                }
            }
        } while (room_id != 0);
        System.out.println("Exit");
    }

    public void displayElements(int roomId, RoomStatus roomStatus) {
        String elementQuery = Queries.buildDisplayElementQuery();
        try (Connection conn = getConnection("escape_room");
             PreparedStatement psmt = conn.prepareStatement(elementQuery)) {
            psmt.setInt(1, roomStatus == RoomStatus.ACTIVE ? 0 : 1);
            psmt.setInt(2, roomId);
            ResultSet rs = psmt.executeQuery();
            while (rs.next()) {
                String elementDetails = OUTPUT_BUILDER.buildElementOutput(rs);
                System.out.println(elementDetails);
            }
        } catch (SQLException e) {
            LOGGER.error("The elements display couldn't be shown: {}", e.getMessage());
        }
    }

    public boolean askToContinue(){
        boolean isContinuing = false;
        int continueOption = IOHelper.readInt("Do you wish to continue consulting elements?\n" +
                "0. No\n" +
                "1. Yes\n");
        if (continueOption == 1){
            isContinuing = true;
        }
        return isContinuing;
    }
}
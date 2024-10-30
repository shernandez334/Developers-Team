package org.example.dao;

import org.example.entities.Element;
import org.example.entities.Room;
import org.example.enums.Difficulty;
import org.example.enums.RoomStatus;
import org.example.enums.Type;
import org.example.exceptions.IdNotFoundException;
import org.example.factory.RoomFactory;
import org.example.mysql.QueryResult;
import org.example.util.IOHelper;
import org.example.util.MenuHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.mysql.MySqlHelper.createStatementAndExecuteQuery;
import static org.example.mysql.MySqlHelper.getConnection;


public class RoomDao implements RoomFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomDao.class);
    private static final DisplayDao DISPLAY = new DisplayDao();
    private static final DatabaseInputDaoSQL DATABASEINPUT = new DatabaseInputDaoSQL();

    public Room createElementRoom(){
        String name = IOHelper.readString("Name of the room: ");
        Difficulty difficulty = MenuHelper.readDifficultySelection("Choose a level of difficulty: ");
        DATABASEINPUT.inputRoomIntoTable(name, difficulty, 0);
        int room_id = getCurrentRoomId();
        System.out.println(room_id);
        return new Room(room_id, name, difficulty, 0);
    }

    public int getCurrentRoomId() {
        int currentRoomId = 0;
        String sql = "SELECT room_id FROM room ORDER BY room_id DESC LIMIT 1";
        try (Connection conn = getConnection("escape_room");
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            rs.next();
            currentRoomId = rs.getInt("room_id");
        } catch (SQLException e) {
            LOGGER.error("Couldn't get the next element_id number: {}", e.getMessage());
        }
        return currentRoomId;
    }

    public void deleteRoom(){
        DISPLAY.displayAllRooms(RoomStatus.ACTIVE);
        int roomId = IOHelper.readInt("Enter the Room Id you want to delete: ");
        updateRoomStatus(roomId, 1);
    }

    public void retrieveRoom(){
        DISPLAY.displayAllRooms(RoomStatus.DELETED);
        int roomId = IOHelper.readInt("Enter the Room Id you want to delete: ");
        updateRoomStatus(roomId, 0);
    }

    public void updateRoomStatus(int roomId, int newRoomStatus){
        String updateQuery = "UPDATE room SET deleted = ? WHERE room_id = ?";
        try (Connection conn = getConnection("escape_room");
             PreparedStatement psmt = conn.prepareStatement(updateQuery)) {
            psmt.setInt(1, newRoomStatus);
            psmt.setInt(2, roomId);
            int rowsAffected = psmt.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.info("Room with ID {} has been deleted.", roomId);
            } else {
                LOGGER.error("No room found with ID {}.", roomId);
            }
        } catch (SQLException e) {
            LOGGER.error("Error updating room deleted status: {}", e.getMessage());
        }
    }

    public Room getRoomFromId(int roomId) throws IdNotFoundException {
        String sql = String.format("SELECT name, difficulty, deleted FROM room WHERE room_id = '%s'",
                roomId);
        try (QueryResult queryResult = createStatementAndExecuteQuery(sql)){
            ResultSet result = queryResult.getResultSet();
            if (result.next()){
                String name = result.getString("name");
                Difficulty difficulty = Difficulty.valueOf(result.getString("difficulty"));
                int deleted = result.getInt("deleted");
                return new Room(roomId, name, difficulty, deleted);
            }
            throw new IdNotFoundException("There is no room with such id");
        } catch (SQLException e) {
            LOGGER.error("Error retrieving room: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<Element> retrieveRoomElements(Room room) {
        List<Element> elements = new ArrayList<>();
        String sql = String.format("SELECT e.element_id, name, price, type FROM element e JOIN room_has_element r " +
                "ON e.element_id = r.element_id WHERE deleted = 1 AND room_id = '%d';", room.getRoomId());
        try (QueryResult queryResult = createStatementAndExecuteQuery(sql)){
            ResultSet resultSet = queryResult.getResultSet();
            while (resultSet.next()){
                elements.add(new Element(resultSet.getInt("element_id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        Type.valueOf(resultSet.getString("type"))));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return elements;
    }

}
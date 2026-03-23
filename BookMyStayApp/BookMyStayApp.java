import java.util.HashMap;
import java.util.Map;

abstract class Room {
    public abstract void displayRoomDetails();
}

class SingleRoom extends Room {
    public void displayRoomDetails() {
        System.out.println("Room Type: Single");
        System.out.println("Capacity: 1 person");
        System.out.println("Price: $50/night");
    }
}

class DoubleRoom extends Room {
    public void displayRoomDetails() {
        System.out.println("Room Type: Double");
        System.out.println("Capacity: 2 persons");
        System.out.println("Price: $80/night");
    }
}

class SuiteRoom extends Room {
    public void displayRoomDetails() {
        System.out.println("Room Type: Suite");
        System.out.println("Capacity: 4 persons");
        System.out.println("Price: $150/night");
    }
}

class RoomInventory {
    private final Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        SingleRoom singleRoom = new SingleRoom();
        DoubleRoom doubleRoom = new DoubleRoom();
        SuiteRoom suiteRoom = new SuiteRoom();

        System.out.println("Single Room:");
        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + inventory.getRoomAvailability().get("Single"));
        System.out.println();

        System.out.println("Double Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + inventory.getRoomAvailability().get("Double"));
        System.out.println();

        System.out.println("Suite Room:");
        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + inventory.getRoomAvailability().get("Suite"));
    }
}
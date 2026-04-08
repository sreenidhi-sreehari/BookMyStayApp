import java.util.*;

public class BookMyStayApp {

    static class RoomInventory {
        private Map<String, Integer> rooms;

        public RoomInventory() {
            rooms = new HashMap<>();
            rooms.put("Single", 5);
            rooms.put("Double", 3);
            rooms.put("Suite", 2);
        }

        public void increaseRoom(String roomType) {
            rooms.put(roomType, rooms.get(roomType) + 1);
        }

        public int getAvailability(String roomType) {
            return rooms.get(roomType);
        }
    }

    static class CancellationService {

        private Map<String, String> reservationRoomTypeMap;
        private Stack<String> rollbackStack;

        public CancellationService() {
            reservationRoomTypeMap = new HashMap<>();
            rollbackStack = new Stack<>();
        }

        public void registerBooking(String reservationId, String roomType) {
            reservationRoomTypeMap.put(reservationId, roomType);
        }

        public void cancelBooking(String reservationId, RoomInventory inventory) {
            if (!reservationRoomTypeMap.containsKey(reservationId)) {
                System.out.println("Invalid reservation ID");
                return;
            }

            String roomType = reservationRoomTypeMap.get(reservationId);
            inventory.increaseRoom(roomType);
            rollbackStack.push(reservationId);
            reservationRoomTypeMap.remove(reservationId);

            System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
        }

        public void showRollbackHistory() {
            System.out.println();
            System.out.println("Rollback History (Most Recent First):");
            Stack<String> temp = new Stack<>();
            while (!rollbackStack.isEmpty()) {
                String id = rollbackStack.pop();
                System.out.println("Released Reservation ID: " + id);
                temp.push(id);
            }
            while (!temp.isEmpty()) {
                rollbackStack.push(temp.pop());
            }
        }
    }

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();

        String reservationId = "Single-1";
        String roomType = "Single";

        service.registerBooking(reservationId, roomType);

        System.out.println("Booking Cancellation");

        service.cancelBooking(reservationId, inventory);

        service.showRollbackHistory();

        System.out.println();
        System.out.println("Updated " + roomType + " Room Availability: " + inventory.getAvailability(roomType));
    }
}
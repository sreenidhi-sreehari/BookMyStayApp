import java.util.*;

public class BookMyStayApp {

    static class InvalidBookingException extends Exception {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    static class RoomInventory {
        private Map<String, Integer> rooms;

        public RoomInventory() {
            rooms = new HashMap<>();
            rooms.put("Single", 2);
            rooms.put("Double", 2);
            rooms.put("Suite", 1);
        }

        public boolean isAvailable(String roomType) {
            return rooms.containsKey(roomType) && rooms.get(roomType) > 0;
        }

        public void bookRoom(String roomType) {
            rooms.put(roomType, rooms.get(roomType) - 1);
        }
    }

    static class ReservationValidator {
        public void validate(String guestName, String roomType, RoomInventory inventory) throws InvalidBookingException {
            if (guestName == null || guestName.trim().isEmpty()) {
                throw new InvalidBookingException("Guest name cannot be empty.");
            }
            if (!inventory.isAvailable(roomType)) {
                throw new InvalidBookingException("Invalid room type selected.");
            }
        }
    }

    static class BookingRequestQueue {
        private Queue<String> queue;

        public BookingRequestQueue() {
            queue = new LinkedList<>();
        }

        public void addRequest(String request) {
            queue.add(request);
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        try {
            System.out.println("Booking Validation");
            System.out.print("Enter guest name: ");
            String name = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            validator.validate(name, roomType, inventory);

            inventory.bookRoom(roomType);
            bookingQueue.addRequest(name + " - " + roomType);

            System.out.println("Booking successful");

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
import java.util.*;

public class BookMyStayApp {

    static class Reservation {
        String guestName;
        String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }
    }

    static class BookingRequestQueue {
        private Queue<Reservation> queue = new LinkedList<>();

        public synchronized void addRequest(Reservation r) {
            queue.add(r);
        }

        public synchronized Reservation getRequest() {
            return queue.poll();
        }
    }

    static class RoomInventory {
        private Map<String, Integer> rooms = new HashMap<>();

        public RoomInventory() {
            rooms.put("Single", 4);
            rooms.put("Double", 3);
            rooms.put("Suite", 2);
        }

        public synchronized String allocate(String roomType) {
            if (rooms.containsKey(roomType) && rooms.get(roomType) > 0) {
                int count = rooms.get(roomType);
                String roomId = roomType + "-" + count;
                rooms.put(roomType, count - 1);
                return roomId;
            }
            return null;
        }

        public void printInventory() {
            System.out.println();
            System.out.println("Remaining Inventory:");
            for (String key : rooms.keySet()) {
                System.out.println(key + ": " + rooms.get(key));
            }
        }
    }

    static class RoomAllocationService {
        public void allocateRoom(Reservation r, RoomInventory inventory) {
            String roomId = inventory.allocate(r.roomType);
            if (roomId != null) {
                System.out.println("Booking confirmed for Guest: " + r.guestName + ", Room ID: " + roomId);
            } else {
                System.out.println("No rooms available for " + r.roomType);
            }
        }
    }

    static class ConcurrentBookingProcessor implements Runnable {

        private BookingRequestQueue bookingQueue;
        private RoomInventory inventory;
        private RoomAllocationService allocationService;

        public ConcurrentBookingProcessor(BookingRequestQueue bookingQueue, RoomInventory inventory, RoomAllocationService allocationService) {
            this.bookingQueue = bookingQueue;
            this.inventory = inventory;
            this.allocationService = allocationService;
        }

        public void run() {
            while (true) {
                Reservation r;
                synchronized (bookingQueue) {
                    r = bookingQueue.getRequest();
                }
                if (r == null) break;

                synchronized (inventory) {
                    allocationService.allocateRoom(r, inventory);
                }
            }
        }
    }

    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Double"));
        bookingQueue.addRequest(new Reservation("Kural", "Suite"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));

        Thread t1 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));

        System.out.println("Concurrent Booking Simulation");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        inventory.printInventory();
    }
}
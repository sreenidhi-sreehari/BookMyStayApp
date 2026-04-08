import java.util.*;
import java.io.*;

public class BookMyStayApp {

    static class RoomInventory {
        private Map<String, Integer> rooms;

        public RoomInventory() {
            rooms = new HashMap<>();
            rooms.put("Single", 5);
            rooms.put("Double", 3);
            rooms.put("Suite", 2);
        }

        public Map<String, Integer> getRooms() {
            return rooms;
        }

        public void setRoom(String type, int count) {
            rooms.put(type, count);
        }

        public void display() {
            System.out.println("Current Inventory:");
            for (String key : rooms.keySet()) {
                System.out.println(key + ": " + rooms.get(key));
            }
        }
    }

    static class FilePersistenceService {

        public void saveInventory(RoomInventory inventory, String filePath) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                for (Map.Entry<String, Integer> entry : inventory.getRooms().entrySet()) {
                    writer.write(entry.getKey() + "=" + entry.getValue());
                    writer.newLine();
                }
                writer.close();
                System.out.println("Inventory saved successfully.");
            } catch (IOException e) {
                System.out.println("Error saving inventory.");
            }
        }

        public void loadInventory(RoomInventory inventory, String filePath) {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("No valid inventory data found. Starting fresh.");
                return;
            }

            try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        inventory.setRoom(parts[0], Integer.parseInt(parts[1]));
                    }
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("Error loading inventory.");
            }
        }
    }

    public static void main(String[] args) {

        String filePath = "inventory.txt";

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService service = new FilePersistenceService();

        System.out.println("System Recovery");

        service.loadInventory(inventory, filePath);

        System.out.println();
        inventory.display();

        service.saveInventory(inventory, filePath);
    }
}
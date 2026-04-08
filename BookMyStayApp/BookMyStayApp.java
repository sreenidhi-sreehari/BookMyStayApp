import java.util.*;

public class BookMyStayApp {

    static class Service {
        private String serviceName;
        private double cost;

        public Service(String serviceName, double cost) {
            this.serviceName = serviceName;
            this.cost = cost;
        }

        public String getServiceName() {
            return serviceName;
        }

        public double getCost() {
            return cost;
        }
    }

    static class AddOnServiceManager {
        private Map<String, List<Service>> servicesByReservation;

        public AddOnServiceManager() {
            servicesByReservation = new HashMap<>();
        }

        public void addService(String reservationId, Service service) {
            if (!servicesByReservation.containsKey(reservationId)) {
                servicesByReservation.put(reservationId, new ArrayList<>());
            }
            servicesByReservation.get(reservationId).add(service);
        }

        public double calculateTotalServiceCost(String reservationId) {
            double total = 0;
            if (servicesByReservation.containsKey(reservationId)) {
                for (Service s : servicesByReservation.get(reservationId)) {
                    total += s.getCost();
                }
            }
            return total;
        }
    }

    public static void main(String[] args) {
        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "Single-1";

        Service s1 = new Service("Breakfast", 500);
        Service s2 = new Service("Spa", 700);
        Service s3 = new Service("Airport Pickup", 300);

        manager.addService(reservationId, s1);
        manager.addService(reservationId, s2);
        manager.addService(reservationId, s3);

        double total = manager.calculateTotalServiceCost(reservationId);

        System.out.println("Add-on Service Selection");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + total);
    }
}
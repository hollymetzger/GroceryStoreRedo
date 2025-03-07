import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Customer {
    private double arrivalTime;
    private double serviceTime;
    private double departureTime;

    public Customer(double arrivalTime, double serviceTime) {
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.departureTime = arrivalTime + serviceTime;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public double getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(double departureTime) {
        this.departureTime = departureTime;
    }


        public static void doUnitTests() {
            System.out.println("Running Customer tests");

            // Create a log file for test results
            try {
                File file = new File("Customer Test Results.csv");
                FileWriter writer = new FileWriter(file);

                // Test 1: Customer creation and get methods
                Customer customer = new Customer(1.0, 2.0);
                writer.write("Test 1: Customer creation and getArrivalTime, " + (customer.getArrivalTime() == 1.0) + "\n");
                writer.write("Test 1: Customer creation and getServiceTime, " + (customer.getServiceTime() == 2.0) + "\n");
                writer.write("Test 1: Customer creation and getDepartureTime, " + (customer.getDepartureTime() == 3.0) + "\n");

                // Test 2: setDepartureTime
                customer.setDepartureTime(4.0);
                writer.write("Test 2: setDepartureTime, " + (customer.getDepartureTime() == 4.0) + "\n");

                writer.close();
                System.out.println("File created at " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();

        }
    }

}

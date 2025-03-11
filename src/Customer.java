import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Customer extends Job {

    // Private variables
    private int numberOfProducts;
    private double aisleTime;

    // Constructors
    // Without service time
    public Customer(NormalDistribution numberOfItemsRNG, double aisleMultiplier, double serviceMultiplier, double entryTime) {
        super(entryTime);
        numberOfProducts = Math.max(1, (int) numberOfItemsRNG.sample());
        // the time a customer spends shopping and in service are set based on number of items
        aisleTime = numberOfProducts * aisleMultiplier;
        this.setServiceTime(numberOfProducts * serviceMultiplier);
    }

    // Accessors
    public int getNumberOfProducts() {
        return numberOfProducts;
    }
    public double getAisleTime() {
        return aisleTime;
    }
    public String toString() {
        return super.toString() +
                " Numbert of products: " + numberOfProducts +
                " Aisle time: " + aisleTime;

    }

    // Mutators
    public void setQueueEntryTime() {
        queueEntryTime = simEntryTime + serviceTime;
    }

    // Testing method
    public static void doUnitTests() {
        System.out.println("Running Customer tests");
        NormalDistribution rng = new NormalDistribution(10, 5);

        // Test 1: Customer creation and get methods
        Customer customer = new Customer(rng, 1.0,1.0,1.0);
        System.out.println("Number of products: " + customer.getNumberOfProducts());
        System.out.println("Aisle time: " + customer.getAisleTime());
    }
}
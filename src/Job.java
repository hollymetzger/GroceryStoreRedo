import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Job {

    // Private fields
    protected double serviceTime;
    protected double simEntryTime;
    protected double queueEntryTime;
    private double departureTime;
    private double waitingTime; // time spent waiting in single server queue(s), not counting time spent in infinite server queue(s)
    private double totalTime; // total time in simulation
    private String priority;

    // Constructors
    // Job's service time can be set on instantiation or at a later point in Simulation
    public Job(double simEntryTime, double serviceTime) {
        this.simEntryTime = simEntryTime;
        this.serviceTime = serviceTime;
    }
    public Job(double simEntryTime) {
        this.simEntryTime = simEntryTime;
        this.serviceTime = -1.0; // set it to something impossible so we can check/test it
    }

    // Accessors
    public double getSimEntryTime() { return simEntryTime; }
    public double getQueueEntryTime() { return queueEntryTime; }
    public double getServiceTime() {
        return serviceTime;
    }
    public double getWaitingTime() {
        return waitingTime;
    }
    public double getTotalTime() {
        return totalTime;
    }
    public double getDepartureTime() {
        return departureTime;
    }

    // Mutators
    public void setServiceTime(double time) { serviceTime = time; }
    public void setQueueEntryTime(double time) { queueEntryTime = time; }
    public void setDepartureTime(double time) { departureTime = time; }

    // Data gathering and manipulation methods
    public void recordData(double currentTime) {
        waitingTime = currentTime - queueEntryTime;
        totalTime = currentTime - simEntryTime;
    }

    // Testing methods
    public static void doUnitTests() {
        System.out.println("Running Job tests");

        // Test 1: Job creation with service time
        Job job = new Job(1.0, 3.14);
        if (!(job.getServiceTime() == 3.14)) {
            System.out.println("FAIL: service time should be 3.14 but was " + job.getServiceTime());
        }

        // Test 2: Job creation without service time
        Job j2 = new Job(1.0);
        if (j2.getServiceTime() != -1.0) {
            System.out.println("FAIL: service time should be -1.0 but was " + j2.getServiceTime());
        }
        j2.setServiceTime(3.14);
        if (j2.getServiceTime() != 3.14) {
            System.out.println("FAIL: service time should be 3.14 but was " + j2.getServiceTime());
        }

        // Test 3: Other accessors and mutators
        if (j2.getSimEntryTime() != 1.0) {
            System.out.println("FAIL: sim entry time should be 1.0 but was " + j2.getSimEntryTime());
        }
        j2.setQueueEntryTime(5.5);
        if (j2.getQueueEntryTime() != 5.5) {
            System.out.println("FAIL: queue entry time should be 5.5 but was " + j2.getQueueEntryTime());
        }
        j2.setDepartureTime(4.4);
        if (j2.getDepartureTime() != 4.4) {
            System.out.println("FAIL: departure time should be 4.4 but was " + j2.getDepartureTime());
        }
        j2.recordData(10.5);
        if (j2.getWaitingTime() != 5.0) { // Queue entry time is 5.5 so waiting time should be 5.0
            System.out.println("FAIL: waiting time should be 3.14 but was " + j2.getWaitingTime());
        }
        if (j2.getTotalTime() != 9.5) { // Sim entry time is 1.0 so total time should be 9.5
            System.out.println("FAIL: total time should be 9.5 but was " + j2.getTotalTime());
        }
    }
}

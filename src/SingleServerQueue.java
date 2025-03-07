import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class SingleServerQueue {
    private Job jobInService;
    private CustomQueue<Job> waitingJobs;
    private double nextEndServiceTime;
    private NormalDistribution serviceTimeDistribution;

    public SingleServerQueue(NormalDistribution serviceTimeDistribution) {
        this.waitingJobs = new CustomQueue<>();
        this.serviceTimeDistribution = serviceTimeDistribution;
        this.nextEndServiceTime = Double.POSITIVE_INFINITY;
    }

    public void add(Job job, double currentTime) {
        if (jobInService == null) {
            jobInService = job;
            nextEndServiceTime = currentTime + serviceTimeDistribution.sample();
        } else {
            waitingJobs.enqueue(job);
        }
    }

    public double getEndServiceTime() {
        return nextEndServiceTime;
    }

    public void complete(double currentTime) {
        if (jobInService != null) {
            jobInService = null;
            if (!waitingJobs.isEmpty()) {
                jobInService = waitingJobs.dequeue();
                nextEndServiceTime = currentTime + serviceTimeDistribution.sample();
            } else {
                nextEndServiceTime = Double.POSITIVE_INFINITY;
            }
        }
    }

    public static void doUnitTests() {
        System.out.println("Running Single Server Queue tests");
        NormalDistribution mockDistribution = new NormalDistribution(0.0,1.0); // Use a simple distribution for testing
        SingleServerQueue queue = new SingleServerQueue(mockDistribution);

        // Create a log file for test results
        try {
            File file = new File("Single Server Queue Test Results.csv");
            FileWriter writer = new FileWriter(file);

            // Test 1: Initial state
            writer.write("Test 1: Initial state, " + (queue.getEndServiceTime() == Double.POSITIVE_INFINITY) + "\n");

            // Test 2: Add job when queue is empty
            Job job1 = new Job(mockDistribution.sample());
            queue.add(job1, 0.0);
            writer.write("Test 2: Add job when queue is empty, " + (queue.getEndServiceTime() == 1.0) + "\n");

            // Test 3: Add job when queue has a job in service
            Job job2 = new Job(mockDistribution.sample());
            queue.add(job2, 0.5);
            writer.write("Test 3: Add job when queue has a job in service, " + (queue.getEndServiceTime() == 1.0) + "\n");

            // Test 4: Complete job in service when waiting queue is empty
            queue.complete(1.0);
            writer.write("Test 4: Complete job in service when waiting queue is empty, " + (queue.getEndServiceTime() == Double.POSITIVE_INFINITY) + "\n");

            // Test 5: Complete job in service when waiting queue has jobs
            queue.add(job1, 0.0);
            queue.add(job2, 0.5);
            queue.complete(1.0);
            writer.write("Test 5: Complete job in service when waiting queue has jobs, " + (queue.getEndServiceTime() != Double.POSITIVE_INFINITY) + "\n");

            // Test 6: Complete all jobs
            queue.complete(2.0);
            writer.write("Test 6: Complete all jobs, " + (queue.getEndServiceTime() == Double.POSITIVE_INFINITY) + "\n");

            writer.close();
            System.out.println("File created at " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

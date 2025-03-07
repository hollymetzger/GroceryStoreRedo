import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Running unit tests");
        
        // Run unit tests for all classes
        runAllUnitTests();

        // Parameters for simulation
        double simulationTime = 3600; // 1-hour simulation
        double lambdaArrival = 1; // Mean rate of arrival (lambda)
        double meanService = 0.5; // Mean service time
        double stdService = 0.2; // Standard deviation of service time

        // Scenario 1: One Checkout with Two People
        System.out.println("Simulating One Checkout with Two People:");
        ExponentialDistribution arrivalDistribution1 = new ExponentialDistribution(lambdaArrival);
        NormalDistribution serviceDistribution1 = new NormalDistribution(meanService, stdService);
        ArrivalProcess arrivalProcess1 = new ArrivalProcess(arrivalDistribution1);
        CustomQueue<SingleServerQueue> queue1 = new CustomQueue<>();
        queue1.enqueue(new SingleServerQueue(serviceDistribution1));
        queue1.enqueue(new SingleServerQueue(serviceDistribution1));
        Simulation simulation1 = new Simulation(arrivalProcess1, queue1);
        simulation1.run(simulationTime);
        System.out.println("Simulation completed for one checkout with two people.");

        // Scenario 2: Multiple Checkouts with One Person Each
        System.out.println("Simulating Multiple Checkouts with One Person Each:");
        ExponentialDistribution arrivalDistribution2 = new ExponentialDistribution(lambdaArrival);
        NormalDistribution serviceDistribution2 = new NormalDistribution(meanService, stdService);
        ArrivalProcess arrivalProcess2 = new ArrivalProcess(arrivalDistribution2);
        CustomQueue<SingleServerQueue> queue2 = new CustomQueue<>();
        queue2.enqueue(new SingleServerQueue(serviceDistribution2));
        queue2.enqueue(new SingleServerQueue(serviceDistribution2));
        Simulation simulation2 = new Simulation(arrivalProcess2, queue2);
        simulation2.run(simulationTime);
        System.out.println("Simulation completed for multiple checkouts with one person each.");
    }

    // Function to run all unit tests
    public static void runAllUnitTests() {
        Node.doUnitTests();
        CustomQueue.doUnitTests();
        // Customer.doUnitTests();
        Job.doUnitTests();
        ExponentialDistribution.doUnitTests();
        NormalDistribution.doUnitTests();
        ArrivalProcess.doUnitTests();
        SingleServerQueue.doUnitTests();
        Simulation.doUnitTests();
    }
}

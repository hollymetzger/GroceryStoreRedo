import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Simulation {
    private double currentTime;
    private double nextArrivalTime;
    private double nextCompletionTime;
    private CustomQueue<SingleServerQueue> queues;
    private ArrivalProcess arrivalGenerator;
    private CustomQueue<Job> completedJobs;
    public int jobsCompleted;

    public Simulation(ArrivalProcess arrivalGenerator, CustomQueue<SingleServerQueue> queues) {
        this.currentTime = 0.0;
        this.queues = queues;
        this.arrivalGenerator = arrivalGenerator;
        this.completedJobs = new CustomQueue<Job>();
    }

    public void run(double simulationTime) {
        while (currentTime < simulationTime) {
            doLoop();
        }
    }

    private void doLoop() {
        // Determine the next event (arrival or service completion)
        double nextArrivalTime = arrivalGenerator.getNextArrivalTime();
        double nextServiceCompletionTime = Double.POSITIVE_INFINITY;
        SingleServerQueue nextQueueToComplete = null;

        Node<SingleServerQueue> current = queues.getHead();
        while (current != null) {
            SingleServerQueue queue = current.getData();
            double endServiceTime = queue.getNextEndServiceTime();
            if (endServiceTime < nextServiceCompletionTime) {
                nextServiceCompletionTime = endServiceTime;
                nextQueueToComplete = queue;
            }
            current = current.getNext();
        }

        // Process the next event
        if (nextArrivalTime < nextServiceCompletionTime) {
            // Process arrival
            currentTime = nextArrivalTime;
            Job newJob = arrivalGenerator.nextJob();
            newJob.setEntryTime(currentTime);
            placeJob(newJob);
        } else {
            // Process service completion
            currentTime = nextServiceCompletionTime;
            completedJobs.enqueue(nextQueueToComplete.complete(currentTime));
            jobsCompleted++;
        }
    }

    private void placeJob(Job job) {
        // Add to the first queue for simplicity, option for greater functionality later
        queues.getHead().getData().add(job, currentTime);
    }


    public static void testJobs() {
        // create objects
        ExponentialDistribution ed = new ExponentialDistribution(1.0);
        ArrivalProcess arrivalGenerator = new ArrivalProcess(ed);
        CustomQueue<SingleServerQueue> queues = new CustomQueue<>();
        queues.enqueue(new SingleServerQueue(new NormalDistribution(0.0,1.0)));
        Simulation simulation = new Simulation(arrivalGenerator, queues);

        simulation.run(100);
        System.out.println("Jobs completed: " + simulation.jobsCompleted);

        Node current = simulation.completedJobs.getHead();
        while (current != null) {
            //System.out.println(current.getData().getWaitingTime()); // grrrr idk how to fix this
        }


    }



    public static void doUnitTests() {
            System.out.println("Running Simulation tests");

            // Mock objects for testing
    ExponentialDistribution expdistribution = new ExponentialDistribution(1.0);
            ArrivalProcess mockArrivalGenerator = new ArrivalProcess(expdistribution) {
                private double nextArrivalTime = 1.0;

                @Override
                public double getNextArrivalTime() {
                    return nextArrivalTime;
                }

                @Override
                public Job nextJob() {
                    nextArrivalTime += 1.0; // Increment for simplicity
                    return new Job(1);
                }
            };

            SingleServerQueue queue1 = new SingleServerQueue(new NormalDistribution(0.0,1.0));
            SingleServerQueue queue2 = new SingleServerQueue(new NormalDistribution(0.0,1.0));
            CustomQueue<SingleServerQueue> queues = new CustomQueue<>();
            queues.enqueue(queue1);
            queues.enqueue(queue2);

            Simulation simulation = new Simulation(mockArrivalGenerator, queues);

            // Create a log file for test results
            try {
                File file = new File("Simulation Test Results.csv");
                FileWriter writer = new FileWriter(file);

                // Test 1: Initial state
                writer.write("Test 1: Initial state, " + (simulation.currentTime == 0.0) + "\n");

                // Test 2: Run simulation for a short period
                simulation.run(3.0); // Run for 3 time units
                writer.write("Test 2: Run simulation for 3.0 time units, " + (simulation.currentTime >= 3.0) + "\n");

                // Test 3: Verify job completion and queue state
                writer.write("Test 3: Verify job completion and queue state, " + (queue1.getNextEndServiceTime() != Double.POSITIVE_INFINITY || queue2.getNextEndServiceTime() != Double.POSITIVE_INFINITY) + "\n");

                writer.close();
                System.out.println("File created at " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//Attributes:
//currentTime: Tracks the current time of the simulation.
//queues: A custom queue of SingleServerQueue objects. Each SingleServerQueue represents a queue in the system.
//arrivalGenerator: Generates arrival events, which create new jobs that need to be processed.
//completedJobs: A custom queue that holds jobs that have been completed.
//Constructor: The constructor initializes the simulation with an ArrivalProcess object and a custom queue of SingleServerQueue objects. It also sets the currentTime to 0.0 and initializes completedJobs.
//Run Method: The run method drives the simulation. It continues running until the simulation time exceeds a specified limit (simulationTime). It keeps calling doLoop to process events.
//doLoop Method: This is where the magic happens. It determines the next event to process:
//Next Arrival Time: It gets the next arrival time from the arrivalGenerator.
//Next Service Completion Time: It iterates through all the queues to find the earliest service completion time and the corresponding queue.
//Process Events: Based on whether the next event is an arrival or a service completion:

//Arrival:
//The simulation time is updated to the next arrival time.
//A new job is generated using arrivalGenerator.
//The job is added to the first queue (for simplicity).

//Service Completion:
//The simulation time is updated to the next service completion time.
//The next queue to complete the service processes the job.
//This loop continues until the simulation time reaches the specified limit.

import java.util.ArrayList;


public class Main {

	// n philosophers dining
	public static int numDiningPhilosophers = 10;
	// run time in seconds
	public static int runTime = 10;
	public static final int SECOND_CONVERSION = 1000;
	// log data every n seconds
	public static int loggingInterval = 2;
	
	public static ArrayList<Chopstick> sticks = new ArrayList<Chopstick>();
    public static ArrayList<Philosopher> philosophers = new ArrayList<Philosopher>();
    
    public static void main(String[] args) {
    
    	// always equal number of philosophers and chopsticks
    	// loop through and add them to their lists
    	for(int i = 0; i < numDiningPhilosophers; i++) {
    		// chopsticks instantiated first 
    		// philosophers need to know which chopsticks belong to them
    		sticks.add(new Chopstick());
    	}
    	
    	// table is set, now philosophers can sit
    	for(int i = 0; i < numDiningPhilosophers; i++) {
    		philosophers.add(new Philosopher());	
    	}
    	// philosophers can start dining
    	for(Philosopher phil : philosophers) {
			new Thread(phil).start();
		}
    	
    	long stop = System.currentTimeMillis() + (runTime * SECOND_CONVERSION);
    	
    	while(System.currentTimeMillis() < stop) {
    		// program is running
    		// log some data here every interval
    		for(Philosopher phil : philosophers) {
    			System.out.println(phil);
    		}
    		try {
    			Thread.sleep(loggingInterval * SECOND_CONVERSION);
    		} catch(InterruptedException e) {
    			System.out.println("Can't sleep, something went wrong!");
    		}
    	}
    	int mealsConsumed = 0;
    	for(Philosopher phil : philosophers) {
			phil.done = true;
			mealsConsumed += phil.getHowFull();
			System.out.println("Philosopher #" + phil.getID() + " ate " + phil.getHowFull() + " times.");
		}
    	System.out.println(mealsConsumed + " meals were eaten (" + mealsConsumed / runTime + " per second)");
    	
    	
    }
}

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class Chopstick {
	
	// stick ID
	@SuppressWarnings("unused")
	private int ID;
	// how many sticks we have
	public static int numChopsticks = 0;
	
	/*
	 * set as atomic for thread safety
	 */
	
	// is the stick free?
	public AtomicBoolean isFree = new AtomicBoolean(true);
	// who is holding me?
	// holdID is the Philosopher's ID
	// A value of -1 indicates the chopstick is on the table
	public AtomicInteger holdID = new AtomicInteger(-1);
	
	public Chopstick() {
		ID = numChopsticks;
		numChopsticks++;
	}
	
	public String toString() {
		return Integer.toString(holdID.get());
	}
	
	
}

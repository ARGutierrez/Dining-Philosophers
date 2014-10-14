

public class Philosopher implements Runnable {

	// philosopher ID
	private int ID;
	// time philosopher spends eating (in ms)
	private int eatTime = 1;
	// time philosopher spends thinking (in ms)
	private int thinkTime = 1;
	// what the philosopher is doing
	private PhilosopherState state = PhilosopherState.Hungry;
	// how many philosophers
	public static int numPhilosophers = 0;
	// done for the day
	public boolean done = false;
	
	// chopsticks
	private Chopstick left;
	private Chopstick right;
	
	// how many times the philosopher ate
	private int howFull = 0;
	
	public Philosopher() {
		ID = numPhilosophers;
		numPhilosophers++;
		
		// left stick is ID
		left = Main.sticks.get(ID);
		// right stick needs to be the first stick if you're the last philosopher
		if(ID == Main.numDiningPhilosophers - 1) {
			right = Main.sticks.get(0);
		} else {
			right = Main.sticks.get(ID + 1);
		}
	}
	
	private boolean checkAndPickUp(Chopstick left, Chopstick right) {
		
		while(true) {
			// if the chopstick is free, claim it
			// Trying to claim one stick at a time led to unnecessary busy-waiting
			if(left.isFree.get() && right.isFree.get()) {
				left.isFree.set(false);
				right.isFree.set(false);
				left.holdID.set(ID);
				right.holdID.set(ID);
				return true;
			} else {
				return false;
			}
		}
	}
	
	@Override
	public void run() {
		
		while(!done) {
			// Gets hungry after thinking
			if(state == PhilosopherState.Thinking) {
				state = PhilosopherState.Hungry;
			// Hungry, try to take chopsticks
			} else if(state == PhilosopherState.Hungry){
				// if you can claim both sticks, eat!
				if(checkAndPickUp(left, right)) {
					// eating
					state = PhilosopherState.Eating;
					KillTime(eatTime);
					howFull++;
					// put sticks down
					left.isFree.set(true);
					left.holdID.set(-1);
					right.isFree.set(true);
					right.holdID.set(-1);
					// back to philosophizing for a while
					state = PhilosopherState.Thinking;
					KillTime(thinkTime);
				} else {
					// If we got one stick, be courteous and put it down
					
					// was causing odd behavior, 
					// checked for both sticks 
					// in one function call instead
					// Led to extra busy-wait time
					
					/*
					if(left.holdID.get() == ID) {
						left.isFree.set(true);
						left.holdID.set(-1);
					}
					if(right.holdID.get() == ID) {
						right.isFree.set(true);
						right.holdID.set(-1);
					}
					*/
					
					// cant eat, try again soon
					KillTime(thinkTime);
				}
			}
		}
	}
	
	private void KillTime(int waitTime) {
		
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			System.out.println("Can't sleep, something went wrong!");
		}
	}
	
	public int getID() {
		return ID;
	}
	
	public int getHowFull() {
		return howFull;
	}
	
	public String toString() {
		return "I am Philosopher #" + ID + " and I am " + state + "\n" 
				+ "Philosopher #" + left + " has the left stick" + "\n"
				+ "Philosopher #" + right + " has the right stick";
	}



	
	
	
}

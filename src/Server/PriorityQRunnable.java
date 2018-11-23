package Server;


public abstract class PriorityQRunnable implements Comparable<PriorityQRunnable>, Runnable {
	int priority;
	
	@Override
	public int compareTo(PriorityQRunnable o) {
		return this.priority - o.priority;
	}

	public PriorityQRunnable(int priority){
		this.priority = priority;
	}

}
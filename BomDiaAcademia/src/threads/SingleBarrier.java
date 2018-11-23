package threads;

/**
 * The Class SingleBarrier.
 * @author Alexandre Mendes
 * @version 1.0
 */
public class SingleBarrier { //
	
	/** The current posters. */
	int currentPosters = 0;
	
	/** The total posters. */
	int totalPosters = 0;
	
	/** The passed waiters. */
	int passedWaiters = 0;
	
	/** The total waiters. */
	int totalWaiters = 1;
	
	/**
	 * Instantiates a new single barrier.
	 *
	 * @param i the i
	 */
	public SingleBarrier(int i) {
		totalPosters = i;
	}
	
	/**
	 * Instantiates a new single barrier.
	 *
	 * @param i the i
	 * @param j the j
	 */
	public SingleBarrier(int i, int j) {
		totalPosters = i;
		totalWaiters = j;
	}
	
	/**
	 * Instantiates a new single barrier.
	 */
	public SingleBarrier() {
	}
	
	/**
	 * Inits the.
	 *
	 * @param i the i
	 */
	public synchronized void init(int i) {
		totalPosters = i;
		currentPosters = 0;
	}
	
	public int getCurrentPosters() {
		return currentPosters;
	}
	
	public int getTotalPosters() {
		return totalPosters;
	}
	
	public int getPassedWaiters() {
		return passedWaiters;
	}
	
	public int getTotalWaiters() {
		return totalWaiters;
	}
	
	/**
	 * Barrier set.
	 *
	 * @param i the i
	 */
	public synchronized void barrierSet(int i) {
		totalPosters = i;
		currentPosters = 0;
	}
	
	/**
	 * Barrier wait.
	 */
	public synchronized void barrierWait() {
		boolean interrupted = false;
		while (currentPosters != totalPosters) {
			try {
				wait();
			} catch (InterruptedException ie) {
				interrupted = true;
			}
		}
		passedWaiters++;
		if (passedWaiters == totalWaiters) {
			currentPosters = 0;
			passedWaiters = 0;
			notifyAll();
		}
		if (interrupted) {
			Thread.currentThread().interrupt();
		}
	}
	
	/**
	 * Barrier post.
	 */
	public synchronized void barrierPost() {
		boolean interrupted = false;
		// In case a poster thread beats barrierWait,
		// keep count of posters.
		while (currentPosters == totalPosters) {
			try {
				wait();
			} catch (InterruptedException ie) {
				interrupted = true;
			}
		}
		currentPosters++;
		if (currentPosters == totalPosters)
			notifyAll();
		if (interrupted) {
			Thread.currentThread().interrupt();
		}
	}
}

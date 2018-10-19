package threads;

public class SingleBarrier {
	
	int currentPosters = 0;
	int totalPosters = 0;
	
	int passedWaiters = 0;
	int totalWaiters = 1;
	
	public SingleBarrier(int i) {
		totalPosters = i;
	}
	
	public SingleBarrier(int i, int j) {
		totalPosters = i;
		totalWaiters = j;
	}
	
	public SingleBarrier() {
	}
	
	public synchronized void init(int i) {
		totalPosters = i;
		currentPosters = 0;
	}
	
	public synchronized void barrierSet(int i) {
		totalPosters = i;
		currentPosters = 0;
	}
	
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

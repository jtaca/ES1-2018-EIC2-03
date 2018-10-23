package threads;

import tasks.Task;

/**
 * The Class Worker.
 */
public class Worker extends Thread {
	
	/** The stop. */
	private boolean stop;
	
	/**
	 * Instantiates a new worker.
	 */
	public Worker() {
		stop = false;
	}
	
	/**
	 * Stop working.
	 */
	public void stopWorking() {
		stop = true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() { // Just grabs a task and run it. Will wait if there is no tasks in the blockingQueue to grab.
		Task task;
		while(!stop) {
			try {
				task = TaskTable.getInstance().getTask();
				task.run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

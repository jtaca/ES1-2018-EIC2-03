package BDA.threads;

import BDA.tasks.Task;

/**
 * The Class Worker.
 * @author Alexandre Mendes
 * @version 1.0
 */
public class Worker extends Thread { //
	
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
				if(task != null) {
					task.run();
				}
			} catch (InterruptedException e) {
				stop = true;
			}
		}
	}
}

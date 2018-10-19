package threads;

import tasks.Task;

public class Worker extends Thread {
	
	private boolean stop;
	
	public Worker() {
		stop = false;
	}
	
	public void stopWorking() {
		stop = true;
	}
	
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

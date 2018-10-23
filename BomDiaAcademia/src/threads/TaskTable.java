package threads;

import java.util.ArrayList;
import java.util.List;

import tasks.Task;

/**
 * The Class TaskTable.
 */
public class TaskTable {
	
	/** The tasks. */
	private List<Task> tasks = new ArrayList<Task>(); // Blocking Queue
	
	/** The Constant INSTANCE. */
	private static final TaskTable INSTANCE = new TaskTable();
	
	/**
	 * Instantiates a new task table.
	 */
	private TaskTable() {
	}
	
	/**
	 * Gets the single instance of TaskTable.
	 *
	 * @return single instance of TaskTable
	 */
	public static TaskTable getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Put task.
	 *
	 * @param task the task
	 */
	public synchronized void putTask(Task task) {
		tasks.add(task);
		notify(); // Tinha notifyAll();
	}
	
	/**
	 * Gets the task.
	 *
	 * @return the task
	 * @throws InterruptedException the interrupted exception
	 */
	public synchronized Task getTask() throws InterruptedException {
		while(tasks.size() == 0) {
			wait();
		}
		Task task = tasks.remove(0);
		return task;
	}

}

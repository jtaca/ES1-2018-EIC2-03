package threads;

import java.util.ArrayList;
import java.util.List;

import tasks.Task;

public class TaskTable {
	
	private List<Task> tasks = new ArrayList<Task>(); // Blocking Queue
	
	private static final TaskTable INSTANCE = new TaskTable();
	
	private TaskTable() {
	}
	
	public static TaskTable getInstance() {
		return INSTANCE;
	}
	
	public synchronized void putTask(Task task) {
		tasks.add(task);
		notify(); // Tinha notifyAll();
	}
	
	public synchronized Task getTask() throws InterruptedException {
		while(tasks.size() == 0) {
			wait();
		}
		Task task = tasks.remove(0);
		return task;
	}

}

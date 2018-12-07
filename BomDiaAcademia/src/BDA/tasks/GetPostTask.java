package BDA.tasks;

import java.util.List;

import BDA.gui.MainController;
import BDA.threads.InformationEntryGatherer;
import BDA.threads.TaskTable;

/**
 * The Class GetPostTask.
 * 
 * @author Alexandre Mendes
 * @version 1.0
 */
public class GetPostTask implements Task { //

	/** The tasks. */
	private List<ServiceReadTask> tasks;

	/** The information entry gatherer. */
	private InformationEntryGatherer information_entry_gatherer;

	/**
	 * Instantiates a new gets the post task.
	 *
	 * @param tasks the tasks
	 */
	public GetPostTask(List<ServiceReadTask> tasks) {
		this.tasks = tasks;
		information_entry_gatherer = new InformationEntryGatherer(tasks.size()); // tem cenas mal...
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		for (ServiceReadTask t : tasks) {
			t.setBarrier(information_entry_gatherer);
			TaskTable.getInstance().putTask(t);
		}
		information_entry_gatherer.waitForEveryOcurrence();
		MainController.getInstance().loadPosts(information_entry_gatherer.getList(), true);
//		Main.auxFunctionToPrintEmails(information_entry_gatherer.getList());
	}

}

package tasks;

import java.util.List;

import threads.InformationEntryGatherer;
import threads.TaskTable;

public class GetPostTask implements Task {
	
	private List<ServiceReadTask> tasks;
	private InformationEntryGatherer information_entry_gatherer;
	
	public GetPostTask(List<ServiceReadTask> tasks) {
		this.tasks = tasks;
		information_entry_gatherer = new InformationEntryGatherer(tasks.size());
	}
	
	@Override
	public void run() {
		for(ServiceReadTask t : tasks) {
			TaskTable.getInstance().putTask(t);
		}
		information_entry_gatherer.waitForEveryOcurrence();
	}
	

}

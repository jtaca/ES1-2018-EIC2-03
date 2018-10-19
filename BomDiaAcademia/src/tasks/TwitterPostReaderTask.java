package tasks;

import java.util.List;

import entry_objects.InformationEntry;
import threads.InformationEntryGatherer;

public class TwitterPostReaderTask implements ServiceReadTask {
	
	/*
	private InformationEntryGatherer barrier;
	private TwitterConnection twitterConnection;
	
	public TwitterPostReaderTask(InformationEntryGatherer barrier, TwitterConnection twitterConnection) {
		this.twitterConnection = twitterConnection;
		this.barrier = barrier;
	}*/
	
	@Override
	public void run() {
		List<InformationEntry> information_entry_list; // Where the results should go to.
		//information_entry_list = twitterConnection.readPosts();
		// Place the result on a simple barrier in order for the UI to load all the news at once, after organizing them by date.
		//barrier.addResult(information_entry_list);
	}
	
	

}

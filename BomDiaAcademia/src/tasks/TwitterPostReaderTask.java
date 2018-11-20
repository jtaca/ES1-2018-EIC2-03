package tasks;

import java.util.List;

import entry_objects.InformationEntry;
import threads.InformationEntryGatherer;
import twitter.TwitterFunctions;

/**
 * The Class TwitterPostReaderTask.
 * @author Alexandre Mendes
 * @version 1.0
 */
public class TwitterPostReaderTask implements ServiceReadTask { //
	
	
	private InformationEntryGatherer barrier;
	private TwitterFunctions twitterConnection;
	
	public TwitterPostReaderTask(InformationEntryGatherer barrier, TwitterFunctions twitterConnection) {
		this.twitterConnection = twitterConnection;
		this.barrier = barrier;
	}
	
	@Override
	public void setBarrier(InformationEntryGatherer barrier) {
		this.barrier = barrier;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		List<InformationEntry> information_entry_list; // Where the results should go to.
		//information_entry_list = twitterConnection.readPosts();
		// Place the result on a simple barrier in order for the UI to load all the news at once, after organizing them by date.
		//barrier.addResult(information_entry_list);
	}
	
	

}

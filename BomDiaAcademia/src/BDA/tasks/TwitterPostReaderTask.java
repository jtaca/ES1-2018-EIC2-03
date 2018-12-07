package BDA.tasks;

import java.util.List;

import BDA.entry_objects.InformationEntry;
import BDA.threads.InformationEntryGatherer;
import BDA.twitter.TwitterConnection;

/**
 * The Class TwitterPostReaderTask.
 * @author Alexandre Mendes
 * @version 1.0
 */
public class TwitterPostReaderTask implements ServiceReadTask { //
	
	
	private InformationEntryGatherer barrier;
	
	/*
	private TwitterFunctions twitterConnection;
	
	public TwitterPostReaderTask(TwitterFunctions twitterConnection) {
		this.twitterConnection = twitterConnection;
	}
	*/
	
	public TwitterPostReaderTask() {
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
//		information_entry_list = twitterConnection.getTweetsFiltered();
		information_entry_list = TwitterConnection.getInstance().getTweetsFiltered();
		// Place the result on a simple barrier in order for the UI to load all the news at once, after organizing them by date.
		barrier.addResult(information_entry_list);
	}
	
	

}

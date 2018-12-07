package BDA.tasks;

import java.util.List;

import BDA.entry_objects.InformationEntry;
import BDA.facebook.FacebookConnection;
import BDA.threads.InformationEntryGatherer;

/**
 * The Class FacebookPostReaderTask.
 * @author Alexandre Mendes
 * @version 1.0
 */
public class FacebookPostReaderTask implements ServiceReadTask { //
	
	
	/** The barrier. */
 private InformationEntryGatherer barrier;
//	private FacebookConnection facebookConnection;
//	
//	public FacebookPostReaderTask(InformationEntryGatherer barrier, FacebookConnection facebookConnection) {
//		this.facebookConnection = facebookConnection;
//		this.barrier = barrier;
//	}
/* (non-Javadoc)
 * @see BDA.tasks.ServiceReadTask#setBarrier(BDA.threads.InformationEntryGatherer)
 */
//	
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
		information_entry_list = FacebookConnection.requestFacebook("me/feed");
		// Place the result on a simple barrier in order for the UI to load all the news at once, after organizing them by date.
		barrier.addResult(information_entry_list);
	}
	
	
}

package BDA.tasks;

import java.util.List;

import BDA.email.EmailConnection;
import BDA.entry_objects.InformationEntry;
import BDA.threads.InformationEntryGatherer;

/**
 * The Class EmailReaderTask.
 * @author Alexandre Mendes
 * @version 1.0
 */
public class EmailReaderTask implements ServiceReadTask { //
	
	/** The barrier. */
	private InformationEntryGatherer barrier;
	
	/** The email connection. */
	private EmailConnection emailConnection;
	
	/**
	 * Instantiates a new email reader task.
	 *
	 * @param emailConnection the email connection
	 */
	public EmailReaderTask(EmailConnection emailConnection) {
		this.emailConnection = emailConnection;
	}
	
	/* (non-Javadoc)
	 * @see BDA.tasks.ServiceReadTask#setBarrier(BDA.threads.InformationEntryGatherer)
	 */
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
		information_entry_list = emailConnection.receiveMail(); // require to recieve a list of emails
		// Place the result on a simple barrier in order for the UI to load all the news at once, after organizing them by date.
		if(barrier != null) {
			barrier.addResult(information_entry_list);
		}
	}
	
}

package tasks;

import java.util.List;

import email.EmailConnection;
import entry_objects.InformationEntry;
import threads.InformationEntryGatherer;

public class EmailReaderTask implements ServiceReadTask {
	
	private InformationEntryGatherer barrier;
	private EmailConnection emailConnection;
	
	public EmailReaderTask(InformationEntryGatherer barrier, EmailConnection emailConnection) {
		this.barrier = barrier;
		this.emailConnection = emailConnection;
	}

	@Override
	public void run() {
		List<InformationEntry> information_entry_list; // Where the results should go to.
		information_entry_list = emailConnection.receiveMail(); // require to recieve a list of emails
		// Place the result on a simple barrier in order for the UI to load all the news at once, after organizing them by date.
		barrier.addResult(information_entry_list);
	}
	
}

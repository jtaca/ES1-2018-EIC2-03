package BDA.tasks;

import BDA.email.EmailConnection;
import BDA.threads.TaskTable;

public class CreateEmailWriterTask implements Task { //
	
	private EmailWriterTask task;

	public CreateEmailWriterTask(EmailConnection emailConnection, String sendEmailTo, String subject, String message) {
		EmailWriterTask task = new EmailWriterTask(emailConnection, sendEmailTo, subject, message);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		TaskTable.getInstance().putTask(task);
	}
	
}

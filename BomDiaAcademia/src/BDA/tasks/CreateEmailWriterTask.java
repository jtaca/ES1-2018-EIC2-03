package BDA.tasks;

import BDA.email.EmailConnection;
import BDA.threads.TaskTable;

/**
 * The Class CreateEmailWriterTask.
 */
public class CreateEmailWriterTask implements Task { //
	
	/** The task. */
 private EmailWriterTask task;

	/**
	 * Instantiates a new creates the email writer task.
	 *
	 * @param emailConnection the email connection
	 * @param sendEmailTo the send email to
	 * @param subject the subject
	 * @param message the message
	 */
	public CreateEmailWriterTask(EmailConnection emailConnection, String sendEmailTo, String subject, String message) {
		task = new EmailWriterTask(emailConnection, sendEmailTo, subject, message);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		TaskTable.getInstance().putTask(task);
	}
	
}

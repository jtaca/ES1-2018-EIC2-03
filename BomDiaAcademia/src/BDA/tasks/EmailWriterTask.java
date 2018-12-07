package BDA.tasks;

import BDA.email.EmailConnection;

/**
 * The Class EmailReaderTask.
 * @author Alexandre Mendes
 * @version 3.0
 */
public class EmailWriterTask implements Task { //
	
	/** The email connection. */
	private EmailConnection emailConnection;
	
	/** The send email to. */
	private String sendEmailTo;
	
	/** The subject. */
	private String subject;
	
	/** The message. */
	private String message;

	
	/**
	 * Instantiates a new email writer task.
	 *
	 * @param emailConnection the email connection
	 * @param sendEmailTo the send email to
	 * @param subject the subject
	 * @param message the message
	 */
	public EmailWriterTask(EmailConnection emailConnection, String sendEmailTo, String subject, String message) {
		this.emailConnection = emailConnection;
		this.sendEmailTo = sendEmailTo;
		this.subject = subject;
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		emailConnection.sendEmail(sendEmailTo, subject, message);
	}
	
}

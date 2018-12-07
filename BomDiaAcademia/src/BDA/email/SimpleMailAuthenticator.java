package BDA.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * The Class SimpleMailAuthenticator.
 * @author Alexandre Mendes
 * @version 3.0
 */
public class SimpleMailAuthenticator extends Authenticator {
	
	/** The authentication. */
	private PasswordAuthentication authentication;
	
	/**
	 * Instantiates a new simple mail authenticator.
	 *
	 * @param username the username
	 * @param password the password
	 */
	public SimpleMailAuthenticator(String username, String password) {
		super();
		authentication = new PasswordAuthentication(username, password);
	}
	
	/* (non-Javadoc)
	 * @see javax.mail.Authenticator#getPasswordAuthentication()
	 */
	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		return authentication;
	}
	
}

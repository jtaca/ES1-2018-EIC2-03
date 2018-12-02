package email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SimpleMailAuthenticator extends Authenticator {
	
	private PasswordAuthentication authentication;
	
	public SimpleMailAuthenticator(String username, String password) {
		super();
		authentication = new PasswordAuthentication(username, password);
	}
	
	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		return authentication;
	}
	
}

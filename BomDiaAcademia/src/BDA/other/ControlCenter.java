package BDA.other;

import java.util.ArrayList;
import java.util.List;

import BDA.email.EmailConnection;
import BDA.facebook.FacebookConnection;
import BDA.files.ReadAndWriteXMLFile;
import BDA.interfaces.ServiceInstance;
import BDA.twitter.TwitterConnection;
import BDA.other.Service;

/**
 * The Class ControlCenter.
 */
public class ControlCenter {
	
	/** The email list. */
	private List<EmailConnection> emailList = null;
	
	/** The current email used. */
	private EmailConnection currentEmailUsed = null;
	
	/** The ignore email. */
	private boolean ignore_email = false;
	
	/** The twitter list. */
	private List<TwitterConnection> twitterList = null;
	
	/** The ignore twitter. */
	private boolean ignore_twitter = false;
	
	/** The facebook list. */
	private List<FacebookConnection> facebookList = null;
	
	/** The ignore facebook. */
	private boolean ignore_facebook = false;
	
	/** The Constant INSTANCE. */
	private static final ControlCenter INSTANCE = new ControlCenter();
	
	/**
	 * Instantiates a new control center.
	 */
	private ControlCenter() {
	}
	
	/**
	 * Gets the single instance of ControlCenter.
	 *
	 * @return single instance of ControlCenter
	 */
	public static ControlCenter getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Gets the email list.
	 *
	 * @return the email list
	 */
	public List<EmailConnection> getEmailList() {
		return emailList;
	}
	
	/**
	 * Gets the current email used.
	 *
	 * @return the current email used
	 */
	public EmailConnection getCurrentEmailUsed() {
		return currentEmailUsed;
	}
	
	
	/**
	 * Gets the twitter list.
	 *
	 * @return the twitter list
	 */
	public List<TwitterConnection> getTwitterList() {
		return twitterList;
	}
	
	/**
	 * Gets the facebook list.
	 *
	 * @return the facebook list
	 */
	public List<FacebookConnection> getFacebookList() {
		return facebookList;
	}
	
	/**
	 * Sets the email list.
	 *
	 * @param emailList the new email list
	 */
	public synchronized void setEmailList(List<EmailConnection> emailList) {
		this.emailList = emailList;
	}
	
	/**
	 * Sets the current email used.
	 *
	 * @param currentEmailUsed the new current email used
	 */
	public synchronized void setCurrentEmailUsed(EmailConnection currentEmailUsed) {
		this.currentEmailUsed = currentEmailUsed;
	}
	
	
	/**
	 * Sets the twitter list.
	 *
	 * @param twitterList the new twitter list
	 */
	public synchronized void setTwitterList(List<TwitterConnection> twitterList) {
		this.twitterList = twitterList;
	}
	
	/**
	 * Sets the facebook list.
	 *
	 * @param facebookList the new facebook list
	 */
	public synchronized void setFacebookList(List<FacebookConnection> facebookList) {
		this.facebookList = facebookList;
	}
	
	
	/**
	 * Adds the email connection to email list.
	 *
	 * @param emailConnection the email connection
	 */
	public synchronized void addEmailConnectionToEmailList(EmailConnection emailConnection) {
		if(emailList != null && !emailList.contains(emailConnection)) {
			try {
				ReadAndWriteXMLFile.addServiceInstanceToXMLFile(emailConnection);
				emailList.add(emailConnection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Adds the email connections to email list.
	 *
	 * @param emailConnections the email connections
	 */
	public synchronized void addEmailConnectionsToEmailList(List<EmailConnection> emailConnections) {
		if(emailList != null) {
			List<ServiceInstance> to_add = new ArrayList<ServiceInstance>();
			for(EmailConnection email : emailConnections) {
				if(!emailList.contains(email)) {
					to_add.add(email);
				}
			}
			try {
				ReadAndWriteXMLFile.addServiceInstanceToXMLFile(to_add);
				for(ServiceInstance email : to_add) {
					emailList.add((EmailConnection) email);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Adds the twitter function to twitter list.
	 *
	 * @param twitterFunction the twitter function
	 */
	public synchronized void addTwitterFunctionToTwitterList(TwitterConnection twitterFunction) {
		if(twitterList != null && !twitterList.contains(twitterFunction)) {
			try {
				ReadAndWriteXMLFile.addServiceInstanceToXMLFile(twitterFunction);
				twitterList.add(twitterFunction);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Adds the twitter functions to twitter list.
	 *
	 * @param twitterFunctions the twitter functions
	 */
	public synchronized void addTwitterFunctionsToTwitterList(List<TwitterConnection> twitterFunctions) {
		if(twitterList != null) {
			List<ServiceInstance> to_add = new ArrayList<ServiceInstance>();
			for(TwitterConnection twitter : twitterFunctions) {
				if(!twitterList.contains(twitter)) {
					to_add.add(twitter);
				}
			}
			try {
				ReadAndWriteXMLFile.addServiceInstanceToXMLFile(to_add);
				for(ServiceInstance twitter : to_add) {
					twitterList.add((TwitterConnection) twitter);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Adds the facebook connection to facebook list.
	 *
	 * @param facebookConnection the facebook connection
	 */
	public synchronized void addFacebookConnectionToFacebookList(FacebookConnection facebookConnection) {
		if(facebookList != null && !facebookList.contains(facebookConnection)) {
			try {
				ReadAndWriteXMLFile.addServiceInstanceToXMLFile(facebookConnection);
				facebookList.add(facebookConnection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Adds the facebook connections to facebook list.
	 *
	 * @param facebookConnection the facebook connection
	 */
	public synchronized void addFacebookConnectionsToFacebookList(List<FacebookConnection> facebookConnection) {
		if(facebookList != null) {
			List<ServiceInstance> to_add = new ArrayList<ServiceInstance>();
			for(FacebookConnection facebook : facebookConnection) {
				if(!facebookList.contains(facebook)) {
					to_add.add(facebook);
				}
			}
			try {
				ReadAndWriteXMLFile.addServiceInstanceToXMLFile(to_add);
				for(ServiceInstance facebook : to_add) {
					facebookList.add((FacebookConnection) facebook);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Removes the email connection from email list.
	 *
	 * @param emailConnection the email connection
	 */
	public synchronized void removeEmailConnectionFromEmailList(EmailConnection emailConnection) {
		if(emailList != null && emailList.contains(emailConnection)) {
			try {
				ReadAndWriteXMLFile.removeServiceInstanceFromXMLFile(emailConnection);
				emailList.remove(emailConnection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Removes the email connections from email list.
	 *
	 * @param emailConnections the email connections
	 */
	public synchronized void removeEmailConnectionsFromEmailList(List<EmailConnection> emailConnections) {
		if(emailList != null) {
			List<ServiceInstance> to_remove = new ArrayList<ServiceInstance>();
			for(EmailConnection email : emailConnections) {
				if(emailList.contains(email)) {
					to_remove.add(email);
				}
			}
			try {
				ReadAndWriteXMLFile.removeServiceInstanceFromXMLFile(to_remove);
				for(ServiceInstance email : to_remove) {
					emailList.remove(email); // not sure if this works here!
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Removes the twitter function from twitter list.
	 *
	 * @param twitterFunction the twitter function
	 */
	public synchronized void removeTwitterFunctionFromTwitterList(TwitterConnection twitterFunction) {
		if(twitterList != null && twitterList.contains(twitterFunction)) {
			try {
				ReadAndWriteXMLFile.removeServiceInstanceFromXMLFile(twitterFunction);
				twitterList.remove(twitterFunction);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Removes the twitter functions from twitter list.
	 *
	 * @param twitterFunctions the twitter functions
	 */
	public synchronized void removeTwitterFunctionsFromTwitterList(List<TwitterConnection> twitterFunctions) {
		if(twitterList != null) {
			List<ServiceInstance> to_remove = new ArrayList<ServiceInstance>();
			for(TwitterConnection twitter : twitterFunctions) {
				if(twitterList.contains(twitter)) {
					to_remove.add(twitter);
				}
			}
			try {
				ReadAndWriteXMLFile.removeServiceInstanceFromXMLFile(to_remove);
				for(ServiceInstance twitter : to_remove) {
					twitterList.remove(twitter); // not sure if this works here!
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Removes the facebook connection from facebook list.
	 *
	 * @param facebookConnection the facebook connection
	 */
	public synchronized void removeFacebookConnectionFromFacebookList(FacebookConnection facebookConnection) {
		if(facebookList != null && facebookList.contains(facebookConnection)) {
			try {
				ReadAndWriteXMLFile.removeServiceInstanceFromXMLFile(facebookConnection);
				facebookList.remove(facebookConnection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Removes the facebook connections from facebook list.
	 *
	 * @param facebookConnections the facebook connections
	 */
	public synchronized void removeFacebookConnectionsFromFacebookList(List<FacebookConnection> facebookConnections) {
		if(facebookList != null) {
			List<ServiceInstance> to_remove = new ArrayList<ServiceInstance>();
			for(FacebookConnection facebook : facebookConnections) {
				if(facebookList.contains(facebook)) {
					to_remove.add(facebook);
				}
			}
			try {
				ReadAndWriteXMLFile.removeServiceInstanceFromXMLFile(to_remove);
				for(ServiceInstance facebook : to_remove) {
					facebookList.remove(facebook); // not sure if this works here!
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sets the ignore service.
	 *
	 * @param service the service
	 * @param ignore the ignore
	 */
	public synchronized void setIgnoreService(Service service, boolean ignore) {
		switch (service) {
			case EMAIL:
				ignore_email = ignore;
				break;
			case TWITTER:
				ignore_twitter = ignore;
				break;
			case FACEBOOK:
				ignore_facebook = ignore;
				break;
		}
	}
	
	/**
	 * Gets the ignore service.
	 *
	 * @param service the service
	 * @return the ignore service
	 */
	public boolean getIgnoreService(Service service) {
		boolean ignore = false;
		switch (service) {
			case EMAIL:
				ignore = ignore_email;
				break;
			case TWITTER:
				ignore = ignore_twitter;
				break;
			case FACEBOOK:
				ignore = ignore_facebook;
				break;
		}
		return ignore;
	}

}

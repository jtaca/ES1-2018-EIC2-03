package BDA.other;

import java.util.ArrayList;
import java.util.List;

import BDA.email.EmailConnection;
import BDA.facebook.FacebookConnection;
import BDA.files.ReadAndWriteXMLFile;
import BDA.interfaces.ServiceInstance;
import BDA.threads.ThreadPool;
import BDA.threads.Worker;
import BDA.twitter.TwitterConnection;

public class ControlCenter {
	
	private List<EmailConnection> emailList = null;
	private EmailConnection currentEmailUsed = null;
	
	private List<TwitterConnection> twitterList = null;
	private List<FacebookConnection> facebookList = null;
	
	private static final ControlCenter INSTANCE = new ControlCenter();
	
	private ControlCenter() {
	}
	
	public static ControlCenter getInstance() {
		return INSTANCE;
	}
	
	public List<EmailConnection> getEmailList() {
		return emailList;
	}
	
	public EmailConnection getCurrentEmailUsed() {
		return currentEmailUsed;
	}
	
	
	public List<TwitterConnection> getTwitterList() {
		return twitterList;
	}
	
	public List<FacebookConnection> getFacebookList() {
		return facebookList;
	}
	
	public synchronized void setEmailList(List<EmailConnection> emailList) {
		this.emailList = emailList;
	}
	
	public synchronized void setCurrentEmailUsed(EmailConnection currentEmailUsed) {
		this.currentEmailUsed = currentEmailUsed;
	}
	
	
	public synchronized void setTwitterList(List<TwitterConnection> twitterList) {
		this.twitterList = twitterList;
	}
	
	public synchronized void setFacebookList(List<FacebookConnection> facebookList) {
		this.facebookList = facebookList;
	}
	
	
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

}

package other;

import java.io.IOException;

/**
 * The Class XMLUserConfiguration.
 * @author Alexandre Mendes
 * @version 1.0
 */
public class XMLUserConfiguration { //
	
	/** The save information. */
	private boolean saveInformation = false;
	
	/** The service. */
	private Service service; // 0 = email, 1 = facebook, 2 = twitter;
	
	/** The username. */
	private String username = "";
	
	/** The password. */
	private String password = "";
	
	
	private String twitterConsumerKey = "";
	private String twitterSecretKey = "";
	private String twitterAccessToken = "";
	private String twitterAccessTokenSecret = "";
	
	
	public XMLUserConfiguration(boolean saveInformation, Service service, String username, String password, String twitterConsumerKey, String twitterSecretKey, String twitterAccessToken, String twitterAccessTokenSecret) throws Exception {
		if (service == null) {
			throw new Exception("Please insert a valid Service.");
		}
		this.saveInformation = saveInformation;
		this.service = service;
		this.username = username;
		this.password = password;
		this.twitterConsumerKey = twitterConsumerKey;
		this.twitterSecretKey = twitterSecretKey;
		this.twitterAccessToken = twitterAccessToken;
		this.twitterAccessTokenSecret = twitterAccessTokenSecret;
	}
	
	public XMLUserConfiguration(boolean saveInformation, Service service, String twitterConsumerKey, String twitterSecretKey, String twitterAccessToken, String twitterAccessTokenSecret) throws Exception {
		this(saveInformation, service, "", "", twitterConsumerKey, twitterSecretKey, twitterAccessToken, twitterAccessTokenSecret);
	}
	
	public XMLUserConfiguration(boolean saveInformation, Service service, String username, String password) throws Exception {
		this(saveInformation, service, username, password, "", "", "", "");
	}
	
	
	
	
	/**
	 * Instantiates a new XML user configuration.
	 *
	 * @param saveInformation the save information
	 * @param service_number the service number
	 * @param username the username
	 * @param password the password
	 * @throws Exception the exception
	 */
	public XMLUserConfiguration(boolean saveInformation, int service_number, String username, String password) throws Exception {
		this(saveInformation, Service.values()[service_number], username, password);
	}
	
	public XMLUserConfiguration(boolean saveInformation, int service_number, String twitterConsumerKey, String twitterSecretKey, String twitterAccessToken, String twitterAccessTokenSecret) throws Exception {
		this(saveInformation, Service.values()[service_number], "", "", twitterConsumerKey, twitterSecretKey, twitterAccessToken, twitterAccessTokenSecret);
	}
	
	/**
	 * Checks if is information saved.
	 *
	 * @return true, if is information saved
	 */
	public boolean isInformationSaved() {
		return saveInformation;
	}
	
	/**
	 * Information saved as int.
	 *
	 * @return the int
	 */
	private int informationSavedAsInt() {
		if(saveInformation) {
			return 1; // true
		} else {
			return 0; // false
		}
	}
	
	/**
	 * Gets the type of service number.
	 *
	 * @return the type of service number
	 */
	public int getTypeOfServiceNumber() {
		return service.getTypeOfServiceNumber();
	}
	
	/**
	 * Gets the type of service in string.
	 *
	 * @return the type of service in string
	 */
	public String getTypeOfServiceInString() {
		return service.stringFormat();
	}
	
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	
	
	public String getTwitterConsumerKey() {
		return twitterConsumerKey;
	}

	public String getTwitterSecretKey() {
		return twitterSecretKey;
	}

	public String getTwitterAccessToken() {
		return twitterAccessToken;
	}

	public String getTwitterAccessTokenSecret() {
		return twitterAccessTokenSecret;
	}

	@Override
	public String toString() {
		return "XMLUserConfiguration [service=" + service + ", username=" + username + ", password=" + password
				+ ", TWITTER_CONSUMER_KEY=" + twitterConsumerKey + ", TWITTER_SECRET_KEY=" + twitterSecretKey
				+ ", TWITTER_ACCESS_TOKEN=" + twitterAccessToken + ", TWITTER_ACCESS_TOKEN_SECRET="
				+ twitterAccessTokenSecret + "]";
	}
	
}

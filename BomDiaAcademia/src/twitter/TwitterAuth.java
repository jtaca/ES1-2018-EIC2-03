package twitter;

import other.XMLUserConfiguration;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Responsible for every twitter authentication needs.
 * @author DElfim
 * @version 2.0
 */
public class TwitterAuth {
	
	/** The twitter consumer key. */
	private static String TWITTER_CONSUMER_KEY = TwitterConnection.getKeys()[0];
	
	/** The twitter secret key. */
	private static String TWITTER_SECRET_KEY = TwitterConnection.getKeys()[1];
	
	/** The user token. */
	private static AccessToken userToken = null;
	
	/** The twitter. */
	private Twitter twitter = buildAuthenticationTwitter();
	
	/** The twitter keys. */
	private static XMLUserConfiguration twitterKeys = null;
	
	/** The request token. */
	private RequestToken requestToken;
	
	/** The access token. */
	private AccessToken accessToken;


	/**
	 * Prints out an url that the user can go to make the login an then
	 * receives a pin from the user in order to complete authentication.
	 *
	 * @return the auth URL
	 */
	String getAuthURL(){
		try {
			requestToken = twitter.getOAuthRequestToken("oob");
		} catch (TwitterException e) {
			return "";
		}
		String url = requestToken.getAuthenticationURL();
		System.out.println(url);
		return url;
	}
	
	/**
	 * Input pin.
	 *
	 * @param s the s
	 * @return true, if successful
	 */
	boolean inputPin(String s){
		try {
			accessToken = twitter.getOAuthAccessToken(requestToken, s);
			twitter.setOAuthAccessToken(accessToken);
			userToken=accessToken;
		} catch (TwitterException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Builds the authentication twitter.
	 *
	 * @return the twitter
	 */
	private Twitter buildAuthenticationTwitter(){
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setDebugEnabled(true).setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
		builder.setOAuthConsumerSecret(TWITTER_SECRET_KEY);
		Configuration configuration = builder.build();

		TwitterFactory factory = new TwitterFactory(configuration);
		return factory.getInstance();
	}
	
	/**
	 * Returns an authenticated instance of the object Twitter.
	 * @return Twitter
	 */
	Twitter authenticatedInstance(){
		if (twitter==null){
			twitter=buildAuthenticationTwitter();
		}
		if(userToken==null)return null;
		twitter.setOAuthAccessToken(userToken);
		return twitter;
	}
	
	/**
	 * Gets the twitter.
	 *
	 * @return the twitter
	 */
	Twitter getTwitter(){
		return this.twitter;
	}
}

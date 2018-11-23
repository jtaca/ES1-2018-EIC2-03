package twitter;

import java.util.Scanner;

import files.ReadAndWriteXMLFile;
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
 *
 */
public class Logger {
	/**
	 * 
	 */
	private static String TWITTER_CONSUMER_KEY = TwitterFunctions.getKeys()[0];
	private static String TWITTER_SECRET_KEY = TwitterFunctions.getKeys()[1];
	private static AccessToken userToken = null;
	private Twitter twitter = buildAuthenticationTwitter();
	private static XMLUserConfiguration twitterKeys = null;
	private RequestToken requestToken;
	private AccessToken accessToken;


	/**
	 * Prints out an url that the user can go to make the login an then
	 * receives a pin from the user in order to complete authentication.
	 */
	public String getAuthURL(){
		try {
			requestToken = twitter.getOAuthRequestToken("oob");
		} catch (TwitterException e) {
			return "";
		}
		String url = requestToken.getAuthenticationURL();
		System.out.println(url);
		return url;
	}
	public boolean inputPin(String s){
		try {
			accessToken = twitter.getOAuthAccessToken(requestToken, s);
			twitter.setOAuthAccessToken(accessToken);
			userToken=accessToken;
		} catch (TwitterException e) {
			return false;
		}
		return true;
	}
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
	public Twitter authenticatedInstance(){
		if (twitter==null){
			twitter=buildAuthenticationTwitter();
		}
		if(userToken==null)return null;
		twitter.setOAuthAccessToken(userToken);
		return twitter;
	}
	public Twitter getTwitter(){
		return this.twitter;
	}
}

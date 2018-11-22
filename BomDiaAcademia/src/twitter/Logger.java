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
	private static Twitter twitter = null;
	private static XMLUserConfiguration twitterKeys = null;



	/**
	 * Prints out an url that the user can go to make the login an then
	 * receives a pin from the user in order to complete authentication.
	 */
	public void getAuthUrl() {
		
//		try {
//			TWITTER_CONSUMER_KEY = Utils.getConsumerKeyFromXML();
//			TWITTER_SECRET_KEY = Utils.getSecretKeyFromXML();
//		} catch (Exception e1) {
//			System.out.println("Could not get api keys from xml file(Twitter)");
//			e1.printStackTrace();
//		}
//		
		
		twitter = buildAuthenticationTwitter();
		twitterAutentication(twitter);

	}
	private Twitter buildAuthenticationTwitter(){
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setDebugEnabled(true).setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
		builder.setOAuthConsumerSecret(TWITTER_SECRET_KEY);
		Configuration configuration = builder.build();

		TwitterFactory factory = new TwitterFactory(configuration);
		return factory.getInstance();
	}
	private void twitterAutentication(Twitter twitter){
		RequestToken requestToken;
		AccessToken accessToken;
		Scanner in = new Scanner(System.in);
		try {
			requestToken = twitter.getOAuthRequestToken("oob");
			String url = requestToken.getAuthenticationURL();
			System.out.println(url);
			System.out.println("Enter pin pls ...");
			accessToken = twitter.getOAuthAccessToken(requestToken, in.nextLine());
			twitter.setOAuthAccessToken(accessToken);
			userToken=accessToken;

		} catch (TwitterException e) {
			e.printStackTrace();
		}
		in.close();
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

package twitter;

import java.util.Scanner;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Logger {
	static final String TWITTER_CONSUMER_KEY = "MMhfibuBOYCRvcSYhu7CGm8eE";
	static final String TWITTER_SECRET_KEY = "K5OAA4YwnC6w93Xb0xbvbkbqHNnJqfH3byx4hNV0TvLp7V0Cqs";
	static final String TWITTER_ACCESS_TOKEN = "2389545732-pusPUzJqBCmMxx3iwW6k0G6xMfSn2hyXzl2Hsdw";
	static final String TWITTER_ACCESS_TOKEN_SECRET = "RNfBwVLc7aqTiNZfv2PAWByf7w6QigG43Ni89BRZVrbs4";
	private static AccessToken userToken = null;
	private static Twitter twitter;



	public void getAuthUrl() {
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setDebugEnabled(true).setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
		builder.setOAuthConsumerSecret(TWITTER_SECRET_KEY);
		Configuration configuration = builder.build();

		TwitterFactory factory = new TwitterFactory(configuration);
		twitter = factory.getInstance();
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

	public static Twitter authenticatedInstance(){
		if (twitter==null){
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setDebugEnabled(true).setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
		builder.setOAuthConsumerSecret(TWITTER_SECRET_KEY);
		Configuration configuration = builder.build();

		TwitterFactory factory = new TwitterFactory(configuration);
		twitter = factory.getInstance();
		twitter.setOAuthAccessToken(userToken);

		}
		return twitter;
	}
}

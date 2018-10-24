package twitter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entry_objects.InformationEntry;
import entry_objects.TwitterEntry;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterFunctions {

	private static final String TWITTER_CONSUMER_KEY = "MMhfibuBOYCRvcSYhu7CGm8eE";
	private static final String TWITTER_SECRET_KEY = "K5OAA4YwnC6w93Xb0xbvbkbqHNnJqfH3byx4hNV0TvLp7V0Cqs";
	private static final String TWITTER_ACCESS_TOKEN = "2389545732-pusPUzJqBCmMxx3iwW6k0G6xMfSn2hyXzl2Hsdw";
	private static final String TWITTER_ACCESS_TOKEN_SECRET = "RNfBwVLc7aqTiNZfv2PAWByf7w6QigG43Ni89BRZVrbs4";

	private static Twitter twitter;

	private static void init() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
		cb.setOAuthConsumerSecret(TWITTER_SECRET_KEY);
		cb.setOAuthAccessToken(TWITTER_ACCESS_TOKEN);
		cb.setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}

	public void retweet(Status tweet) throws TwitterException {
		Logger.authenticatedInstance().retweetStatus(tweet.getId());
	}

	public List<InformationEntry> requestTwitter() {
		List<InformationEntry> list = new ArrayList<>();

		if (twitter == null)
			init();

		try {
			Query query = new Query("ISCTE");
			QueryResult result;
			do {
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				tweets.sort(Comparator.comparing(Status::getCreatedAt).reversed());
				for (Status tweet : tweets) {
					list.add(new TwitterEntry(tweet));
					System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getCreatedAt() + " - "
							+ tweet.getText());
				}
			} while ((query = result.nextQuery()) != null);
			return list;
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
			return null;
		}
	}

	public static List<Status> getTweetsForUser(int ammount, String user) {
		List<Status> statuses = null;

		if (twitter == null)
			init();

		Paging paging = new Paging(1, ammount);

		try {
			statuses = twitter.getUserTimeline(user, paging);
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		return statuses;
	}

	public static List<Status> getTweetsForUsers(int ammount, String... users) {
		List<Status> statuses = null, temp;

		for (String user : users)
			if ((temp = getTweetsForUser(ammount, user)) != null)
				if (statuses == null)
					statuses = temp;
				else
					statuses.addAll(temp);

		if (statuses != null)
			statuses.sort(Comparator.comparing(Status::getCreatedAt).reversed());

		return statuses;
	}
}
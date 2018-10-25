package twitter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entry_objects.InformationEntry;
import entry_objects.TwitterEntry;
import files.ReadAndWriteXMLFile;
import other.XMLUserConfiguration;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author DElfim
 *
 */
public class TwitterFunctions {

	/**
	 * 
	 */
	private static String TWITTER_CONSUMER_KEY = null;
	private static String TWITTER_SECRET_KEY = null;
	private static String TWITTER_ACCESS_TOKEN = null;
	private static String TWITTER_ACCESS_TOKEN_SECRET = null;

	private static Twitter twitter;
	private static XMLUserConfiguration twitterKeys = null;
	/**
	 * 
	 * 
	 */
	private static void init() {
		try {
			twitterKeys = ReadAndWriteXMLFile.ReadConfigXMLFile().get(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TWITTER_CONSUMER_KEY = twitterKeys.getTWITTER_CONSUMER_KEY();
		TWITTER_SECRET_KEY = twitterKeys.getTWITTER_SECRET_KEY();
		TWITTER_ACCESS_TOKEN = twitterKeys.getTWITTER_ACCESS_TOKEN();
		TWITTER_ACCESS_TOKEN_SECRET = twitterKeys.getTWITTER_ACCESS_TOKEN_SECRET();
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
		cb.setOAuthConsumerSecret(TWITTER_SECRET_KEY);
		cb.setOAuthAccessToken(TWITTER_ACCESS_TOKEN);
		cb.setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}

	/**
	 * @param tweet
	 * @throws TwitterException
	 */
	public void retweet(Status tweet) throws TwitterException {
		Logger.authenticatedInstance().retweetStatus(tweet.getId());
	}

	/**
	 * @return
	 * @throws Exception 
	 */
	public List<InformationEntry> requestTwitter() throws Exception {
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

	/**
	 * @param ammount
	 * @param user
	 * @return List<InformationEntry>
	 * @throws Exception 
	 */
	public static List<InformationEntry> getTweetsForUser(int ammount, String user) throws Exception {
		List<InformationEntry> tweets = new ArrayList<>();

		if (twitter == null)
			init();

		try {
			twitter.getUserTimeline(user, new Paging(1, ammount)).forEach(s -> tweets.add(new TwitterEntry(s)));
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		return tweets;
	}

	/**
	 * @param ammount
	 * @param users
	 * @return List<InformationEntry>
	 * @throws Exception 
	 */
	public static List<InformationEntry> getTweetsForUsers(int ammount, String... users) throws Exception {
		List<InformationEntry> tweets = new ArrayList<>();

		for (String user : users)
			tweets.addAll(getTweetsForUser(ammount, user));

		tweets.sort(Comparator.comparing(InformationEntry::getDate).reversed());

		return tweets;
	}
}
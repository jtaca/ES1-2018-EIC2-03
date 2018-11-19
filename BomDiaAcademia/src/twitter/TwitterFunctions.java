package twitter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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
	private static String TWITTER_CONSUMER_KEY = "3wp8mBeehnAcKIEmjzefEm3Z3";
	private static String TWITTER_SECRET_KEY = "VapBDLbqMo4pDxp3iKIsj6IoyhdlNHa3pcaNAVcRpSmv72xuAV";
	private static String TWITTER_ACCESS_TOKEN = "2389545732-RHAjyOlFNBSCJgSI2zILX1sXdAS2ptYfiRUlRJG";
	private static String TWITTER_ACCESS_TOKEN_SECRET = "Yu34xSNj0XNxtD5X1dTecO1G9zGd0rKWlg83BRAjW1zO7";

	private static Twitter twitter;
	private static XMLUserConfiguration twitterKeys = null;

	/**
	 * 
	 * 
	 */
	private static void init() {
//		try {
//			twitterKeys = ReadAndWriteXMLFile.ReadConfigXMLFile().get(1);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		TWITTER_CONSUMER_KEY = twitterKeys.getTWITTER_CONSUMER_KEY();
//		TWITTER_SECRET_KEY = twitterKeys.getTWITTER_SECRET_KEY();
//		TWITTER_ACCESS_TOKEN = twitterKeys.getTWITTER_ACCESS_TOKEN();
//		TWITTER_ACCESS_TOKEN_SECRET = twitterKeys.getTWITTER_ACCESS_TOKEN_SECRET();
		if(twitter==null){
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true);
			cb.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
			cb.setOAuthConsumerSecret(TWITTER_SECRET_KEY);
			cb.setOAuthAccessToken(TWITTER_ACCESS_TOKEN);
			cb.setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);
			TwitterFactory tf = new TwitterFactory(cb.build());
			twitter = tf.getInstance();
		}
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

	public static List<InformationEntry> getTweets(int ammount) {
		List<InformationEntry> tweets = new ArrayList<>();
		List<String> users;
		
			init();
		
		try {
			users = ReadAndWriteXMLFile.getTwitterUsers();
			
			for(String user : users)
				twitter.getUserTimeline(user, new Paging(1, ammount)).forEach(status -> tweets.add(new TwitterEntry(status)));
			
		} catch (Exception e) {	
			e.printStackTrace();
		}
		
		return tweets;
	}
	
	/**
	 * @param ammount
	 * @param user
	 * @return List<InformationEntry>
	 * @throws Exception
	 */
	public static List<InformationEntry> getTweetsForUser(int ammount, String user) throws Exception {
		List<InformationEntry> tweets = new ArrayList<>();
		
			init();

		try {
			twitter.getUserTimeline(user, new Paging(1, ammount)).forEach(s -> tweets.add(new TwitterEntry(s)));
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		return tweets;
	}
	
	public static List<InformationEntry> getTweetsForUserByDate(Date date, long l) throws Exception {
		List<InformationEntry> tweets = new ArrayList<>();

			init();

		try {
			twitter.getUserTimeline(l).forEach(s -> {
			if(s.getCreatedAt().after(date))
			tweets.add(new TwitterEntry(s));
			});
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
	
	/*
	public static List<InformationEntry> getTweetsForUser() {
		for(String filter : Filter.getFilter())
			tweeter.addAll(getTweetsForUser(filter));
	}
	*/

	public static String[] getKeys() {
		return new String[] { TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY, TWITTER_ACCESS_TOKEN,
				TWITTER_ACCESS_TOKEN_SECRET };
	}
}
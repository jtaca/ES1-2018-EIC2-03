package twitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import entry_objects.InformationEntry;
import entry_objects.TwitterEntry;
import files.ReadAndWriteXMLFile;
import other.Filter;
import other.Service;
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
 * Used to execute every twitter interaction.
 * @author DElfim
 *
 */
public class TwitterFunctions {

	/**
	 * 
	 */
	private static String TWITTER_CONSUMER_KEY = "k4a4y5Wcq3UqdGKs9R6CufWoA";
	private static String TWITTER_SECRET_KEY = "WTSpB0qE4IS1EpeHA2mAhC5C8wD3iUYqihg5AIBVeIhplHgR8w";
	private static String TWITTER_ACCESS_TOKEN = "2389545732-VxLp2gwOAuv2hV7cHXV96uYT7LNDPiFTLFf5MRi";
	private static String TWITTER_ACCESS_TOKEN_SECRET = "6c0V85yaqaSo5kvLll4tZxDdneQWOhfU78HMucmUM8VZn";

	private static Twitter twitter = init();
	private static XMLUserConfiguration twitterKeys = null;
	private Logger logger= new Logger();
	
	private static Twitter init() {
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
		
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true);
			cb.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
			cb.setOAuthConsumerSecret(TWITTER_SECRET_KEY);
			cb.setOAuthAccessToken(TWITTER_ACCESS_TOKEN);
			cb.setOAuthAccessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET);
			TwitterFactory tf = new TwitterFactory(cb.build());
			twitter = tf.getInstance();
		
		return twitter;
	}
	
	/**
	 * Receives the Status object equivalent to the tweet the user wants to retweet
	 * fails if the user is not authenticated.
	 * @param tweet
	 * @throws TwitterException
	 */
	public void retweet(Status tweet) throws TwitterException {
		Twitter t = logger.authenticatedInstance();
		if(t!=null){
			t.retweetStatus(tweet.getId());
		}else{
			System.out.println("E nessessario efetuar login para utilizar esta funcao");
		}
	}

	/**
	 * Returns a List with a basic query from twitter with the String "ISCTE".
	 * @return List<InformationEntry>
	 * @throws Exception
	 */
	public static List<InformationEntry> requestTwitter() throws Exception {
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
	public static List<InformationEntry> getTweetsFiltered(){
		init();
		Filter f = Filter.getInstance();
		List<InformationEntry> l=null;
		try {
			l=getTweetsFromUsers(f.getDate(), f.getFilterList(Service.TWITTER).toArray(new String[0]));
		} catch (Exception e) {
			System.out.println("getFilterList retorna lista com objetos do tipo errado ou retorna null");
			e.printStackTrace();
		}
		return l;
	}

	/**
	 * Returns a List of all tweets done by the user referred, after the designated date.
	 * @param date
	 * @param user
	 * @return List<InformationEntry>
	 * @throws Exception
	 */
	public static List<InformationEntry> getTweetsFromUserByDate(Date date, String user) {
		List<InformationEntry> tweets = new ArrayList<>();
		try {
			twitter.getUserTimeline(user).forEach(s -> {
			if(s.getCreatedAt().after(date))
	//		if(s.getCreatedAt().compareTo(date)>=0)
			tweets.add(new TwitterEntry(s));
			});
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		return tweets;
	}

	/**
	 * Returns a List of all tweets done by the users referred, after the designated date.
	 * @param date
	 * @param users
	 * @return List<InformationEntry>
	 * @throws Exception
	 */
	public static List<InformationEntry> getTweetsFromUsers(Date date, String... users) {
		List<InformationEntry> tweets = new ArrayList<>();
		if(users!=null)
		for (String user : users)
			tweets.addAll(getTweetsFromUserByDate(date, user));

		tweets.sort(Comparator.comparing(InformationEntry::getDate).reversed());

		return tweets;
	}
	
	public static Status getSomeStatus(){
		List<InformationEntry> l = getTweetsFiltered();
		TwitterEntry te = (TwitterEntry) l.get(0);
		return te.getStatus();
	}

	public static String[] getKeys() {
		return new String[] { TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY, TWITTER_ACCESS_TOKEN,
				TWITTER_ACCESS_TOKEN_SECRET };
	}
}

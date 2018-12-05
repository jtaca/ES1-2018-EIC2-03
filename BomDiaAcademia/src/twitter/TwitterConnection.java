package twitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import entry_objects.InformationEntry;
import entry_objects.TwitterEntry;
import files.ReadAndWriteXMLFile;
import interfaces.ServiceInstance;
import other.Filter;
import other.Service;
import other.XMLUserConfiguration;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Used to execute every twitter interaction.
 * @author DElfim
 * @version 2.0
 */
public class TwitterConnection implements ServiceInstance {
	private static TwitterConnection INSTANCE=null;

	/** The twitter consumer key. */
	private static final String TWITTER_CONSUMER_KEY = "k4a4y5Wcq3UqdGKs9R6CufWoA";
	
	/** The twitter secret key. */
	private static final String TWITTER_SECRET_KEY = "WTSpB0qE4IS1EpeHA2mAhC5C8wD3iUYqihg5AIBVeIhplHgR8w";
	
	/** The twitter access token. */
	private static final String TWITTER_ACCESS_TOKEN = "2389545732-VxLp2gwOAuv2hV7cHXV96uYT7LNDPiFTLFf5MRi";
	
	/** The twitter access token secret. */
	private static final String TWITTER_ACCESS_TOKEN_SECRET = "6c0V85yaqaSo5kvLll4tZxDdneQWOhfU78HMucmUM8VZn";

	/** The twitter. */
	private Twitter twitter;
	
	/** The twitter keys. */
	private static XMLUserConfiguration twitterKeys = null;
	
	/** The logger. */
	private final TwitterAuth logger= new TwitterAuth();
	
	//Initialization
	public static TwitterConnection getInstance() {
	      if(INSTANCE == null) {
	    	  synchronized (TwitterConnection.class){
	    		  if(INSTANCE == null) {
	    			  INSTANCE = new TwitterConnection();
	    		  }
	    	  }
	      }
	      return INSTANCE;
	   }
	
	
	
	private TwitterConnection(){
		this.twitter=init();
	}
	
	
	
	/**
	 * Inits the.
	 *
	 * @return the twitter
	 */
	private Twitter init() {
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
	//Authentication
	public String getAuthUrl(){
		return logger.getAuthURL();
	}
	public boolean confirmAuth(String s){
		return logger.inputPin(s);
	}
	public boolean isLoggedIn(){
		return logger.isLoggedIn();
	}
	public void logout(){
		logger.logout();
	}
	
	//Auth Only Operations
	/**
	 * Receives the Status object equivalent to the tweet the user wants to retweet
	 * fails if the user is not authenticated.
	 *
	 * @param tweet the tweet
	 * @throws TwitterException the twitter exception
	 */
	public boolean retweet(Status tweet){
		Twitter t = logger.authenticatedInstance();
		if(t!=null){
			try {
				t.retweetStatus(tweet.getId());
				return true;
			} catch (TwitterException e) {
				return false;
			}
		}else{
			System.out.println("E nessessario efetuar login para utilizar esta funcao");
		}
		return false;
	}
	
	public boolean tweet(String s) {
		Twitter t = logger.authenticatedInstance();
		if(t!=null){
			try {
				t.updateStatus(s);
				return true;
			} catch (TwitterException e) {
				return false;
			}
		}else{
			System.out.println("E nessessario efetuar login para utilizar esta funcao");
		}
		return false;
	}
	public String getUsername(){
		try {
			return logger.authenticatedInstance().getScreenName();
		} catch (IllegalStateException e ) {
			
		} catch (TwitterException e) {
			
		}
		return "";
	}
	public boolean favoriteTweet(Status tweet){
		Twitter t = logger.authenticatedInstance();
		if(t!=null){
			try {
				t.createFavorite(tweet.getId());
				return true;
			} catch (TwitterException e) {
				return false;
			}
		}else{
			System.out.println("E nessessario efetuar login para utilizar esta funcao");
		}
		return false;
	}
	public boolean commentTweet(Status tweet,String comment){
		Twitter t = logger.authenticatedInstance();
		if(t!=null){
			try {
				StatusUpdate statusUpdate = new StatusUpdate(comment);
				statusUpdate.inReplyToStatusId(tweet.getId()); 
				Status status = twitter.updateStatus(statusUpdate);
			} catch (TwitterException e) {
				return false;
			}
		}else{
			System.out.println("E nessessario efetuar login para utilizar esta funcao");
		}
		return false;
	}
	public boolean deletePost(Status tweet){
		Twitter t = logger.authenticatedInstance();
		if(t!=null){
			try {
				t.destroyStatus(tweet.getId());
			} catch (TwitterException e) {
				return false;
			}
		}else{
			System.out.println("E nessessario efetuar login para utilizar esta funcao");
		}
		return false;
	}

	//Querys
	/**
	 * Returns a List with a basic query from twitter with the String "ISCTE".
	 *
	 * @return List<InformationEntry>
	 * @throws Exception the exception
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
	
	/**
	 * Gets the tweets filtered.
	 *
	 * @return the tweets filtered
	 */
	public List<InformationEntry> getTweetsFiltered(){
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
	 *
	 * @param date the date
	 * @param user the user
	 * @return List<InformationEntry>
	 */
	public List<InformationEntry> getTweetsFromUserByDate(Date date, String user) {
		List<InformationEntry> tweets = new ArrayList<>();
		try {
			twitter.getUserTimeline(user).forEach(s -> {
			if(s.getCreatedAt().after(date))
			tweets.add(new TwitterEntry(s));
			});
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		return tweets;
	}

	/**
	 * Returns a List of all tweets done by the users referred, after the designated date.
	 *
	 * @param date the date
	 * @param users the users
	 * @return List<InformationEntry>
	 */
	public List<InformationEntry> getTweetsFromUsers(Date date, String... users) {
		List<InformationEntry> tweets = new ArrayList<>();
		if(users!=null)
		for (String user : users)
			tweets.addAll(getTweetsFromUserByDate(date, user));

		tweets.sort(Comparator.comparing(InformationEntry::getDate).reversed());

		return tweets;
	}
	
//	public static Status getSomeStatus(){
//		Filter.getInstance().defineDateIntervalFromCurrentDate(24);
//		List<InformationEntry> l = getTweetsFiltered();
//		TwitterEntry te = (TwitterEntry) l.get(0);
//		return te.getStatus();
/**
 * Gets the some retweet.
 *
 * @return the some retweet
 */
//	}
	public Status getSomeRetweet(){
		Filter.getInstance().defineDateIntervalFromCurrentDate(24);
		List<InformationEntry> l = getTweetsFiltered();
		TwitterEntry te=null;
		for(InformationEntry elem : l){
			te=(TwitterEntry)elem;
			if(te.getStatus().isRetweet())break;
		}
		return te.getStatus();
	}
	public String getUserPicture(String user) throws TwitterException{
		User u = twitter.showUser(user);
		return u.get400x400ProfileImageURL();
	}

	/**
	 * Gets the keys.
	 *
	 * @return the keys
	 */
	public static String[] getKeys() {
		return new String[] { TWITTER_CONSUMER_KEY, TWITTER_SECRET_KEY, TWITTER_ACCESS_TOKEN,
				TWITTER_ACCESS_TOKEN_SECRET };
	}



	@Override
	public Service getService() {
		return Service.TWITTER;
	}
}

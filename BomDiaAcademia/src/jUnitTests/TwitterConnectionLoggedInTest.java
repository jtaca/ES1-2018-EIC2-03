package jUnitTests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import twitter.TwitterConnection;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

public class TwitterConnectionLoggedInTest {
		
		public static final AccessToken at= new AccessToken(TwitterConnection.getKeys()[2], TwitterConnection.getKeys()[3]);
		public static final Status status = TwitterConnection.getInstance().getSomeStatus();
		@BeforeClass
		public static void presetUp(){
			TwitterConnection tf = TwitterConnection.getInstance();
			tf.setUserToken(at);
			tf.deleteRetweet(status);
		}
		@Before
		public void setUp(){
			TwitterConnection.getInstance().setUserToken(at);
		}
	//Authetication functions
		@Test
		public void testGetAuthUrl(){
			String s = TwitterConnection.getInstance().getAuthUrl();
			assert(s=="");
		}
		@Test
		public void testIsLoggedIn(){
			assert(TwitterConnection.getInstance().isLoggedIn());
		}
		@Test
		public void testLogout(){
			TwitterConnection.getInstance().logout();
			assert(!TwitterConnection.getInstance().isLoggedIn());
		}
		
		//LoggedIn functions
		@Test
		public void testGetUsername(){
			String s=TwitterConnection.getInstance().getUsername();
			assertEquals(s,"delfim75524641");
		}
		@Test
		public void testRetweet() throws TwitterException {
			
			TwitterConnection tf = TwitterConnection.getInstance();
			assert(tf.retweet(status));
			assert(tf.isRetweetedbyMe(status));
			assert(tf.deleteRetweet(status));
		}
		
		@Test
		public void testTweetnComment() {
			assert(TwitterConnection.getInstance().tweet(""+((Math.random()*1000))));
			List<Status> list;
			try {
				list = TwitterConnection.getInstance().getTwitter().getUserTimeline();
				assert(TwitterConnection.getInstance().commentTweet(list.get(0), ""+((Math.random()*1000))));
				assert(TwitterConnection.getInstance().deletePost(list.get(0)));
			} catch (TwitterException e) {
				fail();
			}
			
		}
		@Test
		public void testFavorite(){
			assert(TwitterConnection.getInstance().favoriteTweet(status));
			assert(TwitterConnection.getInstance().isFavorited(status));
			assert(TwitterConnection.getInstance().unFavoriteTweet(status));
		}
		
}

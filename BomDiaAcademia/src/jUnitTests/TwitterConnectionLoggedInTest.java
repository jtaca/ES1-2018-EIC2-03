package jUnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import entry_objects.InformationEntry;
import other.Filter;
import twitter.TwitterConnection;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Scopes;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.TwitterException;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;
import twitter4j.auth.AccessToken;

public class TwitterConnectionLoggedInTest {
		
		public static final AccessToken at= new AccessToken(TwitterConnection.getKeys()[2], TwitterConnection.getKeys()[3]);
		public static final Status status = new TwitterConnectionLoggedInTest().new TestStatus(1064992747311063040L);
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
			Status status = new TestStatus(1064992747311063040L);
			TwitterConnection tf = TwitterConnection.getInstance();
			assert(tf.retweet(status));
			assert(tf.isRetweetedbyMe(status));
			assert(tf.deleteRetweet(status));
		}
		
		
		public class TestStatus implements Status{
			long post_id=-1L;
			public TestStatus(long l){
				this.post_id=l;
			}
			@Override
			public int compareTo(Status o) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getAccessLevel() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public RateLimitStatus getRateLimitStatus() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public HashtagEntity[] getHashtagEntities() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public MediaEntity[] getMediaEntities() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public SymbolEntity[] getSymbolEntities() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public URLEntity[] getURLEntities() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public UserMentionEntity[] getUserMentionEntities() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long[] getContributors() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Date getCreatedAt() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getCurrentUserRetweetId() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getDisplayTextRangeEnd() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getDisplayTextRangeStart() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getFavoriteCount() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public GeoLocation getGeoLocation() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getId() {
				// TODO Auto-generated method stub
				return this.post_id;
			}

			@Override
			public String getInReplyToScreenName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getInReplyToStatusId() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long getInReplyToUserId() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String getLang() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Place getPlace() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Status getQuotedStatus() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getQuotedStatusId() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public URLEntity getQuotedStatusPermalink() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getRetweetCount() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Status getRetweetedStatus() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Scopes getScopes() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getSource() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getText() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public User getUser() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String[] getWithheldInCountries() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isFavorited() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isPossiblySensitive() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRetweet() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRetweeted() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isRetweetedByMe() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isTruncated() {
				// TODO Auto-generated method stub
				return false;
			}
			
		}

}

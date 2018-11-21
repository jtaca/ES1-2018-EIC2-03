package jUnitTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import entry_objects.InformationEntry;
import twitter.TwitterFunctions;
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

public class TwitterFunctionsTesting {

	@Test
	public void testRetweet() {
		Status status = new TestStatus(1064992747311063040L);
		TwitterFunctions tf = new  TwitterFunctions();
		try {
			tf.retweet(status);
		} catch (TwitterException e) {
			System.out.println("erro no retweet");
			fail();
		}
	}

	@Test
	public void testRequestTwitter() {
		List<InformationEntry> l=new ArrayList();
		try {
			l = TwitterFunctions.requestTwitter();
		} catch (Exception e) {
			fail("failed to get tweets");
			e.printStackTrace();
		}
		assertNotNull(l);
	}

	@Test
	public void testGetTweetsFromUser() {
		
	}

	@Test
	public void testGetTweetsFromUserByDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTweetsFromUsers() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetKeys() {
		String [] l=TwitterFunctions.getKeys();
		assertEquals("k4a4y5Wcq3UqdGKs9R6CufWoA", l[0]);
		assertEquals("WTSpB0qE4IS1EpeHA2mAhC5C8wD3iUYqihg5AIBVeIhplHgR8w", l[1]);
		assertEquals("2389545732-VxLp2gwOAuv2hV7cHXV96uYT7LNDPiFTLFf5MRi", l[2]);
		assertEquals("6c0V85yaqaSo5kvLll4tZxDdneQWOhfU78HMucmUM8VZn", l[3]);
	}
	
	private class TestStatus implements Status{
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

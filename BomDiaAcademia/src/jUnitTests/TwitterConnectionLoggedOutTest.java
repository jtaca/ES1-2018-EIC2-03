package jUnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import entry_objects.InformationEntry;
import other.Filter;
import twitter.TwitterConnection;
import twitter4j.Status;
import twitter4j.TwitterException;

public class TwitterConnectionLoggedOutTest {
	@BeforeClass
	public static void setUp(){
		TwitterConnection.getInstance().logout();
	}
	//Authetication functions
	@Test
	public void testGetAuthUrl(){
		String s = TwitterConnection.getInstance().getAuthUrl();
		assert(s!="");
	}
	@Test
	public void testInputPin(){
		assert(!TwitterConnection.getInstance().confirmAuth("asdasd"));
	}
	@Test
	public void testIsLoggedIn(){
		assert(!TwitterConnection.getInstance().isLoggedIn());
	}
	
	//LoggedIn functions
	@Test
	public void testGetUsername(){
		String s=TwitterConnection.getInstance().getUsername();
		assertEquals(s,"");
	}
	@Test
	public void testRetweet() throws TwitterException {
		TwitterConnection tf = TwitterConnection.getInstance();
		assert(!tf.retweet(tf.getSomeStatus()));
	}
	@Test
	public void testFailWhenOffFunctions() {
		TwitterConnection tc= TwitterConnection.getInstance();
		Status status=tc.getSomeStatus();
		assert(!tc.tweet(""));
		assert(!tc.favoriteTweet(status));
		assert(!tc.commentTweet(status, ""));
		assert(!tc.isRetweetedbyMe(status));
		assert(!tc.deleteRetweet(status));
	}

	@Test
	public void testRequestTwitter() throws Exception {
		List<InformationEntry> l=new ArrayList<InformationEntry>();
		l = TwitterConnection.getInstance().requestTwitter();
		assertNotNull(l);
	}
	

	@Test
	public void testGetTweetsFromUserByDate() {
		Calendar c= Calendar.getInstance();
		c.set(2018, Calendar.OCTOBER , 14,23,0,0);
		Date d = c.getTime();
		List<InformationEntry> l;
		l=TwitterConnection.getInstance().getTweetsFromUserByDate(d, "ANACLET28324119");
		assert(l.isEmpty());
		c.set(2018, Calendar.OCTOBER , 14,0,0,0);
		d=c.getTime();
		l=TwitterConnection.getInstance().getTweetsFromUserByDate(d, "ANACLET28324119");
		assertEquals(l.get(0).toString(),"ola meu outra vez");
		assertEquals(l.get(1).toString(),"ola meu.");
	}

	@Test
	public void testGetTweetsFromUsers() {
		List<InformationEntry> l;
		Calendar c= Calendar.getInstance();
		c.set(2018, Calendar.OCTOBER , 14,0,0,0);
		Date date = c.getTime();
		String [] users=null;
		l=TwitterConnection.getInstance().getTweetsFromUsers(date, users);
		assert(l.isEmpty());
		users=new String[0];
		l=TwitterConnection.getInstance().getTweetsFromUsers(date, users);
		assert(l.isEmpty());
		users=new String[1];
		users[0]="ANACLET28324119";
		l=TwitterConnection.getInstance().getTweetsFromUsers(date, users);
		assert(!l.isEmpty());
		assertEquals(l.get(0).toString(),"ola meu outra vez");
		assertEquals(l.get(1).toString(),"ola meu.");
	}
	@Test 
	public void testGetTweetsFiltered(){
		Filter.getInstance().defineDateIntervalFromCurrentDate(24);
		List<InformationEntry> l=TwitterConnection.getInstance().getTweetsFiltered();
		assert(!l.isEmpty());
	}
	@Test
	public void testGetKeys() {
		String [] l=TwitterConnection.getKeys();
		assertEquals("k4a4y5Wcq3UqdGKs9R6CufWoA", l[0]);
		assertEquals("WTSpB0qE4IS1EpeHA2mAhC5C8wD3iUYqihg5AIBVeIhplHgR8w", l[1]);
		assertEquals("2389545732-VxLp2gwOAuv2hV7cHXV96uYT7LNDPiFTLFf5MRi", l[2]);
		assertEquals("6c0V85yaqaSo5kvLll4tZxDdneQWOhfU78HMucmUM8VZn", l[3]);
	}
	//Logger Testing
	
//	@Test
//	public void TestLogin(){
//		TwitterAuth l = new TwitterAuth();
//		String s=l.getAuthURL();
//		assert(!s.isEmpty());
//		boolean b= l.inputPin("asdasdasd");
//		assert(!b);
//	}
//	

	
	
}

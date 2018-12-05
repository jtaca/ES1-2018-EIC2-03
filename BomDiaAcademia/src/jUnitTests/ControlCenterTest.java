package jUnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import email.EmailConnection;
import facebook.FacebookConnection;
import files.ReadAndWriteXMLFile;
import other.ControlCenter;
import other.XMLUserConfiguration;
import tasks.EmailReaderTask;
import tasks.FacebookPostReaderTask;
import tasks.TwitterPostReaderTask;
import twitter.TwitterFunctions;

public class ControlCenterTest {
	
	private static ControlCenter control_center = ControlCenter.getInstance();
	
	private static List<EmailConnection> EMAIL_LIST = new ArrayList<EmailConnection>();
	private static EmailConnection CURRENT_EMAIL_USED = null;
	
	private static List<TwitterFunctions> TWITTER_LIST = new ArrayList<TwitterFunctions>();
	private static List<FacebookConnection> FACEBOOK_LIST = new ArrayList<FacebookConnection>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		List<XMLUserConfiguration> xml_user_list = ReadAndWriteXMLFile.ReadConfigXMLFile();
		boolean firstEmail = true;
		
		EmailConnection email;
		TwitterFunctions twitter;
		FacebookConnection facebook;
		
		for(XMLUserConfiguration xml_user : xml_user_list) {
			switch (xml_user.getService()) {
			case EMAIL:
				email = new EmailConnection(xml_user.getUsername(), xml_user.getPassword());
				if(firstEmail == true) {
					firstEmail = false;
					CURRENT_EMAIL_USED = email;
				}
				EMAIL_LIST.add(email);
				break;
				
			case TWITTER:
				twitter = TwitterFunctions.getInstance(); // Require to be implemented object oriented and not function oriented
				TWITTER_LIST.add(twitter);
				break;
				
			case FACEBOOK:
				facebook = FacebookConnection.getInstance(); // Require to be implemented object oriented and not function oriented
				FACEBOOK_LIST.add(facebook);
				break;
			}
			
			
		}
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		EMAIL_LIST = null;
		CURRENT_EMAIL_USED = null;
		TWITTER_LIST = null;
		FACEBOOK_LIST = null;
	}

	@Before
	public void setUp() throws Exception {
		control_center.setCurrentEmailUsed(CURRENT_EMAIL_USED);
		control_center.setEmailList(EMAIL_LIST);
		control_center.setTwitterList(TWITTER_LIST);
		control_center.setFacebookList(FACEBOOK_LIST);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetEmailList() {
		List<EmailConnection> emailList = control_center.getEmailList();
		assertEquals(EMAIL_LIST, emailList);
	}
	
	@Test
	public void testGetCurrentEmailUsed() {
		EmailConnection email = control_center.getCurrentEmailUsed();
		assertEquals(CURRENT_EMAIL_USED, email);
	}
	
	@Test
	public void testGetTwitterList() {
		List<TwitterFunctions> twitterList = control_center.getTwitterList();
		assertEquals(TWITTER_LIST, twitterList);
	}
	
	@Test
	public void testGetFacebookList() {
		List<FacebookConnection> facebookList = control_center.getFacebookList();
		assertEquals(FACEBOOK_LIST, facebookList);
	}
	
	@Test
	public void testSetEmailList() {
		List<EmailConnection> expected_list = new ArrayList<EmailConnection>();
		expected_list.add(new EmailConnection("dummy", "dummy"));
		control_center.setEmailList(expected_list);
		List<EmailConnection> actual_list = control_center.getEmailList();
		assertEquals(expected_list, actual_list);
	}
	
	@Test
	public void testSetCurrentEmailUsed() {
		EmailConnection expected = new EmailConnection("dummy", "dummy");
		control_center.setCurrentEmailUsed(expected);
		EmailConnection actual = control_center.getCurrentEmailUsed();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testSetTwitterList() {
		List<TwitterFunctions> expected_list = new ArrayList<TwitterFunctions>();
		expected_list.add(TwitterFunctions.getInstance());
		control_center.setTwitterList(expected_list);
		List<TwitterFunctions> actual_list = control_center.getTwitterList();
		assertEquals(expected_list, actual_list);
	}
	
	@Test
	public void testSetFacebookList() {
		List<FacebookConnection> expected_list = new ArrayList<FacebookConnection>();
		expected_list.add(FacebookConnection.getInstance());
		control_center.setFacebookList(expected_list);
		List<FacebookConnection> actual_list = control_center.getFacebookList();
		assertEquals(expected_list, actual_list);
	}
	
	
	@Test
	public void testAddEmailConnectionToEmailList() {
		List<EmailConnection> expected_list = EMAIL_LIST;
		EmailConnection email = new EmailConnection("dummy", "dummy");
		expected_list.add(email);
		control_center.addEmailConnectionToEmailList(email);
		List<EmailConnection> actual_list = control_center.getEmailList();
		assertEquals(expected_list, actual_list);
	}
	
	@Test
	public void testAddTwitterFunctionToTwitterList() {
		control_center.setTwitterList(new ArrayList<TwitterFunctions>());
		List<TwitterFunctions> expected_list = new ArrayList<TwitterFunctions>();
		TwitterFunctions twitter = TwitterFunctions.getInstance();
		expected_list.add(twitter);
		control_center.addTwitterFunctionToTwitterList(twitter);
		List<TwitterFunctions> actual_list = control_center.getTwitterList();
		assertEquals(expected_list, actual_list);
	}
	
	@Test
	public void testAddFacebookConnectionToFacebookList() {
		control_center.setFacebookList(new ArrayList<FacebookConnection>());
		List<FacebookConnection> expected_list = new ArrayList<FacebookConnection>();
		FacebookConnection facebook = FacebookConnection.getInstance();
		expected_list.add(facebook);
		control_center.addFacebookConnectionToFacebookList(facebook);
		List<FacebookConnection> actual_list = control_center.getFacebookList();
		assertEquals(expected_list, actual_list);
	}
	
	@Test
	public void testAddEmailConnectionsToEmailList() {
		List<EmailConnection> expected_list = EMAIL_LIST;
		EmailConnection email = new EmailConnection("dummy", "dummy");
		EmailConnection email2 = new EmailConnection("dummy2", "dummy2");
		List<EmailConnection> emailList = new ArrayList<EmailConnection>();
		emailList.add(email);
		emailList.add(email2);
		expected_list.addAll(emailList);
		control_center.addEmailConnectionsToEmailList(emailList);
		List<EmailConnection> actual_list = control_center.getEmailList();
		assertEquals(expected_list, actual_list);
	}
	
	@Test
	public void testAddTwitterFunctionsToTwitterList() {
		control_center.setTwitterList(new ArrayList<TwitterFunctions>());
		List<TwitterFunctions> expected_list = new ArrayList<TwitterFunctions>();
		TwitterFunctions twitter = TwitterFunctions.getInstance();
		TwitterFunctions twitter2 = TwitterFunctions.getInstance();
		List<TwitterFunctions> twitterList = new ArrayList<TwitterFunctions>();
		twitterList.add(twitter);
		twitterList.add(twitter2);
		expected_list.addAll(twitterList);
		control_center.addTwitterFunctionsToTwitterList(twitterList);
		List<TwitterFunctions> actual_list = control_center.getTwitterList();
		assertEquals(expected_list, actual_list);
	}

}

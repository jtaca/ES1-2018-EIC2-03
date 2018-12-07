package jUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import BDA.email.EmailConnection;
import BDA.facebook.FacebookConnection;
import BDA.files.ReadAndWriteXMLFile;
import BDA.other.ControlCenter;
import BDA.other.Service;
import BDA.other.XMLUserConfiguration;
import BDA.twitter.TwitterConnection;

/**
 * The Class ControlCenterTest.
 */
public class ControlCenterTest {
	
	/** The control center. */
	private static ControlCenter control_center = ControlCenter.getInstance();
	
	/** The email list. */
	private static List<EmailConnection> EMAIL_LIST = new ArrayList<EmailConnection>();
	
	/** The current email used. */
	private static EmailConnection CURRENT_EMAIL_USED = null;
	
	/** The twitter list. */
	private static List<TwitterConnection> TWITTER_LIST = new ArrayList<TwitterConnection>();
	
	/** The facebook list. */
	private static List<FacebookConnection> FACEBOOK_LIST = new ArrayList<FacebookConnection>();

	/**
	 * Sets the up before class.
	 *
	 * @throws Exception the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		List<XMLUserConfiguration> xml_user_list = ReadAndWriteXMLFile.ReadConfigXMLFile();
		boolean firstEmail = true;
		
		EmailConnection email;
		TwitterConnection twitter;
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
				twitter = TwitterConnection.getInstance(); // Require to be implemented object oriented and not function oriented
				TWITTER_LIST.add(twitter);
				break;
				
			case FACEBOOK:
				facebook = FacebookConnection.getInstance(); // Require to be implemented object oriented and not function oriented
				FACEBOOK_LIST.add(facebook);
				break;
			}
			
			
		}
		
	}

	/**
	 * Tear down after class.
	 *
	 * @throws Exception the exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		EMAIL_LIST = null;
		CURRENT_EMAIL_USED = null;
		TWITTER_LIST = null;
		FACEBOOK_LIST = null;
	}

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		control_center.setCurrentEmailUsed(CURRENT_EMAIL_USED);
		control_center.setEmailList(EMAIL_LIST);
		control_center.setTwitterList(TWITTER_LIST);
		control_center.setFacebookList(FACEBOOK_LIST);
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test get email list.
	 */
	@Test
	public void testGetEmailList() {
		List<EmailConnection> emailList = control_center.getEmailList();
		assertEquals(EMAIL_LIST, emailList);
	}
	
	/**
	 * Test get current email used.
	 */
	@Test
	public void testGetCurrentEmailUsed() {
		EmailConnection email = control_center.getCurrentEmailUsed();
		assertEquals(CURRENT_EMAIL_USED, email);
	}
	
	/**
	 * Test get twitter list.
	 */
	@Test
	public void testGetTwitterList() {
		List<TwitterConnection> twitterList = control_center.getTwitterList();
		assertEquals(TWITTER_LIST, twitterList);
	}
	
	/**
	 * Test get facebook list.
	 */
	@Test
	public void testGetFacebookList() {
		List<FacebookConnection> facebookList = control_center.getFacebookList();
		assertEquals(FACEBOOK_LIST, facebookList);
	}
	
	/**
	 * Test set email list.
	 */
	@Test
	public void testSetEmailList() {
		List<EmailConnection> expected_list = new ArrayList<EmailConnection>();
		expected_list.add(new EmailConnection("dummy", "dummy"));
		control_center.setEmailList(expected_list);
		List<EmailConnection> actual_list = control_center.getEmailList();
		assertEquals(expected_list, actual_list);
	}
	
	/**
	 * Test set current email used.
	 */
	@Test
	public void testSetCurrentEmailUsed() {
		EmailConnection expected = new EmailConnection("dummy", "dummy");
		control_center.setCurrentEmailUsed(expected);
		EmailConnection actual = control_center.getCurrentEmailUsed();
		assertEquals(expected, actual);
	}
	
	/**
	 * Test set twitter list.
	 */
	@Test
	public void testSetTwitterList() {
		List<TwitterConnection> expected_list = new ArrayList<TwitterConnection>();
		expected_list.add(TwitterConnection.getInstance());
		control_center.setTwitterList(expected_list);
		List<TwitterConnection> actual_list = control_center.getTwitterList();
		assertEquals(expected_list, actual_list);
	}
	
	/**
	 * Test set facebook list.
	 */
	@Test
	public void testSetFacebookList() {
		List<FacebookConnection> expected_list = new ArrayList<FacebookConnection>();
		expected_list.add(FacebookConnection.getInstance());
		control_center.setFacebookList(expected_list);
		List<FacebookConnection> actual_list = control_center.getFacebookList();
		assertEquals(expected_list, actual_list);
	}
	
	
	/**
	 * Test add email connection to email list.
	 */
	@Test
	public void testAddEmailConnectionToEmailList() {
		control_center.setEmailList(new ArrayList<EmailConnection>());
		List<EmailConnection> expected_list = new ArrayList<EmailConnection>();
		EmailConnection email = new EmailConnection("dummy", "dummy");
		expected_list.add(email);
		control_center.addEmailConnectionToEmailList(email);
		List<EmailConnection> actual_list = control_center.getEmailList();
		assertEquals(expected_list, actual_list);
	}
	
	/**
	 * Test add twitter function to twitter list.
	 */
	@Test
	public void testAddTwitterFunctionToTwitterList() {
		control_center.setTwitterList(new ArrayList<TwitterConnection>());
		List<TwitterConnection> expected_list = new ArrayList<TwitterConnection>();
		TwitterConnection twitter = TwitterConnection.getInstance();
		expected_list.add(twitter);
		control_center.addTwitterFunctionToTwitterList(twitter);
		List<TwitterConnection> actual_list = control_center.getTwitterList();
		assertEquals(expected_list, actual_list);
	}
	
	/**
	 * Test add facebook connection to facebook list.
	 */
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
	
	/**
	 * Test add email connections to email list.
	 */
	@Test
	public void testAddEmailConnectionsToEmailList() {
		control_center.setEmailList(new ArrayList<EmailConnection>());
		List<EmailConnection> expected_list = new ArrayList<EmailConnection>();
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
	
	/**
	 * Test add twitter functions to twitter list.
	 */
	@Test
	public void testAddTwitterFunctionsToTwitterList() {
		control_center.setTwitterList(new ArrayList<TwitterConnection>());
		List<TwitterConnection> expected_list = new ArrayList<TwitterConnection>();
		TwitterConnection twitter = TwitterConnection.getInstance();
		TwitterConnection twitter2 = TwitterConnection.getInstance();
		List<TwitterConnection> twitterList = new ArrayList<TwitterConnection>();
		twitterList.add(twitter);
		twitterList.add(twitter2);
		expected_list.addAll(twitterList);
		control_center.addTwitterFunctionsToTwitterList(twitterList);
		List<TwitterConnection> actual_list = control_center.getTwitterList();
		assertEquals(expected_list, actual_list);
	}
	
	/**
	 * Test add facebook connections to facebook list.
	 */
	@Test
	public void testAddFacebookConnectionsToFacebookList() {
		control_center.setFacebookList(new ArrayList<FacebookConnection>());
		List<FacebookConnection> expected_list = new ArrayList<FacebookConnection>();
		FacebookConnection facebook = FacebookConnection.getInstance();
		FacebookConnection facebook2 = FacebookConnection.getInstance();
		List<FacebookConnection> facebookList = new ArrayList<FacebookConnection>();
		facebookList.add(facebook);
		facebookList.add(facebook2);
		expected_list.addAll(facebookList);
		control_center.addFacebookConnectionsToFacebookList(facebookList);
		List<FacebookConnection> actual_list = control_center.getFacebookList();
		assertEquals(expected_list, actual_list);
	}
	
	/**
	 * Test remove email connection from email list.
	 */
	@Test
	public void testRemoveEmailConnectionFromEmailList() {
		List<EmailConnection> emailList = new ArrayList<EmailConnection>();
		EmailConnection email = new EmailConnection("dummy", "dummy");
		emailList.add(email);
		control_center.setEmailList(emailList);
		control_center.removeEmailConnectionFromEmailList(email);
		List<EmailConnection> actual_list = control_center.getEmailList();
		assertTrue(actual_list.isEmpty());
	}
	
	/**
	 * Test remove twitter function from twitter list.
	 */
	@Test
	public void testRemoveTwitterFunctionFromTwitterList() {
		List<TwitterConnection> twitterList = new ArrayList<TwitterConnection>();
		TwitterConnection twitter = TwitterConnection.getInstance();
		twitterList.add(twitter);
		control_center.setTwitterList(twitterList);
		control_center.removeTwitterFunctionFromTwitterList(twitter);
		List<TwitterConnection> actual_list = control_center.getTwitterList();
		assertTrue(actual_list.isEmpty());
	}
	
	/**
	 * Test remove facebook connection from facebook list.
	 */
	@Test
	public void testRemoveFacebookConnectionFromFacebookList() {
		List<FacebookConnection> facebookList = new ArrayList<FacebookConnection>();
		FacebookConnection facebook = FacebookConnection.getInstance();
		facebookList.add(facebook);
		control_center.setFacebookList(facebookList);
		control_center.removeFacebookConnectionFromFacebookList(facebook);
		List<FacebookConnection> actual_list = control_center.getFacebookList();
		assertTrue(actual_list.isEmpty());
	}
	
	/**
	 * Test remove email connections from email list.
	 */
	@Test
	public void testRemoveEmailConnectionsFromEmailList() {
		List<EmailConnection> emailList = new ArrayList<EmailConnection>();
		EmailConnection email = new EmailConnection("dummy", "dummy");
		emailList.add(email);
		control_center.setEmailList(emailList);
		control_center.removeEmailConnectionsFromEmailList(emailList);
		List<EmailConnection> actual_list = control_center.getEmailList();
		assertTrue(actual_list.isEmpty());
	}
	
	/**
	 * Test remove twitter functions from twitter list.
	 */
	@Test
	public void testRemoveTwitterFunctionsFromTwitterList() {
		List<TwitterConnection> twitterList = new ArrayList<TwitterConnection>();
		TwitterConnection twitter = TwitterConnection.getInstance();
		twitterList.add(twitter);
		control_center.setTwitterList(twitterList);
		control_center.removeTwitterFunctionsFromTwitterList(twitterList);
		List<TwitterConnection> actual_list = control_center.getTwitterList();
		assertTrue(actual_list.isEmpty());
	}
	
	/**
	 * Test remove facebook connections from facebook list.
	 */
	@Test
	public void testRemoveFacebookConnectionsFromFacebookList() {
		List<FacebookConnection> facebookList = new ArrayList<FacebookConnection>();
		FacebookConnection facebook = FacebookConnection.getInstance();
		facebookList.add(facebook);
		control_center.setFacebookList(facebookList);
		control_center.removeFacebookConnectionsFromFacebookList(facebookList);
		List<FacebookConnection> actual_list = control_center.getFacebookList();
		assertTrue(actual_list.isEmpty());
	}
	
	/**
	 * Test set and get ignore service email.
	 */
	@Test
	public void testSetAndGetIgnoreServiceEmail() {
		ControlCenter.getInstance().setIgnoreService(Service.EMAIL, true);
		boolean actual_boolean = ControlCenter.getInstance().getIgnoreService(Service.EMAIL);
		assertTrue(actual_boolean);
	}
	
	/**
	 * Test set and get ignore service twitter.
	 */
	@Test
	public void testSetAndGetIgnoreServiceTwitter() {
		ControlCenter.getInstance().setIgnoreService(Service.TWITTER, true);
		boolean actual_boolean = ControlCenter.getInstance().getIgnoreService(Service.TWITTER);
		assertTrue(actual_boolean);
	}
	
	/**
	 * Test set and get ignore service facebook.
	 */
	@Test
	public void testSetAndGetIgnoreServiceFacebook() {
		ControlCenter.getInstance().setIgnoreService(Service.FACEBOOK, true);
		boolean actual_boolean = ControlCenter.getInstance().getIgnoreService(Service.FACEBOOK);
		assertTrue(actual_boolean);
	}

}

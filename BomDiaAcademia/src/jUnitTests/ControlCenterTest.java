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
import other.XMLUserConfiguration;
import tasks.EmailReaderTask;
import tasks.FacebookPostReaderTask;
import tasks.TwitterPostReaderTask;
import twitter.TwitterFunctions;

public class ControlCenterTest {
	
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
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}

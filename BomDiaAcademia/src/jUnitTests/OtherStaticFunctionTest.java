package jUnitTests;

import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jfoenix.controls.JFXListView;

import BDA.email.EmailConnection;
import BDA.entry_objects.InformationEntry;
import BDA.facebook.FacebookConnection;
import BDA.files.ReadAndWriteXMLFile;
import BDA.gui.MainController;
import BDA.gui.MainWindow;
import BDA.gui.PostBox;
import BDA.other.ControlCenter;
import BDA.other.OtherStaticFunction;
import BDA.other.XMLUserConfiguration;
import BDA.tasks.EmailReaderTask;
import BDA.tasks.FacebookPostReaderTask;
import BDA.tasks.ServiceReadTask;
import BDA.tasks.TwitterPostReaderTask;
import BDA.twitter.TwitterConnection;

/**
 * The Class OtherStaticFunctionTest.
 */
public class OtherStaticFunctionTest {

	/**
	 * Sets the up before class.
	 *
	 * @throws Exception the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Thread t = new Thread("MainWindow Thread") {
			public void run() {
				MainWindow.main(new String[0]);
			}
		};

		t.setDaemon(true);
		t.start();
		Thread.sleep(500);
		
		MainController.getInstance().loadPosts(new ArrayList<InformationEntry>(), true);
	}

	/**
	 * Tear down after class.
	 *
	 * @throws Exception the exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
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
	 * Test refresh GUI with threads with no info on control center.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testRefreshGUIWithThreadsWithNoInfoOnControlCenter() throws Exception {
		JFXListView<PostBox> previousExecutionPostBox = MainController.getInstance().getPosts();
		ServiceReadTask task;
		List<ServiceReadTask> tasks = new ArrayList<ServiceReadTask>();
		List<XMLUserConfiguration> list_of_user_configuration = ReadAndWriteXMLFile.ReadConfigXMLFile();
		for(XMLUserConfiguration xml_user_config : list_of_user_configuration) {
			switch (xml_user_config.getService()) {
			case EMAIL:
				task = new EmailReaderTask(new EmailConnection(xml_user_config.getUsername(), xml_user_config.getPassword()));
				break;
				
			case TWITTER:
				task = new TwitterPostReaderTask(); // Require to be implemented object oriented and not function oriented
				break;
				
			case FACEBOOK:
				task = new FacebookPostReaderTask(); // needs implementation still...
				break;

			default:
				task = null;
				break;
			}
			if(task != null) {
				tasks.add(task);
			}
		}
		
		OtherStaticFunction.refreshGUIWithThreads();
		
		long valueNeededToWaitBeforeCheckOnList = 60000;
		Thread.sleep(valueNeededToWaitBeforeCheckOnList);
		
		JFXListView<PostBox> actualExecutionPostBox = MainController.getInstance().getPosts();
		
		assertNotEquals(previousExecutionPostBox, actualExecutionPostBox);
	}
	
	/**
	 * Test refresh GUI with threads with info on control center.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testRefreshGUIWithThreadsWithInfoOnControlCenter() throws Exception {
		JFXListView<PostBox> previousExecutionPostBox = MainController.getInstance().getPosts();
		ServiceReadTask task;
		List<ServiceReadTask> tasks = new ArrayList<ServiceReadTask>();
		List<XMLUserConfiguration> list_of_user_configuration = ReadAndWriteXMLFile.ReadConfigXMLFile();
		
		List<EmailConnection> email_list = new ArrayList<EmailConnection>();
		List<TwitterConnection> twitter_list = new ArrayList<TwitterConnection>();
		List<FacebookConnection> facebook_list = new ArrayList<FacebookConnection>();
		
		EmailConnection email;
		TwitterConnection twitter;
		FacebookConnection facebook;
		
		for(XMLUserConfiguration xml_user_config : list_of_user_configuration) {
			switch (xml_user_config.getService()) {
			case EMAIL:
				email = new EmailConnection(xml_user_config.getUsername(), xml_user_config.getPassword());
				task = new EmailReaderTask(email);
				email_list.add(email);
				break;
				
			case TWITTER:
				twitter = TwitterConnection.getInstance();
				task = new TwitterPostReaderTask(); // Require to be implemented object oriented and not function oriented
				twitter_list.add(twitter);
				break;
				
			case FACEBOOK:
				facebook = FacebookConnection.getInstance();
				task = new FacebookPostReaderTask(); // needs implementation still...
				facebook_list.add(facebook);
				break;

			default:
				task = null;
				break;
			}
			if(task != null) {
				tasks.add(task);
			}
		}
		
		ControlCenter.getInstance().setEmailList(email_list);
		ControlCenter.getInstance().setTwitterList(twitter_list);
		ControlCenter.getInstance().setFacebookList(facebook_list);
		
		OtherStaticFunction.refreshGUIWithThreads();
		
		long valueNeededToWaitBeforeCheckOnList = 60000;
		Thread.sleep(valueNeededToWaitBeforeCheckOnList);
		
		JFXListView<PostBox> actualExecutionPostBox = MainController.getInstance().getPosts();
		
		assertNotEquals(previousExecutionPostBox, actualExecutionPostBox);
	}

}

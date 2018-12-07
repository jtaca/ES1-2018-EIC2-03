package jUnitTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jfoenix.controls.JFXListView;

import BDA.email.EmailConnection;
import BDA.entry_objects.InformationEntry;
import BDA.files.ReadAndWriteXMLFile;
import BDA.gui.MainController;
import BDA.gui.MainWindow;
import BDA.gui.PostBox;
import BDA.other.XMLUserConfiguration;
import BDA.tasks.EmailReaderTask;
import BDA.tasks.ServiceReadTask;
import BDA.threads.ThreadPool;

/**
 * The Class ThreadPoolTest.
 */
public class ThreadPoolTest {
	
	/** The user. */
	private static XMLUserConfiguration user = null;
	
	/**
	 * Before class.
	 *
	 * @throws Exception the exception
	 */
	@BeforeClass
	public static void beforeClass() throws Exception {
		user = ReadAndWriteXMLFile.ReadConfigXMLFile().get(0);
		
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
	 * Test start threads.
	 */
	@Test
	public void testStartThreads() {
		ThreadPool.getInstance().startThreads();
		boolean threadsWorking = ThreadPool.getInstance().getThreadsWorking();
		assertTrue(threadsWorking);
	}
	
	/**
	 * Test stop threads.
	 */
	@Test
	public void testStopThreads() {
		ThreadPool.getInstance().startThreads();
		ThreadPool.getInstance().stopThreads();
		boolean threadsWorking = ThreadPool.getInstance().getThreadsWorking();
		assertFalse(threadsWorking);
	}
	
	/**
	 * Test stop threads when they arent running.
	 */
	@Test
	public void testStopThreadsWhenTheyArentRunning() {
		ThreadPool.getInstance().stopThreads();
		boolean threadsWorking = ThreadPool.getInstance().getThreadsWorking();
		assertFalse(threadsWorking);
	}
	
	/**
	 * Test refresh GUI with threads.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	public void testRefreshGUIWithThreads() throws InterruptedException {
		List<ServiceReadTask> tasks = new ArrayList<ServiceReadTask>();
		
		// Need to initialize the MainController before everything...
		
		JFXListView<PostBox> previousPostBox = MainController.getInstance().getPosts();
		tasks.add(new EmailReaderTask(new EmailConnection(user.getUsername(), user.getPassword())));
		ThreadPool.refreshGUIWithThreads(tasks);
		long valueNeededToWaitBeforeCheckOnList = 60000;
		Thread.sleep(valueNeededToWaitBeforeCheckOnList);
		JFXListView<PostBox> afterExecutionPostBox = MainController.getInstance().getPosts();
		// Precisamos de uma maneira de verificar se a Lista foi parar mesmo ao GUI
		assertNotEquals(previousPostBox, afterExecutionPostBox);
//		fail("Require assert to the list that it creates");
	}

}

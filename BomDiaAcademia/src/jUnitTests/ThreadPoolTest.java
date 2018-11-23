package jUnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jfoenix.controls.JFXListView;

import email.EmailConnection;
import files.ReadAndWriteXMLFile;
import gui.MainController;
import gui.MainWindow;
import gui.PostBox;
import other.OtherStaticFunction;
import other.XMLUserConfiguration;
import tasks.EmailReaderTask;
import tasks.ServiceReadTask;
import threads.ThreadPool;

public class ThreadPoolTest {
	
	private static XMLUserConfiguration user = null;
	
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
	}

	@Test
	public void testStartThreads() {
		ThreadPool.getInstance().startThreads();
		boolean threadsWorking = ThreadPool.getInstance().getThreadsWorking();
		assertTrue(threadsWorking);
	}
	
	@Test
	public void testStopThreads() {
		ThreadPool.getInstance().startThreads();
		ThreadPool.getInstance().stopThreads();
		boolean threadsWorking = ThreadPool.getInstance().getThreadsWorking();
		assertFalse(threadsWorking);
	}
	
	@Test
	public void testStopThreadsWhenTheyArentRunning() {
		ThreadPool.getInstance().stopThreads();
		boolean threadsWorking = ThreadPool.getInstance().getThreadsWorking();
		assertFalse(threadsWorking);
	}
	
	@Test
	public void testRefreshGUIWithThreads() {
		List<ServiceReadTask> tasks = new ArrayList<ServiceReadTask>();
		
		// Need to initialize the MainController before everything...
		
		JFXListView<PostBox> previousPostBox = MainController.getInstance().getPosts();
		tasks.add(new EmailReaderTask(new EmailConnection(user.getUsername(), user.getPassword())));
		ThreadPool.refreshGUIWithThreads(tasks);
		long valueNeededToWaitBeforeCheckOnList = 60000;
		try {
			Thread.sleep(valueNeededToWaitBeforeCheckOnList);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		JFXListView<PostBox> afterExecutionPostBox = MainController.getInstance().getPosts();
		// Precisamos de uma maneira de verificar se a Lista foi parar mesmo ao GUI
		assertNotEquals(previousPostBox, afterExecutionPostBox);
//		fail("Require assert to the list that it creates");
	}

}

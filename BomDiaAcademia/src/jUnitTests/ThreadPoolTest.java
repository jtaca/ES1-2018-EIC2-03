package jUnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import email.EmailConnection;
import files.ReadAndWriteXMLFile;
import other.OtherStaticFunction;
import other.XMLUserConfiguration;
import tasks.EmailReaderTask;
import tasks.ServiceReadTask;
import threads.ThreadPool;

public class ThreadPoolTest {
	
	private static XMLUserConfiguration user = null;
	
	@BeforeClass
	public static void beforeClass() {
		try {
			user = ReadAndWriteXMLFile.ReadConfigXMLFile().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		tasks.add(new EmailReaderTask(new EmailConnection(user.getUsername(), user.getPassword())));
		ThreadPool.refreshGUIWithThreads(tasks);
		// Precisamos de uma maneira de verificar se a Lista foi parar mesmo ao GUI
		fail("Require assert to the list that it creates");
	}

}

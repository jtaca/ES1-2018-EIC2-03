package jUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import entry_objects.EmailEntry;
import entry_objects.TwitterEntry;
import gui.MainWindow;
import gui.PostBox;
import javafx.geometry.Pos;
import twitter.TwitterConnection;

public class PostBoxTest {

	@BeforeClass
	public static void setUpClass() throws InterruptedException {
		Thread t = new Thread("MainWindow Test Thread") {
			public void run() {
				MainWindow.main(new String[0]);
			}
		};

		t.setDaemon(true);
		t.start();
		Thread.sleep(500);
	}

	@Test
	public void testEmailPostBox() {
		EmailEntry email = new EmailEntry("Receiver", new Date(), "Writer", "Subject", "Content");
		PostBox postBox = new PostBox(email);

		assertTrue(postBox.getService().equals(email.getService()));
	}

	@Test
	public void testTwitterPostBox() {
		TwitterEntry tweet = new TwitterEntry(TwitterConnection.getInstance().getSomeRetweet());
		PostBox postBox = new PostBox(tweet);

		assertTrue(postBox.getService().equals(tweet.getService()));
	}

	@Test
	public void testLoadingPostBox() {
		PostBox postBox = new PostBox(null);

		assertTrue(postBox.getAlignment().equals(Pos.CENTER));
	}

	@Test
	public void testGetDate() {
		EmailEntry email = new EmailEntry("Receiver", new Date(), "Writer", "Subject", "Content");
		PostBox postBox = new PostBox(email);

		assertEquals(postBox.getDate(), email.getDate());
	}

	@Test
	public void testGetPostAuthor() {
		EmailEntry email = new EmailEntry("Receiver", new Date(), "Writer", "Subject", "Content");
		PostBox postBox = new PostBox(email);

		assertEquals(postBox.getPostAuthor(), email.getWriterName());
	}

	@Test
	public void testGetEmailReceiver() {
		EmailEntry email = new EmailEntry("Receiver", new Date(), "Writer", "Subject", "Content");
		PostBox postBox = new PostBox(email);

		assertEquals(postBox.getEmailReceiver(), email.getReceiverEmail());
	}

	@Test
	public void testGetInformationEntry() {
		EmailEntry email = new EmailEntry("Receiver", new Date(), "Writer", "Subject", "Content");
		PostBox postBox = new PostBox(email);

		assertEquals(postBox.getInformationEntry(), email);
	}
}
package jUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import BDA.entry_objects.EmailEntry;
import BDA.entry_objects.TwitterEntry;
import BDA.gui.MainWindow;
import BDA.gui.PostBox;
import BDA.twitter.TwitterConnection;
import javafx.geometry.Pos;

/**
 * The Class PostBoxTest.
 */
public class PostBoxTest {

	/**
	 * Sets the up class.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
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

	/**
	 * Test email post box.
	 */
	@Test
	public void testEmailPostBox() {
		EmailEntry email = new EmailEntry("Receiver", new Date(), "Writer", "Subject", "Content");
		PostBox postBox = new PostBox(email);

		assertTrue(postBox.getService().equals(email.getService()));
	}

	/**
	 * Test twitter post box.
	 */
	@Test
	public void testTwitterPostBox() {
		TwitterEntry tweet = new TwitterEntry(TwitterConnection.getInstance().getSomeRetweet());
		PostBox postBox = new PostBox(tweet);

		assertTrue(postBox.getService().equals(tweet.getService()));
	}

	/**
	 * Test loading post box.
	 */
	@Test
	public void testLoadingPostBox() {
		PostBox postBox = new PostBox(null);

		assertTrue(postBox.getAlignment().equals(Pos.CENTER));
	}

	/**
	 * Test get date.
	 */
	@Test
	public void testGetDate() {
		EmailEntry email = new EmailEntry("Receiver", new Date(), "Writer", "Subject", "Content");
		PostBox postBox = new PostBox(email);

		assertEquals(postBox.getDate(), email.getDate());
	}

	/**
	 * Test get post author.
	 */
	@Test
	public void testGetPostAuthor() {
		EmailEntry email = new EmailEntry("Receiver", new Date(), "Writer", "Subject", "Content");
		PostBox postBox = new PostBox(email);

		assertEquals(postBox.getPostAuthor(), email.getWriterName());
	}

	/**
	 * Test get email receiver.
	 */
	@Test
	public void testGetEmailReceiver() {
		EmailEntry email = new EmailEntry("Receiver", new Date(), "Writer", "Subject", "Content");
		PostBox postBox = new PostBox(email);

		assertEquals(postBox.getEmailReceiver(), email.getReceiverEmail());
	}

	/**
	 * Test get information entry.
	 */
	@Test
	public void testGetInformationEntry() {
		EmailEntry email = new EmailEntry("Receiver", new Date(), "Writer", "Subject", "Content");
		PostBox postBox = new PostBox(email);

		assertEquals(postBox.getInformationEntry(), email);
	}
}
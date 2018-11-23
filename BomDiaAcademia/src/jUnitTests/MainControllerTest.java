package jUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.Semaphore;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import entry_objects.EmailEntry;
import entry_objects.InformationEntry;
import entry_objects.TwitterEntry;
import gui.MainController;
import gui.MainWindow;
import gui.PostBox;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import twitter.TwitterFunctions;

public class MainControllerTest {

	private MainController controller;
	private Class<? extends MainController> cl;

	@BeforeClass
	public static void setUpClass() throws InterruptedException {
		Thread t = new Thread("MainWindow Thread") {
			public void run() {
				MainWindow.main(new String[0]);
			}
		};

		t.setDaemon(true);
		t.start();
		Thread.sleep(500);
	}

	@Before
	public void setup() {
		controller = MainController.getInstance();
		cl = controller.getClass();
	}

	@Test
	public void testToPostBoxEmailEntry() throws Exception {
		EmailEntry email = new EmailEntry(new Date(), "Writer Name", "Subject", "Content");

		Method toPostBox = cl.getDeclaredMethod("toPostBox", InformationEntry.class);

		toPostBox.setAccessible(true);

		PostBox postBox = (PostBox) toPostBox.invoke(controller, email);

		assertEquals(email, postBox.getInformationEntry());
		assertEquals(email.getService(), postBox.getService());
	}

	@Test
	public void testToPostBoxTwitterEntry() throws Exception {
		TwitterEntry tweet = new TwitterEntry(TwitterFunctions.getSomeRetweet());

		Method toPostBox = cl.getDeclaredMethod("toPostBox", InformationEntry.class);

		toPostBox.setAccessible(true);

		PostBox postBox = (PostBox) toPostBox.invoke(controller, tweet);

		assertEquals(tweet, postBox.getInformationEntry());
		assertEquals(tweet.getService(), postBox.getService());
	}

	@Test
	public void testOpenPostEmailEntry() throws Exception {
		EmailEntry email = new EmailEntry(new Date(), "Writer Name", "Subject", "Content");
		Method openPost = cl.getDeclaredMethod("openPost", InformationEntry.class);

		Field postFooter = cl.getDeclaredField("postFooter");
		Field emailFooter = cl.getDeclaredField("emailFooter");

		postFooter.setAccessible(true);
		emailFooter.setAccessible(true);

		openPost.setAccessible(true);
		openPost.invoke(controller, email);
		waitForRunLater();

		StackPane footer = (StackPane) postFooter.get(controller);
		HBox efooter = (HBox) emailFooter.get(controller);

		assertEquals(efooter, footer.getChildren().get(footer.getChildren().size() - 1));
	}

	@Test
	public void testOpenPostTwitterEntry() throws Exception {
		TwitterEntry tweet = new TwitterEntry(TwitterFunctions.getSomeRetweet());
		Method openPost = cl.getDeclaredMethod("openPost", InformationEntry.class);

		Field postFooter = cl.getDeclaredField("postFooter");
		Field twitterFooter = cl.getDeclaredField("twitterFooter");

		postFooter.setAccessible(true);
		twitterFooter.setAccessible(true);

		openPost.setAccessible(true);
		openPost.invoke(controller, tweet);
		waitForRunLater();

		StackPane footer = (StackPane) postFooter.get(controller);
		HBox tfooter = (HBox) twitterFooter.get(controller);

		assertEquals(tfooter, footer.getChildren().get(footer.getChildren().size() - 1));
	}

	@Test
	public void testClearEmail() throws Exception {
		Method clearEmail = cl.getDeclaredMethod("clearEmail");

		Field emailReceiver = cl.getDeclaredField("emailReceiver");
		Field emailSubject = cl.getDeclaredField("emailSubject");
		Field emailMessage = cl.getDeclaredField("emailMessage");
		Field emailError = cl.getDeclaredField("emailError");

		emailReceiver.setAccessible(true);
		emailSubject.setAccessible(true);
		emailMessage.setAccessible(true);
		emailError.setAccessible(true);

		clearEmail.setAccessible(true);
		clearEmail.invoke(controller);
		waitForRunLater();

		JFXTextField receiver = (JFXTextField) emailReceiver.get(controller);
		JFXTextField subject = (JFXTextField) emailSubject.get(controller);
		JFXTextArea message = (JFXTextArea) emailMessage.get(controller);
		Label error = (Label) emailError.get(controller);

		assertEquals(true, receiver.getText().isEmpty());
		assertEquals(true, subject.getText().isEmpty());
		assertEquals(true, message.getText().isEmpty());
		assertEquals(true, error.getText().equals(""));
	}

	@Test
	public void testAddEmail() throws Exception {
		Method addEmail = cl.getDeclaredMethod("addEmail");

		Field emailList = cl.getDeclaredField("emailList");
		Field newEmail = cl.getDeclaredField("newEmail");

		emailList.setAccessible(true);
		newEmail.setAccessible(true);

		addEmail.setAccessible(true);
		addEmail.invoke(controller);

		@SuppressWarnings("unchecked")
		JFXListView<String> listView = (JFXListView<String>) emailList.get(controller);
		ObservableList<String> emails = listView.getItems();
		String email = ((TextField) newEmail.get(controller)).getText();

		assertEquals(email, emails.get(emails.size() - 1));
	}

	@Test
	public void testRemoveEmail() throws Exception {
		Method removeEmail = cl.getDeclaredMethod("removeEmail");

		Field emailList = cl.getDeclaredField("emailList");

		emailList.setAccessible(true);

		@SuppressWarnings("unchecked")
		JFXListView<String> listView = (JFXListView<String>) emailList.get(controller);

		emailList.setAccessible(true);

		int before = listView.getItems().size(), after;

		removeEmail.setAccessible(true);
		removeEmail.invoke(controller);
		waitForRunLater();

		after = listView.getItems().size();

		assertTrue(after <= before);
	}

	@Test
	public void testClosePost() throws Exception {
		Method closePost = cl.getDeclaredMethod("closePost");
		Field centerPane = cl.getDeclaredField("centerPane");
		Field postLayer = cl.getDeclaredField("postLayer");

		closePost.setAccessible(true);
		centerPane.setAccessible(true);
		postLayer.setAccessible(true);

		StackPane pane = (StackPane) centerPane.get(controller);
		StackPane post = (StackPane) postLayer.get(controller);

		closePost.invoke(controller);
		waitForRunLater();

		assertEquals(post, pane.getChildren().get(0));
	}

	@Test
	public void testSetUsername() throws Exception {
		Method setUsername = cl.getDeclaredMethod("setUsername", String.class);
		Field username = cl.getDeclaredField("username");

		setUsername.setAccessible(true);
		username.setAccessible(true);

		Label label = (Label) username.get(controller);

		setUsername.invoke(controller, "Username");
		waitForRunLater();

		assertEquals("Username", label.getText());
	}

	@Test
	public void testGetPosts() throws Exception {
		JFXListView<PostBox> posts = controller.getPosts();

		Field postList = cl.getDeclaredField("posts");
		postList.setAccessible(true);

		@SuppressWarnings("unchecked")
		JFXListView<PostBox> expected = (JFXListView<PostBox>) postList.get(controller);

		assertEquals(expected, posts);
	}

	public static void waitForRunLater() throws InterruptedException {
		Semaphore semaphore = new Semaphore(0);
		Platform.runLater(() -> semaphore.release());
		semaphore.acquire();
	}
}
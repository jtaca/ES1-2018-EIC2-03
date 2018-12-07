package jUnitTests;

import static org.junit.Assert.assertEquals;
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

import BDA.entry_objects.EmailEntry;
import BDA.entry_objects.InformationEntry;
import BDA.entry_objects.TwitterEntry;
import BDA.gui.MainController;
import BDA.gui.MainWindow;
import BDA.gui.PostBox;
import BDA.twitter.TwitterConnection;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * The Class MainControllerTest.
 */
public class MainControllerTest {

	/** The controller. */
	private MainController controller;
	
	/** The cl. */
	private Class<? extends MainController> cl;

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
	 * Setup.
	 */
	@Before
	public void setup() {
		controller = MainController.getInstance();
		cl = controller.getClass();
	}

	/**
	 * Test set theme.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testSetTheme() throws Exception {
		Method setTheme = cl.getDeclaredMethod("setTheme");
		setTheme.setAccessible(true);

		Field themeList = cl.getDeclaredField("themeList");
		Field mainBox = cl.getDeclaredField("mainBox");
		themeList.setAccessible(true);
		mainBox.setAccessible(true);

		@SuppressWarnings("unchecked")
		SingleSelectionModel<String> themes = ((ChoiceBox<String>) themeList.get(controller)).getSelectionModel();
		HBox main = (HBox) mainBox.get(controller);
		String style = main.getStylesheets().get(0).split("MainScene")[1];
		themes.select(0);

		waitForRunLater(setTheme);

		assertEquals(themes.getSelectedIndex() + ".css", style);
	}

	/**
	 * Test tweet counter color red.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testTweetCounterColorRed() throws Exception {
		Field tweetTextArea = cl.getDeclaredField("tweetTextArea");
		tweetTextArea.setAccessible(true);

		JFXTextArea tweetArea = (JFXTextArea) tweetTextArea.get(controller);
		String tweet = "";
		for (int i = 0; i < 280; i++)
			tweet += "a";

		tweetArea.setText(tweet);
	}

	/**
	 * Test tweet counter color black.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testTweetCounterColorBlack() throws Exception {
		Field tweetTextArea = cl.getDeclaredField("tweetTextArea");
		tweetTextArea.setAccessible(true);

		JFXTextArea tweetArea = (JFXTextArea) tweetTextArea.get(controller);
		String tweet = "";

		tweetArea.setText(tweet);
	}

	/**
	 * Test write email.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testWriteEmail() throws Exception {
		Method writeEmail = cl.getDeclaredMethod("writeEmail");
		writeEmail.setAccessible(true);

		Field centerPane = cl.getDeclaredField("centerPane");
		Field emailPane = cl.getDeclaredField("emailPane");
		centerPane.setAccessible(true);
		emailPane.setAccessible(true);

		StackPane center = (StackPane) centerPane.get(controller);
		VBox email = (VBox) emailPane.get(controller);

		waitForRunLater(writeEmail);

		assertEquals(email, center.getChildren().get(center.getChildren().size() - 1));
	}

	/**
	 * Test compose tweet.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testComposeTweet() throws Exception {
		Method composeTweet = cl.getDeclaredMethod("composeTweet");
		composeTweet.setAccessible(true);

		Field centerPane = cl.getDeclaredField("centerPane");
		Field tweetPane = cl.getDeclaredField("composeTweet");
		centerPane.setAccessible(true);
		tweetPane.setAccessible(true);

		StackPane center = (StackPane) centerPane.get(controller);
		StackPane tweet = (StackPane) tweetPane.get(controller);

		waitForRunLater(composeTweet);

		assertEquals(tweet, center.getChildren().get(center.getChildren().size() - 1));
	}

	/**
	 * Test show settings.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testShowSettings() throws Exception {
		Method showSettings = cl.getDeclaredMethod("showSettings");
		showSettings.setAccessible(true);

		Field centerPane = cl.getDeclaredField("centerPane");
		Field settings = cl.getDeclaredField("settings");
		centerPane.setAccessible(true);
		settings.setAccessible(true);

		StackPane center = (StackPane) centerPane.get(controller);
		ScrollPane set = (ScrollPane) settings.get(controller);

		waitForRunLater(showSettings);

		assertEquals(set, center.getChildren().get(center.getChildren().size() - 1));
	}

	/**
	 * Test search not empty.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testSearchNotEmpty() throws Exception {
		Method search = cl.getDeclaredMethod("search");
		search.setAccessible(true);

		Field searchBar = cl.getDeclaredField("searchBar");
		Field leaveSearch = cl.getDeclaredField("leaveSearch");
		searchBar.setAccessible(true);
		leaveSearch.setAccessible(true);

		JFXTextField s = (JFXTextField) searchBar.get(controller);
		Hyperlink ls = (Hyperlink) leaveSearch.get(controller);
		s.setText("");

		waitForRunLater(search);

		assertTrue(ls.isVisible());
	}

	/**
	 * Test search empty.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testSearchEmpty() throws Exception {
		Method search = cl.getDeclaredMethod("search");
		search.setAccessible(true);

		Field searchBar = cl.getDeclaredField("searchBar");
		searchBar.setAccessible(true);

		JFXTextField s = (JFXTextField) searchBar.get(controller);
		s.setText("");

		waitForRunLater(search);

		assertTrue(s.getText().isEmpty());
	}

	/**
	 * Test open post email entry.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testOpenPostEmailEntry() throws Exception {
		Method openPost = cl.getDeclaredMethod("openPost", InformationEntry.class);
		openPost.setAccessible(true);

		Field authorName = cl.getDeclaredField("authorName");
		Field authorUsername = cl.getDeclaredField("authorUsername");
		Field postText = cl.getDeclaredField("postText");
		authorName.setAccessible(true);
		authorUsername.setAccessible(true);
		postText.setAccessible(true);

		EmailEntry email = new EmailEntry("", new Date(), "Writer Name", "Subject", "Content");
		String[] names = email.getWriterName().split("<");
		String author = names[0].trim();
		String user = names.length > 1 ? names[1].substring(0, names[1].length() - 1) : names[0];
		String content = email.getContent().trim();
		Label name = (Label) authorName.get(controller);
		Label screenName = (Label) authorUsername.get(controller);
		Text postContent = (Text) postText.get(controller);

		waitForRunLater(openPost, email);

		assertEquals(author, name.getText());
		assertEquals(user, screenName.getText());
		assertEquals(content, postContent.getText());
	}

	/**
	 * Test open post twitter entry.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testOpenPostTwitterEntry() throws Exception {
		Method openPost = cl.getDeclaredMethod("openPost", InformationEntry.class);
		openPost.setAccessible(true);

		Field authorName = cl.getDeclaredField("authorName");
		Field authorUsername = cl.getDeclaredField("authorUsername");
		Field postText = cl.getDeclaredField("postText");
		authorName.setAccessible(true);
		authorUsername.setAccessible(true);
		postText.setAccessible(true);

		TwitterEntry tweet = new TwitterEntry(TwitterConnection.getInstance().getSomeRetweet());
		String author = tweet.getStatus().getRetweetedStatus().getUser().getName();
		String user = "@" + tweet.getStatus().getRetweetedStatus().getUser().getScreenName();
		String content = tweet.getStatus().getRetweetedStatus().getText().trim();
		Label name = (Label) authorName.get(controller);
		Label screenName = (Label) authorUsername.get(controller);
		Text postContent = (Text) postText.get(controller);

		waitForRunLater(openPost, tweet);

		assertEquals(author, name.getText());
		assertEquals(user, screenName.getText());
		assertEquals(content, postContent.getText());
	}

	/**
	 * Test open post twitter entry with image.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testOpenPostTwitterEntryWithImage() throws Exception {
		Method openPost = cl.getDeclaredMethod("openPost", InformationEntry.class);
		openPost.setAccessible(true);

		Field authorName = cl.getDeclaredField("authorName");
		Field authorUsername = cl.getDeclaredField("authorUsername");
		Field postText = cl.getDeclaredField("postText");
		authorName.setAccessible(true);
		authorUsername.setAccessible(true);
		postText.setAccessible(true);

		TwitterEntry tweet = new TwitterEntry(TwitterConnection.getInstance().getStatusById(1070789437901037568l));
		String author = tweet.getStatus().getUser().getName();
		String user = "@" + tweet.getStatus().getUser().getScreenName();
		String content = tweet.getStatus().getText().trim();
		Label name = (Label) authorName.get(controller);
		Label screenName = (Label) authorUsername.get(controller);
		Text postContent = (Text) postText.get(controller);

		waitForRunLater(openPost, tweet);

		assertEquals(author, name.getText());
		assertEquals(user, screenName.getText());
		assertEquals(content, postContent.getText());
	}

	/**
	 * Test open post twitter entry with video.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testOpenPostTwitterEntryWithVideo() throws Exception {
		Method openPost = cl.getDeclaredMethod("openPost", InformationEntry.class);
		openPost.setAccessible(true);

		Field authorName = cl.getDeclaredField("authorName");
		Field authorUsername = cl.getDeclaredField("authorUsername");
		Field postText = cl.getDeclaredField("postText");
		authorName.setAccessible(true);
		authorUsername.setAccessible(true);
		postText.setAccessible(true);

		TwitterEntry tweet = new TwitterEntry(TwitterConnection.getInstance().getStatusById(1070789789803139073l));
		String author = tweet.getStatus().getUser().getName();
		String user = "@" + tweet.getStatus().getUser().getScreenName();
		String content = tweet.getStatus().getText().trim();
		Label name = (Label) authorName.get(controller);
		Label screenName = (Label) authorUsername.get(controller);
		Text postContent = (Text) postText.get(controller);

		waitForRunLater(openPost, tweet);

		assertEquals(author, name.getText());
		assertEquals(user, screenName.getText());
		assertEquals(content, postContent.getText());
	}

	/**
	 * Test open post twitter entry with gif.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testOpenPostTwitterEntryWithGif() throws Exception {
		Method openPost = cl.getDeclaredMethod("openPost", InformationEntry.class);
		openPost.setAccessible(true);

		Field authorName = cl.getDeclaredField("authorName");
		Field authorUsername = cl.getDeclaredField("authorUsername");
		Field postText = cl.getDeclaredField("postText");
		authorName.setAccessible(true);
		authorUsername.setAccessible(true);
		postText.setAccessible(true);

		TwitterEntry tweet = new TwitterEntry(TwitterConnection.getInstance().getStatusById(1070797035593498624l));
		String author = tweet.getStatus().getUser().getName();
		String user = "@" + tweet.getStatus().getUser().getScreenName();
		String content = tweet.getStatus().getText().trim();
		Label name = (Label) authorName.get(controller);
		Label screenName = (Label) authorUsername.get(controller);
		Text postContent = (Text) postText.get(controller);

		waitForRunLater(openPost, tweet);

		assertEquals(author, name.getText());
		assertEquals(user, screenName.getText());
		assertEquals(content, postContent.getText());
	}

	/**
	 * Test clear email.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testClearEmail() throws Exception {
		Method clearEmail = cl.getDeclaredMethod("clearEmail");
		clearEmail.setAccessible(true);

		Field emailReceiver = cl.getDeclaredField("emailReceiver");
		Field emailSubject = cl.getDeclaredField("emailSubject");
		Field emailMessage = cl.getDeclaredField("emailMessage");
		Field emailError = cl.getDeclaredField("emailError");
		emailReceiver.setAccessible(true);
		emailSubject.setAccessible(true);
		emailMessage.setAccessible(true);
		emailError.setAccessible(true);

		waitForRunLater(clearEmail);

		JFXTextField receiver = (JFXTextField) emailReceiver.get(controller);
		JFXTextField subject = (JFXTextField) emailSubject.get(controller);
		JFXTextArea message = (JFXTextArea) emailMessage.get(controller);
		Label error = (Label) emailError.get(controller);

		assertTrue(receiver.getText().isEmpty());
		assertTrue(subject.getText().isEmpty());
		assertTrue(message.getText().isEmpty());
		assertTrue(error.getText().isEmpty());
	}

	/**
	 * Test add email.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testAddEmail() throws Exception {
		Method addEmail = cl.getDeclaredMethod("addEmail");
		addEmail.setAccessible(true);

		Field emailList = cl.getDeclaredField("emailList");
		Field newEmail = cl.getDeclaredField("newEmail");
		emailList.setAccessible(true);
		newEmail.setAccessible(true);

		waitForRunLater(addEmail);

		@SuppressWarnings("unchecked")
		JFXListView<String> listView = (JFXListView<String>) emailList.get(controller);
		ObservableList<String> emails = listView.getItems();
		String email = ((TextField) newEmail.get(controller)).getText();

		assertEquals(email, emails.get(emails.size() - 1));
	}

	/**
	 * Test remove email.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testRemoveEmail() throws Exception {
		Method removeEmail = cl.getDeclaredMethod("removeEmail");
		Method addEmail = cl.getDeclaredMethod("addEmail");
		removeEmail.setAccessible(true);
		addEmail.setAccessible(true);

		Field emailList = cl.getDeclaredField("emailList");
		emailList.setAccessible(true);

		waitForRunLater(addEmail);

		@SuppressWarnings("unchecked")
		JFXListView<String> listView = (JFXListView<String>) emailList.get(controller);
		listView.getSelectionModel().select(0);

		int before = listView.getItems().size(), after;

		waitForRunLater(removeEmail);

		after = listView.getItems().size();

		assertTrue(after < before);
	}

	/**
	 * Test close post.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testClosePost() throws Exception {
		Method closePost = cl.getDeclaredMethod("closePost");
		closePost.setAccessible(true);

		Field centerPane = cl.getDeclaredField("centerPane");
		Field postLayer = cl.getDeclaredField("postLayer");
		centerPane.setAccessible(true);
		postLayer.setAccessible(true);

		StackPane pane = (StackPane) centerPane.get(controller);
		StackPane post = (StackPane) postLayer.get(controller);

		waitForRunLater(closePost);

		assertEquals(post, pane.getChildren().get(0));
	}

	/**
	 * Test set username.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testSetUsername() throws Exception {
		Method setUsername = cl.getDeclaredMethod("setUsername", String.class);
		setUsername.setAccessible(true);

		Field username = cl.getDeclaredField("username");
		username.setAccessible(true);

		Label label = (Label) username.get(controller);

		waitForRunLater(setUsername, "Username");

		assertEquals("Username", label.getText());
	}

	/**
	 * Test get posts.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetPosts() throws Exception {
		Field postList = cl.getDeclaredField("posts");
		postList.setAccessible(true);

		JFXListView<PostBox> posts = controller.getPosts();

		@SuppressWarnings("unchecked")
		JFXListView<PostBox> expected = (JFXListView<PostBox>) postList.get(controller);

		assertEquals(expected, posts);
	}

	/**
	 * Wait for run later.
	 *
	 * @param method the method
	 * @param args the args
	 * @throws Exception the exception
	 */
	public void waitForRunLater(Method method, Object... args) throws Exception {
		Semaphore semaphore = new Semaphore(0);
		Platform.runLater(() -> {
			try {
				method.invoke(controller, args);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			semaphore.release();
		});
		semaphore.acquire();
	}
}
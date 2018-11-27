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

import entry_objects.EmailEntry;
import entry_objects.InformationEntry;
import entry_objects.TwitterEntry;
import gui.MainController;
import gui.MainWindow;
import gui.PostBox;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import twitter.TwitterFunctions;

public class MainControllerTest {

	private MainController controller;
	private Class<? extends MainController> cl;

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

	@Before
	public void setup() {
		controller = MainController.getInstance();
		cl = controller.getClass();
	}

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

	@Test
	public void testToPostBoxEmailEntry() throws Exception {
		Method toPostBox = cl.getDeclaredMethod("toPostBox", InformationEntry.class);
		toPostBox.setAccessible(true);

		EmailEntry email = new EmailEntry(new Date(), "Writer Name", "Subject", "Content");
		PostBox postBox = (PostBox) toPostBox.invoke(controller, email);

		assertEquals(email, postBox.getInformationEntry());
		assertEquals(email.getService(), postBox.getService());
	}

	@Test
	public void testToPostBoxTwitterEntry() throws Exception {
		Method toPostBox = cl.getDeclaredMethod("toPostBox", InformationEntry.class);
		toPostBox.setAccessible(true);

		TwitterEntry tweet = new TwitterEntry(TwitterFunctions.getSomeRetweet());
		PostBox postBox = (PostBox) toPostBox.invoke(controller, tweet);

		assertEquals(tweet, postBox.getInformationEntry());
		assertEquals(tweet.getService(), postBox.getService());
	}

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

		EmailEntry email = new EmailEntry(new Date(), "Writer Name", "Subject", "Content");
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

		TwitterEntry tweet = new TwitterEntry(TwitterFunctions.getSomeRetweet());
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

	@Test
	public void testGetPosts() throws Exception {
		Field postList = cl.getDeclaredField("posts");
		postList.setAccessible(true);

		JFXListView<PostBox> posts = controller.getPosts();

		@SuppressWarnings("unchecked")
		JFXListView<PostBox> expected = (JFXListView<PostBox>) postList.get(controller);

		assertEquals(expected, posts);
	}

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
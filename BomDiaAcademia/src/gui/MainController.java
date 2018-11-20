package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import email.EmailConnection;
import entry_objects.EmailEntry;
import entry_objects.InformationEntry;
import entry_objects.TwitterEntry;
import javafx.animation.FadeTransition;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import twitter.TwitterFunctions;
import twitter4j.MediaEntity;
import twitter4j.Status;

public class MainController implements Initializable {

	public HBox mainBox, postHeader, postBody, postFooter;
	public JFXListView<PostBox> posts;
	public VBox settings, mainPostBox, postContent, emailPane;
	public StackPane centerPane, postOverlay;
	public ImageView postProfilePic;
	public Label postAuthorName, postAuthorScreenName, username, emailError;
	public JFXButton closePost, prevPost, nextPost, comment, retweet, favourite;
	public JFXTextField emailReceiver, emailSubject;
	public JFXTextArea emailMessage;

	public ToggleGroup sideMenu, theme;
	private EmailConnection emailConnection;

	public MainController(EmailConnection emailConnection) {
		this.emailConnection = emailConnection;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showHomePage();
		centerPane.prefWidthProperty().bind(mainBox.widthProperty().subtract(250));
		mainPostBox.maxHeightProperty().bind(postContent.heightProperty());

		username.setText(emailConnection.getUsername().split("@")[0]);

		List<InformationEntry> entries = new ArrayList<>();

		try {
//			entries.addAll(TwitterFunctions.getTweetsForUsers(20, "iscteiul"));
			entries.addAll(TwitterFunctions.getTweets(20));
			entries.addAll(emailConnection.receiveMail());
		} catch (Exception e) {
			e.printStackTrace();
		}

		entries.sort(Comparator.comparing(InformationEntry::getDate).reversed());

		for (InformationEntry entry : entries)
			posts.getItems().add(loadPost(entry));
	}

	public void setTheme() {
		int cssIndex = theme.getToggles().indexOf(theme.getSelectedToggle());
		String css = getClass().getResource("/res/MainScene" + cssIndex + ".css").toExternalForm();
		mainBox.getStylesheets().clear();
		mainBox.getStylesheets().add(css);
	}

	public void showHomePage() {
		posts.toFront();
	}

	public void showSettings() {
		settings.toFront();
	}

	public void logOut() {

	}

	private PostBox loadPost(InformationEntry entry) {
		if (entry instanceof TwitterEntry) {
			Status s = ((TwitterEntry) entry).getStatus();
			return s.isRetweet() ? loadTweet(s.getRetweetedStatus(), s.getUser().getName()) : loadTweet(s, null);
		} else if (entry instanceof EmailEntry)
			return loadMail((EmailEntry) entry);
		else
			return null;
	}

	private PostBox loadTweet(Status status, String retweeter) {
		PostBox post = new PostBox(status);
		HBox tweetAuthor = new HBox();
		VBox tweetInfo = new VBox();

		FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TWITTER);
		icon.setSize("50");
		icon.setStyle("-fx-fill: #3cbffc");

		ImageView pic = new ImageView(new Image(status.getUser().get400x400ProfileImageURL(), 50, 50, true, true));

		Label name = new Label(status.getUser().getName());
		Label userName = new Label("@" + status.getUser().getScreenName());
		Label retweet = new Label(retweeter + " retweeted");
		Label date = new Label(status.getCreatedAt().toString());
		FontAwesomeIconView rt = new FontAwesomeIconView(FontAwesomeIcon.RETWEET);

		retweet.setStyle("-fx-text-fill: #878787");
		retweet.setPadding(new Insets(0, 10, 0, 5));
		userName.setStyle("-fx-font-weight: bold");
		userName.setPadding(new Insets(0, 0, 0, 10));
		date.setPadding(new Insets(0, 0, 0, 10));
		rt.setStyle("-fx-fill: #878787");

		Label tweet = new Label(status.getText());
		tweet.setWrapText(true);

		post.setOnMouseClicked(e -> showPost(status));

		if (retweeter != null)
			tweetAuthor.getChildren().addAll(rt, retweet);

		tweetAuthor.getChildren().addAll(name, userName);
		tweetAuthor.setAlignment(Pos.BASELINE_LEFT);
		tweetInfo.getChildren().addAll(tweetAuthor, tweet);
		post.getChildren().addAll(icon, pic, tweetInfo);
		post.setSpacing(10);
		post.prefWidthProperty().bind(posts.widthProperty().subtract(110));
		post.setAlignment(Pos.CENTER_LEFT);

		return post;
	}

	private PostBox loadMail(EmailEntry email) {
		PostBox post = new PostBox(null);
		HBox tweetAuthor = new HBox();
		VBox tweetInfo = new VBox();

		FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.ENVELOPE);
		icon.setSize("50");
		icon.setStyle("-fx-fill: #3cbffc");

		Label name = new Label(email.getWriterName()), subject = new Label(email.getSubject());

		subject.setWrapText(true);

		tweetAuthor.getChildren().addAll(name);
		tweetAuthor.setAlignment(Pos.BASELINE_LEFT);
		tweetInfo.getChildren().addAll(tweetAuthor, subject);
		post.getChildren().addAll(icon, tweetInfo);
		post.setSpacing(10);
		post.prefWidthProperty().bind(posts.widthProperty().subtract(110));
		post.setAlignment(Pos.CENTER_LEFT);

		return post;
	}

	private void showPost(Status status) {
		postContent.getChildren().clear();
		postContent.autosize();

		postProfilePic.setImage(new Image(status.getUser().get400x400ProfileImageURL(), 50, 50, true, true));

		postAuthorName.setText(status.getUser().getName());
		postAuthorScreenName.setText("@" + status.getUser().getScreenName());

		Text text = new Text(status.getText());
		text.setWrappingWidth(450);

		postContent.getChildren().add(text);

		for (MediaEntity m : status.getMediaEntities())
			postContent.getChildren().add(new ImageView(new Image(m.getMediaURLHttps(), 450, 0, true, true)));

		postOverlay.toFront();
	}

	public void writeEmail() {
		emailPane.toFront();
	}

	public void sendEmail() {
		if (!emailReceiver.getText().isEmpty() && !emailSubject.getText().isEmpty()
				&& !emailMessage.getText().isEmpty())
			emailConnection.sendEmail(emailReceiver.getText(), emailSubject.getText(), emailMessage.getText());
		else {
			FadeTransition errorFade = new FadeTransition(Duration.seconds(1), emailError);
			emailError.setText("Preencha todos os campos");
			
			errorFade.setFromValue(0);
			errorFade.setToValue(1);
			
			errorFade.play();
		}
	}

	public void clearEmail() {
		emailReceiver.clear();
		emailSubject.clear();
		emailMessage.clear();
		emailError.setText("");
	}

	public void nextPost() {
		posts.getSelectionModel().select(posts.getSelectionModel().getSelectedIndex() + 1);
		PostBox post = posts.getSelectionModel().getSelectedItem();
		showPost(post.getStatus());
	}

	public void prevPost() {
		if (posts.getSelectionModel().getSelectedIndex() - 1 >= 0) {
			posts.getSelectionModel().select(posts.getSelectionModel().getSelectedIndex() - 1);
			PostBox post = posts.getSelectionModel().getSelectedItem();
			showPost(post.getStatus());
		}
	}

	public void closePost() {
		postOverlay.toBack();
	}
}
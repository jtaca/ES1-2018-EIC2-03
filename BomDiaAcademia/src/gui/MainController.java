package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import twitter.TwitterFunctions;
import twitter4j.MediaEntity;
import twitter4j.Status;

public class MainController implements Initializable {

	public HBox mainBox, postHeader, postBody, postFooter;
	public JFXListView<PostBox> posts;
	public VBox settings, mainPostBox, postContent;
	public StackPane centerPane, postOverlay;
	public ImageView postProfilePic;
	public Label postAuthorName, postAuthorScreenName;
	public JFXButton closePost, prevPost, nextPost, comment, retweet, favourite;

	public ToggleGroup sideMenu, theme;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showHomePage();
		centerPane.prefWidthProperty().bind(mainBox.widthProperty().subtract(250));
		mainPostBox.maxHeightProperty().bind(postContent.heightProperty());

		List<Status> tweets = TwitterFunctions.getTweetsForUsers(20, "iscteiul");

		if (tweets != null)
			for (Status s : tweets)
				if (s.isRetweet())
					posts.getItems().add(loadPost(s.getRetweetedStatus(), s.getUser().getName()));
				else
					posts.getItems().add(loadPost(s, null));
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

	private PostBox loadPost(Status status, String retweeter) {
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
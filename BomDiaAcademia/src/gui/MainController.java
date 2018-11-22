package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import other.Service;
import threads.ThreadPool;
import twitter4j.Status;

public class MainController implements Initializable {

	private static final MainController INSTANCE = new MainController();

	// Main window
	public HBox mainBox;
	public StackPane centerPane;

	// Side bar
	public ToggleGroup sideMenu;
	public Label username;

	// Posts list
	public JFXListView<PostBox> posts;

	// Open post
	public StackPane postLayer;
	public VBox postContainer, postContent, postAuthorInfo;
	public Label authorName, authorUsername, retweetLabel;
	public ImageView profilePic;
	public HBox emailFooter, twitterFooter;

	// Settings
	public VBox settings;
	public JFXListView<String> emailList;
	public ChoiceBox<String> themeList;
	public TextField newEmail;

	// Email writing
	public VBox emailPane;
	public JFXTextField emailReceiver, emailSubject;
	public Label emailError;
	public JFXTextArea emailMessage;

	private EmailConnection emailConnection;

	private MainController() {
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showHomePage();

		themeList.getItems().add("Laranja");
		themeList.getItems().add("Azul");
		themeList.getItems().add("Azul Invertido");
		themeList.setValue("Laranja");
		themeList.getSelectionModel().selectedItemProperty().addListener(e -> setTheme());

		centerPane.prefWidthProperty().bind(mainBox.widthProperty().subtract(250));
		postContainer.maxHeightProperty().bind(postContent.heightProperty());
	}

	public void loadPosts(List<InformationEntry> entries, boolean reload) {
		Platform.runLater(() -> {
			if (reload)
				posts.getItems().clear();

			for (InformationEntry entry : entries)
				posts.getItems().add(toPostBox(entry));
		});
	}

	public void setTheme() {
		int cssIndex = themeList.getSelectionModel().getSelectedIndex();
		String css = getClass().getResource("/res/MainScene" + cssIndex + ".css").toExternalForm();
		mainBox.getStylesheets().clear();
		mainBox.getStylesheets().add(css);
	}

	public void showHomePage() {
		posts.toFront();
	}

	public void writeEmail() {
		emailPane.toFront();
	}

	public void showSettings() {
		settings.toFront();
	}

	public void logOut(ActionEvent event) {
		ThreadPool.getInstance().stopThreads();
		((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
	}

	private PostBox toPostBox(InformationEntry informationEntry) {
		PostBox postBox = new PostBox(informationEntry);
		FontAwesomeIconView icon = new FontAwesomeIconView();
		VBox entryInfo = new VBox();
		HBox authorInfo = new HBox(), retweetInfo = new HBox();
		Label authorName = new Label(), authorUsername = new Label(), postInfo = new Label(), date = new Label();
		Region region = new Region();

		postInfo.setWrapText(true);
		date.setText(informationEntry.getDate().toString());
		HBox.setHgrow(region, Priority.ALWAYS);
		HBox.setHgrow(entryInfo, Priority.ALWAYS);

		authorUsername.setPadding(new Insets(0, 10, 0, 10));

		authorInfo.getChildren().addAll(authorName, authorUsername, region, date);
		authorInfo.setAlignment(Pos.BASELINE_LEFT);

		entryInfo.getChildren().addAll(authorInfo, postInfo);

		postBox.getChildren().addAll(icon, entryInfo);

		postBox.setSpacing(10);
		postBox.prefWidthProperty().bind(posts.widthProperty().subtract(110));
		postBox.setAlignment(Pos.CENTER_LEFT);

		postBox.setOnMouseClicked(e -> openPost(informationEntry));

		if (informationEntry.getService().equals(Service.EMAIL)) {
			EmailEntry email = (EmailEntry) informationEntry;

			String names[] = email.getWriterName().split("<");
			icon.setIcon(FontAwesomeIcon.ENVELOPE);
			icon.setSize("50");
			icon.setStyle("-fx-fill: #3cbffc");

			authorName.setText(names[0].trim());
			authorUsername.setText(names.length > 1 ? names[1].substring(0, names[1].length() - 1) : names[0]);

			authorUsername.setStyle("-fx-font-weight: bold");

			postInfo.setText(email.getSubject());
		} else if (informationEntry.getService().equals(Service.TWITTER)) {
			TwitterEntry tweet = (TwitterEntry) informationEntry;

			icon.setIcon(FontAwesomeIcon.TWITTER);
			icon.setSize("50");
			icon.setStyle("-fx-fill: #3cbffc");

			Status status = !tweet.isRetweet() ? tweet.getStatus() : tweet.getStatus().getRetweetedStatus();
			ImageView pic = new ImageView(new Image(status.getUser().get400x400ProfileImageURL(), 50, 50, true, true));

			authorName.setText(status.getUser().getName());
			authorUsername.setText("@" + status.getUser().getScreenName());

			authorUsername.setStyle("-fx-font-weight: bold");

			if (tweet.isRetweet()) {
				FontAwesomeIconView retweetIcon = new FontAwesomeIconView(FontAwesomeIcon.RETWEET);
				Label retweeter = new Label(tweet.getStatus().getUser().getName() + " retweeted");

				retweetIcon.setStyle("-fx-fill: #878787");

				retweeter.setStyle("-fx-text-fill: #878787");
				retweeter.setPadding(new Insets(0, 10, 0, 5));

				retweetInfo.getChildren().addAll(retweetIcon, retweeter);

				entryInfo.getChildren().add(0, retweetInfo);
			}

			postInfo.setText(status.getText());

			postBox.getChildren().add(1, pic);
		}

		return postBox;
	}

	private void openPost(InformationEntry informationEntry) {
		postContent.getChildren().clear();
		postContent.autosize();
		retweetLabel.setVisible(false);
		retweetLabel.setMaxHeight(0);

		if (informationEntry.getService().equals(Service.EMAIL)) {
			EmailEntry email = (EmailEntry) informationEntry;

			String names[] = email.getWriterName().split("<");
			profilePic.setFitWidth(0);
			profilePic.setFitHeight(0);
			profilePic.setImage(null);

			HBox.setMargin(profilePic, new Insets(0, 0, 0, 0));

			authorName.setText(names[0].trim());
			authorUsername.setText(names.length > 1 ? names[1].substring(0, names[1].length() - 1) : names[0]);

			Text body = new Text(email.getContent());
			body.setWrappingWidth(470);

			postContent.getChildren().add(body);

			emailFooter.toFront();
		} else if (informationEntry.getService().equals(Service.TWITTER)) {
			TwitterEntry tweet = (TwitterEntry) informationEntry;

			Status status = !tweet.isRetweet() ? tweet.getStatus() : tweet.getStatus().getRetweetedStatus();
			Image pic = new Image(status.getUser().get400x400ProfileImageURL(), 50, 50, true, true);

			profilePic.setFitWidth(50);
			profilePic.setFitHeight(50);
			profilePic.setImage(pic);

			HBox.setMargin(profilePic, new Insets(0, 10, 0, 0));

			authorName.setText(status.getUser().getName());
			authorUsername.setText("@" + status.getUser().getScreenName());

			if (tweet.isRetweet()) {
				retweetLabel.setText(tweet.getStatus().getUser().getName() + " retweeted");
				retweetLabel.setVisible(true);
			}

//			for (MediaEntity m : status.getMediaEntities())
//				postContent.getChildren().add(new ImageView(new Image(m.getMediaURLHttps(), 450, 0, true, true)));

			Text body = new Text(status.getText());
			body.setWrappingWidth(470);

			postContent.getChildren().add(body);

			twitterFooter.toFront();
		}

		postLayer.toFront();
	}

	public void openPreviousPost() {
		if (posts.getSelectionModel().getSelectedIndex() - 1 >= 0) {
			posts.getSelectionModel().select(posts.getSelectionModel().getSelectedIndex() - 1);
			openPost(posts.getSelectionModel().getSelectedItem().getInformationEntry());
		}
	}

	public void openNextPost() {
		posts.getSelectionModel().select(posts.getSelectionModel().getSelectedIndex() + 1);
		openPost(posts.getSelectionModel().getSelectedItem().getInformationEntry());
	}

	public void closePost() {
		postLayer.toBack();
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

	public void addEmail() {
		emailList.getItems().add(newEmail.getText());
		newEmail.setText("");
	}

	public void removeEmail() {
		emailList.getItems().remove(emailList.getSelectionModel().getSelectedIndex());
	}

	public static MainController getInstance() {
		return INSTANCE;
	}
}
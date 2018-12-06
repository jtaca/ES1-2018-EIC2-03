package gui;

import java.util.Date;

import com.jfoenix.controls.JFXSpinner;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entry_objects.EmailEntry;
import entry_objects.FacebookEntry;
import entry_objects.InformationEntry;
import entry_objects.TwitterEntry;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import other.Service;

/**
 * The Class PostBox gives a visual representation of an InformationEntry in the
 * posts list.
 * 
 * @author Rostislav Andreev
 * @version 2.0
 */
public class PostBox extends HBox {

	/** The information entry. */
	private InformationEntry informationEntry;

	/** The service. */
	private Service service;

	private String postAuthor;

	private String emailReceiver;

	private Date date;

	// ------------ Structure elements ------------
	private FontAwesomeIconView icon = new FontAwesomeIconView();
	private VBox entryInfo = new VBox();
	private HBox authorInfo = new HBox();
	private HBox retweetInfo = new HBox();
	private Label authorName = new Label();
	private Label authorUsername = new Label();
	private Label postInfo = new Label();
	private Label dateLabel = new Label();
	private Region region = new Region();

	/**
	 * Instantiates a new post box.
	 *
	 * @param informationEntry the information entry
	 */
	public PostBox(InformationEntry informationEntry) {
		super();
		if (informationEntry != null) {
			this.informationEntry = informationEntry;
			service = informationEntry.getService();
			date = informationEntry.getDate();

			icon.setSize("50");
			icon.setStyle("-fx-fill: #3cbffc");

			postInfo.setWrapText(true);
			dateLabel.setText(date.toString());
			HBox.setHgrow(region, Priority.ALWAYS);
			HBox.setHgrow(entryInfo, Priority.ALWAYS);

			authorUsername.setPadding(new Insets(0, 10, 0, 10));
			authorUsername.setStyle("-fx-font-weight: bold");

			authorInfo.getChildren().addAll(authorName, authorUsername, region, dateLabel);
			authorInfo.setAlignment(Pos.BASELINE_LEFT);

			entryInfo.getChildren().addAll(authorInfo, postInfo);

			getChildren().addAll(icon, entryInfo);

			setSpacing(10);
			setAlignment(Pos.CENTER_LEFT);

			if (informationEntry.getService().equals(Service.EMAIL))
				loadEmailEntry((EmailEntry) informationEntry);
			else if (informationEntry.getService().equals(Service.FACEBOOK))
				loadFacebookEntry((FacebookEntry) informationEntry);
			else if (informationEntry.getService().equals(Service.TWITTER))
				loadTwitterEntry((TwitterEntry) informationEntry);
		} else {
			entryInfo.getChildren().addAll(new JFXSpinner(), new Label("A carregar conteúdo..."));

			setAlignment(Pos.CENTER);

			entryInfo.setAlignment(Pos.CENTER);
			entryInfo.setSpacing(20);

			getChildren().add(entryInfo);
		}
	}

	private void loadEmailEntry(EmailEntry email) {
		String names[] = email.getWriterName().split("<");
		icon.setIcon(FontAwesomeIcon.ENVELOPE);

		authorName.setText(names[0].trim());
		authorUsername.setText(names.length > 1 ? names[1].substring(0, names[1].length() - 1) : names[0]);

		postAuthor = email.getWriterName();
		emailReceiver = email.getReceiverEmail();

		postInfo.setText(email.getSubject());
	}

	private void loadFacebookEntry(FacebookEntry facebook) {
		icon.setIcon(FontAwesomeIcon.FACEBOOK_OFFICIAL);

		ImageView pic = new ImageView(new Image(facebook.getProfileImageUrl(), 50, 50, true, true));

		authorName.setText(facebook.getAuthor());

		postInfo.setText(facebook.getAttachmentTitle());

		getChildren().add(1, pic);
	}

	private void loadTwitterEntry(TwitterEntry tweet) {
		icon.setIcon(FontAwesomeIcon.TWITTER);

		ImageView pic = new ImageView(new Image(tweet.getProfilePictureURL(), 50, 50, true, true));

		authorName.setText(tweet.getName());
		authorUsername.setText("@" + tweet.getUsername());

		postAuthor = tweet.getUsername();

		if (tweet.isRetweet()) {
			FontAwesomeIconView retweetIcon = new FontAwesomeIconView(FontAwesomeIcon.RETWEET);
			Label retweeter = new Label(tweet.getRetweeter() + " retweeted");

			retweetIcon.setStyle("-fx-fill: #878787");

			retweeter.setStyle("-fx-text-fill: #878787");
			retweeter.setPadding(new Insets(0, 10, 0, 5));

			retweetInfo.getChildren().addAll(retweetIcon, retweeter);

			entryInfo.getChildren().add(0, retweetInfo);
		}

		postInfo.setText(tweet.getContent());

		getChildren().add(1, pic);
	}

	public Date getDate() {
		return date;
	}

	public String getPostAuthor() {
		return postAuthor;
	}

	public String getEmailReceiver() {
		return emailReceiver;
	}

	/**
	 * Gets the information entry.
	 *
	 * @return the information entry
	 */
	public InformationEntry getInformationEntry() {
		return informationEntry;
	}

	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	public Service getService() {
		return service;
	}
}
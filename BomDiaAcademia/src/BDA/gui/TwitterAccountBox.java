package BDA.gui;

import BDA.twitter.TwitterConnection;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import twitter4j.TwitterException;

/**
 * The Class TwitterAccountBox.
 */
public class TwitterAccountBox extends HBox {

	/** The username. */
	private String username;
	
	/** The check box. */
	private CheckBox checkBox;

	/**
	 * Instantiates a new twitter account box.
	 *
	 * @param username the username
	 * @param selected the selected
	 */
	public TwitterAccountBox(String username, boolean selected) {
		super();
		this.username = username;

		Label label = new Label("@" + username);
		Region region = new Region();
		checkBox = new CheckBox();
		ImageView pic = null;
		try {
			pic = new ImageView(new Image(TwitterConnection.getInstance().getUserPicture(username), 20, 0, true, true));
		} catch (TwitterException e) {
//			e.printStackTrace();
			pic = new ImageView();
		}

		setAlignment(Pos.CENTER);
		setSpacing(10);
		label.setStyle("-fx-font-weight: bold");
		checkBox.setSelected(selected);

		HBox.setHgrow(region, Priority.ALWAYS);

		getChildren().addAll(pic, label, region, checkBox);
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Checks if is selected.
	 *
	 * @return true, if is selected
	 */
	public boolean isSelected() {
		return checkBox.isSelected();
	}
}
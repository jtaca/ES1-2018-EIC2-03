package gui;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import twitter.TwitterFunctions;
import twitter4j.TwitterException;

public class TwitterAccountBox extends HBox {

	private String username;
	private CheckBox checkBox;

	public TwitterAccountBox(String username, boolean selected) {
		super();
		this.username = username;

		Label label = new Label("@" + username);
		Region region = new Region();
		checkBox = new CheckBox();
		ImageView pic = null;
		try {
			pic = new ImageView(new Image(TwitterFunctions.getInstance().getUserPicture(username), 20, 0, true, true));
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		setAlignment(Pos.CENTER);
		setSpacing(10);
		label.setStyle("-fx-font-weight: bold");
		checkBox.setSelected(selected);

		HBox.setHgrow(region, Priority.ALWAYS);

		getChildren().addAll(pic, label, region, checkBox);
	}

	public String getUsername() {
		return username;
	}

	public boolean isSelected() {
		return checkBox.isSelected();
	}
}
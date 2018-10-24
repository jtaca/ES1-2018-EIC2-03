package gui;

import javafx.scene.layout.HBox;
import twitter4j.Status;

public class PostBox extends HBox {

	private Status status;

	public PostBox(Status status) {
		super();
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}
}
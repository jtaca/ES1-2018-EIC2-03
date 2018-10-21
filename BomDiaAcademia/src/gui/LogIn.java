package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LogIn extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/res/LogIn.fxml"));
		Image icon = new Image(getClass().getResource("/res/logo0.png").toString());

		primaryStage.getIcons().add(icon);
		primaryStage.setTitle("Bom Dia Academia");
		primaryStage.setMinHeight(490);
		primaryStage.setMinWidth(370);

		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		root.requestFocus();
	}
}
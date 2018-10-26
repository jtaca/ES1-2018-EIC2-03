package gui;

import java.util.List;

import files.ReadAndWriteXMLFile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import other.XMLUserConfiguration;

public class LogIn extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		List<XMLUserConfiguration> userConfiguration = ReadAndWriteXMLFile.ReadConfigXMLFile();
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new LogInController(userConfiguration.isEmpty() ? null : userConfiguration.get(0)));
		loader.setLocation(getClass().getResource("/res/LogIn.fxml"));
		Parent root = loader.load();
//		Parent root = FXMLLoader.load(getClass().getResource("/res/LogIn.fxml"));
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
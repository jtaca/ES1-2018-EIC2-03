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
import threads.ThreadPool;

public class Login extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ThreadPool.getInstance().startThreads();

		List<XMLUserConfiguration> userConfiguration = ReadAndWriteXMLFile.ReadConfigXMLFile();
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new LoginController(userConfiguration.isEmpty() ? null : userConfiguration.get(0)));
		loader.setLocation(getClass().getResource("/res/LogIn.fxml"));
		Parent root = loader.load();
		Image icon = new Image(getClass().getResource("/res/logo0.png").toString());

		primaryStage.getIcons().add(icon);
		primaryStage.setTitle("Bom Dia Academia");
		primaryStage.setMinHeight(490);
		primaryStage.setMinWidth(370);
		primaryStage.setOnCloseRequest(e -> ThreadPool.getInstance().stopThreads());

		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		root.requestFocus();
	}
}
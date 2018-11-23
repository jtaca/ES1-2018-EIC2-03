package gui;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tasks.ServiceReadTask;
import tasks.TwitterPostReaderTask;
import threads.ThreadPool;

public class MainWindow extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		List<ServiceReadTask> tasks = new ArrayList<ServiceReadTask>();
		tasks.add(new TwitterPostReaderTask());
		ThreadPool.refreshGUIWithThreads(tasks);

		FXMLLoader loader = new FXMLLoader();
		loader.setController(MainController.getInstance());
		loader.setLocation(getClass().getResource("/res/MainScene.fxml"));
		Parent root = loader.load();
		Image icon = new Image(getClass().getResource("/res/logo0.png").toString());

		primaryStage.getIcons().add(icon);
		primaryStage.setTitle("Bom Dia Academia");
		primaryStage.setMinHeight(540);
		primaryStage.setMinWidth(820);
		primaryStage.setOnCloseRequest(e -> ThreadPool.getInstance().stopThreads());

		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		root.requestFocus();
	}
}
package gui;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tasks.FacebookPostReaderTask;
import tasks.ServiceReadTask;
import tasks.TwitterPostReaderTask;
import threads.ThreadPool;

/**
 * The Class MainWindow.
 * 
 * @author Rostislav Andreev
 * @version 2.0
 */
public class MainWindow extends Application {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		List<ServiceReadTask> tasks = new ArrayList<ServiceReadTask>();
		tasks.add(new TwitterPostReaderTask());
		tasks.add(new FacebookPostReaderTask());
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
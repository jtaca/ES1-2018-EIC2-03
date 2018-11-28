package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import email.EmailConnection;
import facebook.FacebookConnection;
import files.ReadAndWriteXMLFile;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import other.OtherStaticFunction;
import other.Service;
import other.XMLUserConfiguration;
import threads.ThreadPool;
import twitter.TwitterFunctions;

/**
 * The Class LoginController handles the user interaction with the GUI.
 * @author Rostislav Andreev
 * @version 2.0
 */
public class LoginController implements Initializable {

	/** The error message. */
	@FXML
	private Label errorMessage;

	/** The username. */
	@FXML
	private JFXTextField username;

	/** The password. */
	@FXML
	private JFXPasswordField password;

	/** The remember me. */
	@FXML
	private JFXCheckBox rememberMe;

	/** The user. */
	private XMLUserConfiguration user;

	/**
	 * Instantiates a new login controller.
	 *
	 * @param user the user
	 */
	public LoginController(XMLUserConfiguration user) {
		this.user = user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (user != null && user.isInformationSaved()) {
			username.setText(user.getUsername());
			password.setText(user.getPassword());
		}
	}

	/**
	 * Unfocuses any element that may have been set to focused by the user.
	 *
	 * @param e the e
	 */
	@FXML
	private void unfocus(MouseEvent e) {
		((Node) e.getSource()).getScene().getRoot().requestFocus();
	}

	/**
	 * Checks the credentials given by the user and if they are correct then it
	 * opens the main application window.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	private void logIn(ActionEvent event) throws IOException {
		FadeTransition errorFade = new FadeTransition(Duration.seconds(1), errorMessage);
		String oldMessage = errorMessage.getText();

		errorFade.setFromValue(0);
		errorFade.setToValue(1);

		if (username.getText().isEmpty() || password.getText().isEmpty())
			errorMessage.setText("Preencha todos os campos");
		else {
			if (!username.getText().matches(".+(@iscte-iul.pt)"))
				errorMessage.setText("Email não válido");
			else {
				if (EmailConnection.verifyLogin(username.getText(), password.getText())) {

					EmailConnection outlook = null;
					XMLUserConfiguration twitter = null;
					XMLUserConfiguration facebook = null;
					List<XMLUserConfiguration> user_config_list = new ArrayList<XMLUserConfiguration>();

					try {
						twitter = ReadAndWriteXMLFile.ReadConfigXMLFile().get(1);
					} catch (Exception e) {
						System.out.println("Ficheiro sem informacao twitter");
					}
					
					try {
						facebook = ReadAndWriteXMLFile.ReadConfigXMLFile().get(2);
					} catch (Exception e) {
						System.out.println("Ficheiro sem informacao facebook");
					}

					try {
						if (user == null || (user != null && user.isInformationSaved() == false))
							user = new XMLUserConfiguration(rememberMe.isSelected(), Service.EMAIL, username.getText(),
									password.getText());

//						if (rememberMe.isSelected()) {}
						if (twitter == null)
							twitter = new XMLUserConfiguration(rememberMe.isSelected(), Service.TWITTER,
									TwitterFunctions.getKeys()[0], TwitterFunctions.getKeys()[1],
									TwitterFunctions.getKeys()[2], TwitterFunctions.getKeys()[3]);
						
						if (facebook == null)
							facebook = new XMLUserConfiguration(rememberMe.isSelected(), Service.FACEBOOK, FacebookConnection.getAccessToken2());

//					if (rememberMe.isSelected()) {
						user_config_list.add(user);
						user_config_list.add(twitter);
						user_config_list.add(facebook);
						ReadAndWriteXMLFile.CreateConfigXMLFile(user_config_list);
//					}

						// twitter = ReadAndWriteXMLFile.ReadConfigXMLFile().get(1);

//					outlook = new EmailConnection(user.getUsername(), user.getPassword());

						OtherStaticFunction.refreshGUIWithThreads();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

					Stage stage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					loader.setController(MainController.getInstance());
					loader.setLocation(getClass().getResource("/res/MainScene.fxml"));
					Parent root = loader.load();
					Image icon = new Image(getClass().getResource("/res/logo0.png").toString());
					MainController.getInstance().setUsername(username.getText().split("@")[0]);

					stage.getIcons().add(icon);
					stage.setTitle("Bom Dia Academia");
					stage.setMinHeight(540);
					stage.setMinWidth(820);
					stage.setOnCloseRequest(e -> ThreadPool.getInstance().stopThreads());

					stage.setScene(new Scene(root));
					((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
					stage.show();
					root.requestFocus();
				} else
					errorMessage.setText("A palavra-passe introduzida é incorreta");
			}
		}

		if (!errorMessage.getText().equals(oldMessage))
			errorFade.play();
	}
}
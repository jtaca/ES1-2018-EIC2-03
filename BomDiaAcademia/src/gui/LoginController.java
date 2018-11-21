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
import files.ReadAndWriteXMLFile;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
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
import other.Service;
import other.XMLUserConfiguration;
import twitter.TwitterFunctions;

public class LoginController implements Initializable {

	public Label errorMessage;
	public JFXTextField username;
	public JFXPasswordField password;
	public JFXCheckBox rememberMe;

	private XMLUserConfiguration user;

	public LoginController(XMLUserConfiguration user) {
		this.user = user;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (user != null && user.isInformationSaved()) {
			username.setText(user.getUsername());
			password.setText(user.getPassword());
		}
	}

	public void unfocus(MouseEvent e) {
		((Node) e.getSource()).getScene().getRoot().requestFocus();
	}

	public void logIn(ActionEvent event) throws IOException {
		FadeTransition errorFade = new FadeTransition(Duration.seconds(1), errorMessage);
		String oldMessage = errorMessage.getText();

		errorFade.setFromValue(0);
		errorFade.setToValue(1);

		if (username.getText().isEmpty() || password.getText().isEmpty())
			errorMessage.setText("Preencha todos os campos");
		else {
			if (EmailConnection.verifyLogin(username.getText(), password.getText())) {

				EmailConnection outlook = null;
				XMLUserConfiguration twitter = null;
				List<XMLUserConfiguration> user_config_list = new ArrayList<XMLUserConfiguration>();

				try {
					twitter = ReadAndWriteXMLFile.ReadConfigXMLFile().get(1);
				} catch (Exception e) {
					System.out.println("Ficheiro sem informacao");
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

					if (rememberMe.isSelected()) {
						user_config_list.add(user);
						user_config_list.add(twitter);
						ReadAndWriteXMLFile.CreateConfigXMLFile(user_config_list);
					}

					// twitter = ReadAndWriteXMLFile.ReadConfigXMLFile().get(1);
					outlook = new EmailConnection(user.getUsername(), user.getPassword());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				loader.setController(new MainController(outlook));
				loader.setLocation(getClass().getResource("/res/MainScene.fxml"));
				Parent root = loader.load();
				Image icon = new Image(getClass().getResource("/res/logo0.png").toString());

				stage.getIcons().add(icon);
				stage.setTitle("Bom Dia Academia");
				stage.setMinHeight(540);
				stage.setMinWidth(820);

				stage.setScene(new Scene(root));
				((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
				stage.show();
				root.requestFocus();
			} else
				errorMessage.setText("A palavra-passe introduzida é incorreta");
		}

		if (!errorMessage.getText().equals(oldMessage))
			errorFade.play();
	}
}
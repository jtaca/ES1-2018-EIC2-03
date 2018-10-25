package gui;

import java.io.IOException;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LogInController {

	public Label errorMessage;
	public JFXTextField username;
	public JFXPasswordField password;
	public JFXCheckBox rememberMe;

	public void unfocus(MouseEvent e) {
		((Node) e.getSource()).getScene().getRoot().requestFocus();
	}

	public void logIn(ActionEvent e) throws IOException {
		FadeTransition errorFade = new FadeTransition(Duration.seconds(1), errorMessage);
		String oldMessage = errorMessage.getText();

		errorFade.setFromValue(0);
		errorFade.setToValue(1);

		if (username.getText().isEmpty() || password.getText().isEmpty())
			errorMessage.setText("Preencha todos os campos");
		else {
			if (false) {
				;
				/*
				 * Verificação do nome de utilizador e palavra-passe
				 */
			} else {
//				errorMessage.setText("A palavra-passe introduzida é incorreta");
				Stage stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/res/MainScene.fxml"));
				Image icon = new Image(getClass().getResource("/res/logo2.png").toString());

				stage.getIcons().add(icon);
				stage.setTitle("Bom Dia Academia");
				stage.setMinHeight(540);
				stage.setMinWidth(820);

				stage.setScene(new Scene(root));
				((Stage) ((Node) e.getSource()).getScene().getWindow()).close();
				stage.show();
				root.requestFocus();
			}
		}

		if (!errorMessage.getText().equals(oldMessage))
			errorFade.play();
	}
}

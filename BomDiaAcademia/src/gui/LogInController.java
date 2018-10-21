package gui;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class LogInController {

	public Label errorMessage;
	public JFXTextField username;
	public JFXPasswordField password;
	public JFXCheckBox rememberMe;

	public void unfocus(MouseEvent e) {
		((Node) e.getSource()).getScene().getRoot().requestFocus();
	}

	public void logIn(ActionEvent e) {
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
			} else
				errorMessage.setText("A palavra-passe introduzida é incorreta");
		}

		if (!errorMessage.getText().equals(oldMessage))
			errorFade.play();
	}
}

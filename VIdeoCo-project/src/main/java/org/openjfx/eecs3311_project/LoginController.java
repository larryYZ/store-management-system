package org.openjfx.eecs3311_project;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {

	@FXML
	private Button loginButton;
	@FXML
	private Button registerButton;
	@FXML
	private TextField usernameText;
	@FXML
	private PasswordField passwordText;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		login();
		
		registerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				PageUtils.changePage(event, "register.fxml", "Register", null);
				
			}
			
		});
		
	}
	
	public void login() {
		loginButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!usernameText.getText().isEmpty() &&
						!passwordText.getText().isEmpty()) {
					PageUtils.loginUser(event, usernameText.getText(), passwordText.getText());
				} else {
					Alert userAlert = new Alert(Alert.AlertType.ERROR);
					userAlert.setContentText("Please fill in all required information to login.");
					userAlert.show();
				}
			}
			
		});
	}
	
}

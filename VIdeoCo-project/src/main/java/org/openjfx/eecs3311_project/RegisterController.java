package org.openjfx.eecs3311_project;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegisterController implements Initializable {
	
	@FXML
	private Button registerButton;
	@FXML
	private Button loginButton;
	@FXML
	private TextField usernameText;
	@FXML
	private TextField emailText;
	@FXML
	private TextField passwordText;
	@FXML
	private TextField nameText;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		register();
		
		loginButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				PageUtils.changePage(event, "login.fxml", "Login", null);
				
			}
			
		});
	}
	
	public void register() {
		registerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (!usernameText.getText().isEmpty() &&
						!emailText.getText().isEmpty() &&
						!passwordText.getText().isEmpty() &&
						!nameText.getText().isEmpty()) {
					PageUtils.registerUser(event, null, usernameText.getText(), emailText.getText(), passwordText.getText(), Constant.CUSTOMER_PERMISSION, nameText.getText());
				} else {
					Alert userAlert = new Alert(Alert.AlertType.ERROR);
					userAlert.setContentText("Please fill in all required information to register.");
					userAlert.show();
				}
			}
			
		});
	}

    
}
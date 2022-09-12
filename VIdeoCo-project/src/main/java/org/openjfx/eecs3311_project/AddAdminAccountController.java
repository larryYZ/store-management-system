package org.openjfx.eecs3311_project;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddAdminAccountController implements Initializable {
	
	@FXML
	private Button logoutButton;
	@FXML
	private Button backToHomeButton;
	@FXML
	private Button cartButton;
	@FXML
	private Button ordersButton;
	@FXML
	private Button accountButton;
	@FXML
	private Button saveButton;
	@FXML
	private Label welcomeLabel;
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
		logoutButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				 PageUtils.changePage(event, "login.fxml", "Login", null);
			}
		});
		
	}
	
	public void setUser(User user) {
		welcomeLabel.setText("Welcome " + user.getName() + "!");
		
		backToHomeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				 PageUtils.changePage(event, "index.fxml", "Index", user);
			}
		});
		
		cartButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				PageUtils.changeToCartPage(event, user);
			}
		});
		
		ordersButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				PageUtils.changeToOrdersPage(event, user);
			}
		});
		
		accountButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				PageUtils.changeToAccountPage(event, user);
			}
		});
		
		addAdminAccount(user);
		
	}
	
	public void addAdminAccount(User user) {
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!usernameText.getText().isEmpty() &&
						!emailText.getText().isEmpty() &&
						!passwordText.getText().isEmpty() &&
						!nameText.getText().isEmpty()) {
					PageUtils.registerUser(event, user, usernameText.getText(), emailText.getText(), passwordText.getText(), Constant.ADMIN_PERMISSION, nameText.getText());
				} else {
					Alert userAlert = new Alert(Alert.AlertType.ERROR);
					userAlert.setContentText("Please fill in all required information.");
					userAlert.show();
				}
			}
			
		});
	}
}

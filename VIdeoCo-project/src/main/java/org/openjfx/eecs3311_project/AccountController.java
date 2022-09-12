package org.openjfx.eecs3311_project;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AccountController implements Initializable {
	
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
	private Label usernameLabel;
	@FXML
	private TextField nameText;
	@FXML
	private TextField emailText;
	@FXML
	private TextField passwordText;
	@FXML
	private Label loyaltyPointsLabel;
	@FXML
	private Label lateFeeLabel;
	@FXML
	private Label saveMessageLabel;
	
	OrderDatabase orderDatabase = new OrderDatabase();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logoutButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				 PageUtils.changePage(event, "login.fxml", "Login", null);
			}
		});
		orderDatabase.load();
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
		
		displayAccountDetails(user);
		updateAccount(user);
	}
	
	public void displayAccountDetails(User user) {
		usernameLabel.setText(user.getName());
		nameText.setText(user.getRealName());
		emailText.setText(user.getEmail());
		passwordText.setText(user.getPassword());
		loyaltyPointsLabel.setText(String.valueOf(user.getLoyaltyPoints()));
		
		ArrayList<Order> userOrders = orderDatabase.orders.get(user.getName());
		double lateFee = 0L;
		for (Order order : userOrders) {
			if (order.getReturned() == 0) {
				int movieNum = order.getMovieIds().split(",").length;
				long orderTime = order.getTime();
				long timeDiffInDays = (System.currentTimeMillis() - orderTime) / (1000 * 60 * 60 * 24);
				long lateDays = timeDiffInDays - 14;
				
				if (lateDays > 0) {
					lateFee = lateFee + 1.0 * lateDays * movieNum;
					
					if (!"ontario".equals(order.getProvince().toLowerCase()) && !"ON".equals(order.getProvince().toUpperCase())) {
						lateFee += 9.99;
					}
				}
			}
			
		}
		lateFeeLabel.setText(String.valueOf(lateFee));
	}
	
	public void updateAccount(User user) {
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				if (!nameText.getText().isEmpty() &&
						!emailText.getText().isEmpty() &&
						!passwordText.getText().isEmpty()) {
					user.setRealName(nameText.getText());
					user.setEmail(emailText.getText());
					user.setPassword(passwordText.getText());
					UserDatabase userDatabase = new UserDatabase();
					userDatabase.load();
					userDatabase.updateUser(user);
					saveMessageLabel.setText("Changes have been saved");
				} else {
					Alert userAlert = new Alert(Alert.AlertType.ERROR);
					userAlert.setContentText("Please fill in all required information.");
					userAlert.show();
				}
				
			}
		});
	}
	
}

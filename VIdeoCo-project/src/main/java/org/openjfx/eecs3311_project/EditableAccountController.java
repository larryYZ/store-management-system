package org.openjfx.eecs3311_project;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class EditableAccountController implements Initializable {
	
	@FXML
	private Button logoutButton;
	@FXML
	private Button backToHomeButton;
	@FXML
	private Button cartButton;
	@FXML
	private Button ordersButton;
	@FXML
	private Button deleteAccountButton;
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
	private TextField loyaltyPointsText;
	@FXML
	private Label saveMessageLabel;
	
	private OrderDatabase orderDatabase;
	
	@FXML
	private ListView<Order> ordersListView;
	
	ObservableList<Order> ordersObservablelist = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logoutButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				 PageUtils.changePage(event, "login.fxml", "Login", null);
			}
		});
		orderDatabase = new OrderDatabase();
		orderDatabase.load();
		
	}
	
	public void setUser(User user, User selectedUser) {
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
		
		displayAccountInfo(selectedUser);
		
		updateAccount(selectedUser);
		
		displayOrders(user, selectedUser);
		
		deleteAccount(user, selectedUser);
		
	}
	
	public void displayAccountInfo(User selectedUser) {
		usernameLabel.setText(selectedUser.getName());
		nameText.setText(selectedUser.getRealName());
		emailText.setText(selectedUser.getEmail());
		passwordText.setText(selectedUser.getPassword());
		loyaltyPointsText.setText(String.valueOf(selectedUser.getLoyaltyPoints()));
	}
	
	public void displayOrders(User user, User selectedUser) {
		ArrayList<Order> orders = new ArrayList<>();
		Map<String, ArrayList<Order>> allOrders = orderDatabase.orders;
		if (allOrders.containsKey(selectedUser.getName())) {
			orders = allOrders.get(selectedUser.getName());
		}
		ordersObservablelist.addAll(orders);
		ordersListView.setItems(ordersObservablelist);
		
		ordersListView.setCellFactory(param -> new ListCell<Order>() {
            @Override
            protected void updateItem(Order order, boolean empty) {
                super.updateItem(order, empty);

                if (empty || order == null) {
                    setText(null);
                } else {
                  String orderStatus = "";
                  long orderTime = order.getTime();
                  long timeDiffInMinutes = (System.currentTimeMillis() - orderTime) / (1000 * 60 * 60);
                  if (timeDiffInMinutes < 1) {
                  	orderStatus = "Your order has been processed";
                  } else if (timeDiffInMinutes < 5) {
                  	orderStatus = "Your order has been shipped";
                  } else {
                  	orderStatus = "Your order has been delivered";
                  }
                    setText("Order #" + String.valueOf(order.getOrderNumber()) + "      " + orderStatus);
                    setFont(new Font("Arial", 14));
                    setStyle("-fx-text-fill: #5b5b5b;");
                }
            }
        });
		
		ordersListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	            Order selectedOrder = ordersListView.getSelectionModel().getSelectedItem();
	            if (selectedOrder != null) {
	            	PageUtils.changeToOrderDetailsPage(event, user, selectedUser, selectedOrder);
	            }
	        }
	    });
	}
	
	public void updateAccount(User selectedUser) {
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				if (!nameText.getText().isEmpty() &&
						!emailText.getText().isEmpty() &&
						!passwordText.getText().isEmpty()) {
					selectedUser.setRealName(nameText.getText());
					selectedUser.setEmail(emailText.getText());
					selectedUser.setPassword(passwordText.getText());
					selectedUser.setLoyaltyPoints(Integer.valueOf(loyaltyPointsText.getText()));
					UserDatabase userDatabase = new UserDatabase();
					userDatabase.load();
					userDatabase.updateUser(selectedUser);
					saveMessageLabel.setText("Changes have been saved");
				} else {
					Alert userAlert = new Alert(Alert.AlertType.ERROR);
					userAlert.setContentText("Please fill in all required information.");
					userAlert.show();
				}
				
			}
		});
	}
	
	public void deleteAccount(User user, User selectedUser) {
		deleteAccountButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				UserDatabase userDatabase = new UserDatabase();
				userDatabase.load();
				userDatabase.removeUser(selectedUser);
				
				OrderDatabase orderDatabase = new OrderDatabase();
				orderDatabase.load();
				orderDatabase.removeUserOrders(selectedUser.getName());
				
				TentitiveOrderDatabase tentitiveOrderDatabase = new TentitiveOrderDatabase();
				tentitiveOrderDatabase.load();
				tentitiveOrderDatabase.removeOrder(selectedUser.getName());
				PageUtils.changeToAllCustomerAccountPage(event, user);
			}
		});
	}
	
}

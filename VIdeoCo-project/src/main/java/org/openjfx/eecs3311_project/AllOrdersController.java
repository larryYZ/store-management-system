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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class AllOrdersController implements Initializable {
	
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
	private Label welcomeLabel;
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
		
		displayAllOrders(user);
		
	}
	
	public void displayAllOrders(User user) {
		OrderDatabase orderDatabase = new OrderDatabase();
		orderDatabase.load();
		Map<String, ArrayList<Order>> orders = orderDatabase.orders;
		for (ArrayList<Order> o : orders.values()) {
			ordersObservablelist.addAll(o);
		}
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
                  	orderStatus = " - order has been processed";
                  } else if (timeDiffInMinutes < 5) {
                  	orderStatus = " - order has been shipped";
                  } else {
                  	orderStatus = " - order has been delivered";
                  }
                    setText("Order #" + String.valueOf(order.getOrderNumber()) + "      " + order.getUsername() + orderStatus);
                    setFont(new Font("Arial", 14));
                    setStyle("-fx-text-fill: #5b5b5b;");
                }
            }
        });
		
		ordersListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	            Order selectedOrder = ordersListView.getSelectionModel().getSelectedItem();
	            String selectedUsername = selectedOrder.getUsername();
	            UserDatabase userDatabase = new UserDatabase();
	            userDatabase.load();
	            User selectedUser = userDatabase.users.get(selectedUsername);
	            if (selectedOrder != null) {
	            	PageUtils.changeToOrderDetailsPage(event, user, selectedUser, selectedOrder);
	            }
	        }
	    });
	}
	
}

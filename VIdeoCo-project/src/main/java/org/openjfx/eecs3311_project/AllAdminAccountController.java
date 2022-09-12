package org.openjfx.eecs3311_project;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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

public class AllAdminAccountController implements Initializable {
	
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
	private Button addAdminAccountButton;
	@FXML
	private Label welcomeLabel;
	@FXML
	private ListView<User> adminAccountsListView;
	
	ObservableList<User> adminAccountsObservablelist = FXCollections.observableArrayList();

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
		
		displayAllAdminAccounts(user);
		
		if (!Constant.SUPER_ADMIN_PERMISSION.equals(user.getPermission())) {
			addAdminAccountButton.setVisible(false);
		}
		
		addAdminAccountButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				PageUtils.changeToAddAdminAccountPage(event, user);
			}
		});
		
	}
	
	public void displayAllAdminAccounts(User user) {
		UserDatabase userDatabase = new UserDatabase();
		userDatabase.load();
		Map<String, User> allUsers = userDatabase.users;

		List<User> allAdmins = allUsers.values().stream().filter(u -> Constant.ADMIN_PERMISSION.equals(u.getPermission())).collect(Collectors.toList());;
		adminAccountsObservablelist.addAll(allAdmins);
		adminAccountsListView.setItems(adminAccountsObservablelist);
		
		adminAccountsListView.setCellFactory(param -> new ListCell<User>() {
            @Override
            protected void updateItem(User u, boolean empty) {
                super.updateItem(u, empty);

                if (empty || u == null) {
                    setText(null);
                } else {
                    setText(u.getName());
                    setFont(new Font("Arial", 14));
                    setStyle("-fx-text-fill: #5b5b5b;");
                }
            }
        });
		
		adminAccountsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	            User selectedUser = adminAccountsListView.getSelectionModel().getSelectedItem();
	            if (selectedUser != null) {
	            	PageUtils.changeToEditableAccountPage(event, user, selectedUser);
	            }
	        }
	    });
	}
	
}

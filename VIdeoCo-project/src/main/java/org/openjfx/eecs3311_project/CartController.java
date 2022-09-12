package org.openjfx.eecs3311_project;

import java.net.URL;
import java.util.HashSet;
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
import javafx.scene.text.Font;

public class CartController implements Initializable {
	
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
	private Button checkoutButton;
	@FXML
	private Button removeButton;
	@FXML
	private Label welcomeLabel;
	@FXML
	private Label priceLabel;
	@FXML
	private ListView<Long> cartListView;
	
	ObservableList<Long> cartObservablelist = FXCollections.observableArrayList();
	private TentitiveOrderDatabase tentitiveOrderDatabase;
	private MovieDatabase movieDatabase;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logoutButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				 PageUtils.changePage(event, "login.fxml", "Login", null);
			}
		});
		tentitiveOrderDatabase = new TentitiveOrderDatabase();
		tentitiveOrderDatabase.load();
		movieDatabase = new MovieDatabase();
		movieDatabase.load();
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
		
		accountButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				PageUtils.changeToAccountPage(event, user);
			}
		});
		
		displayMoviesInCart(user);
		
		removeMoviesFromCart(user);
		
		if (cartObservablelist.isEmpty()) {
			checkoutButton.setVisible(false);
		}
		
		checkoutButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				 PageUtils.changeToCheckoutPage(event, user, cartObservablelist);
			}
		});
		
		ordersButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				PageUtils.changeToOrdersPage(event, user);
			}
		});
		
	}
	
	public void displayMoviesInCart(User user) {
		HashSet<Long> movieIds = new HashSet<>();
		
		Map<String, HashSet<Long>> tentitiveOrders = tentitiveOrderDatabase.tentitiveOrders;
		if (tentitiveOrders.containsKey(user.getName())) {
			movieIds = tentitiveOrders.get(user.getName());
		}

		cartObservablelist.addAll(movieIds);
		cartListView.setItems(cartObservablelist);
		
		cartListView.setCellFactory(param -> new ListCell<Long>() {
            @Override
            protected void updateItem(Long movieId, boolean empty) {
                super.updateItem(movieId, empty);

                if (empty || movieId == null) {
                    setText(null);
                } else {
                    setText(movieDatabase.movies.get(movieId).getTitle());
                    setFont(new Font("Arial", 14));
                    setStyle("-fx-text-fill: #5b5b5b;");
                }
            }
        });
		
		priceLabel.setText("$" + cartObservablelist.size() * 9.99);
	}
	
	public void removeMoviesFromCart(User user) {
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int selectedIndex = cartListView.getSelectionModel().getSelectedIndex();
				if (selectedIndex > -1) {
					Long removedMovieId = cartObservablelist.get(selectedIndex);
					
					cartObservablelist.remove(selectedIndex);
					priceLabel.setText("$" + cartObservablelist.size() * 9.99);
					tentitiveOrderDatabase.removeMovie(user.getName(), removedMovieId);
					
					if (cartObservablelist.isEmpty()) {
						checkoutButton.setVisible(false);
					}
				}
			}
		});
	}

}

package org.openjfx.eecs3311_project;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class OrderDetailsController implements Initializable {
	
	@FXML
	private Button logoutButton;
	@FXML
	private Button backToHomeButton;
	@FXML
	private Button cancelOrderButton;
	@FXML
	private Button updateOrderButton;
	@FXML
	private Button cartButton;
	@FXML
	private Button ordersButton;
	@FXML
	private Button accountButton;
	@FXML
	private Label welcomeLabel;
	@FXML
	private TextArea orderNumberText;
	@FXML
	private TextArea orderStatusText;
	@FXML
	private TextArea orderDateText;
	@FXML
	private TextArea moviesText;
	@FXML
	private TextArea addressText;
	@FXML
	private TextArea cityText;
	@FXML
	private TextArea provinceText;
	@FXML
	private TextArea postalCodeText;
	@FXML
	private TextArea countryText;
	@FXML
	private TextArea paymentText;
	@FXML
	private TextArea paymentDetailText;
	@FXML
	private TextArea returnStatusText;
	
	private MovieDatabase movieDatabase;
	private OrderDatabase orderDatabase;
	private UserDatabase userDatabase;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logoutButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				 PageUtils.changePage(event, "login.fxml", "Login", null);
			}
		});
		
		movieDatabase = new MovieDatabase();
		movieDatabase.load();
		orderDatabase = new OrderDatabase();
        orderDatabase.load();
        userDatabase = new UserDatabase();
        userDatabase.load();
		
	}
	
	public void setUserAndOrder(User user, User orderUser, Order order) {
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
		
		displayOrderDetails(user, order);
		
		cancelOrder(user, orderUser, order);
		
		updateOrder(user, orderUser, order);
	}
	
	public void displayOrderDetails(User user, Order order) {
		if (Constant.CUSTOMER_PERMISSION.equals(user.getPermission())) {
			orderNumberText.setEditable(false);
			orderStatusText.setEditable(false);
			orderDateText.setEditable(false);
			moviesText.setEditable(false);
			addressText.setEditable(false);
			cityText.setEditable(false);
			provinceText.setEditable(false);
			postalCodeText.setEditable(false);
			countryText.setEditable(false);
			paymentText.setEditable(false);
			paymentDetailText.setEditable(false);
			returnStatusText.setEditable(false);
			updateOrderButton.setVisible(false);
		}
		
		String orderStatus = "";
        long orderTime = order.getTime();
        long timeDiffInHours = (System.currentTimeMillis() - orderTime) / (1000 * 60 * 60);
        if (timeDiffInHours < 1) {
        	orderStatus = "proccessed";
        } else if (timeDiffInHours < 5) {
        	orderStatus = "shipped";
        } else {
        	orderStatus = "delivered";
        }
        
		orderStatusText.setText(orderStatus);
		
		if (!"proccessed".equals(orderStatus)) {
			cancelOrderButton.setVisible(false);
		}
		
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
		
		orderNumberText.setText(String.valueOf(order.getOrderNumber()));
		orderDateText.setText(formater.format(new Date(order.getTime())));
		
		String[] movieIds = order.getMovieIds().split(",");
		StringBuffer bf = new StringBuffer();
		if (movieIds.length > 0) {
			bf.append(movieDatabase.movies.get(Long.valueOf(movieIds[0])).getTitle());
		}
		for (int i = 1; i < movieIds.length; i++) {
			bf.append(", " + movieDatabase.movies.get(Long.valueOf(movieIds[i])).getTitle());
		}
		
		moviesText.setText(bf.toString());
		addressText.setText(order.getAddress());
		cityText.setText(order.getCity());
		provinceText.setText(order.getProvince());
		postalCodeText.setText(order.getPostalCode());
		countryText.setText(order.getCountry());
		paymentText.setText(Constant.CREDIT_CARD.equals(order.getPayment()) ? "Credit Card" : "Loyalty Points");
		paymentDetailText.setText(order.getPaymentDetail());
		returnStatusText.setText(order.getReturned() == 0 ? "Not Returned" : "Returned");
	}
	
	public void cancelOrder(User user, User orderUser, Order order) {
		cancelOrderButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
	            orderDatabase.removeOrder(order);
	            
	            String movieIds = order.getMovieIds();
	            String[] movieIdsArray = movieIds.split(",");
	            List<Long> movieIdsList = new ArrayList<>();
	            for (int i = 0; i < movieIdsArray.length; i++) {
	            	movieIdsList.add(Long.valueOf(movieIdsArray[i]));
	            }
	            
	            if (Constant.LOYALTY_POINTS.equals(order.getPayment())) {
	            	orderUser.increaseLoyaltyPoints(movieIdsList.size() * 10);
	            }
	            orderUser.decreaseLoyaltyPoints(1);
	            userDatabase.updateUser(orderUser);
	            
	            MovieDatabase movieDatabase = new MovieDatabase();
	            movieDatabase.load();
	            movieDatabase.increaseMovieCount(movieIdsList);
	            
				PageUtils.changeToCancelConfirmationPage(event, user, order.getOrderNumber());
			}
		});
	}
	
	public void updateOrder(User user, User orderUser, Order order) {
		updateOrderButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Order newOrder = new Order();
				newOrder.setUsername(orderUser.getName());
				newOrder.setOrderNumber(Long.valueOf(orderNumberText.getText()));
				newOrder.setTime(order.getTime());
				
				String[] moviesArray = moviesText.getText().split(",");
				List<Long> newMovieIds = movieDatabase.getMovieIds(Arrays.asList(moviesArray));
				StringBuffer bf = new StringBuffer();
				for (Long id: newMovieIds) {
					bf.append(id + ",");
				}
				String newMovieIdsString = bf.toString();
				newOrder.setMovies(newMovieIdsString.substring(0, newMovieIdsString.length()-1));
				newOrder.setAddress(addressText.getText());
				newOrder.setCity(cityText.getText());
				newOrder.setProvince(provinceText.getText());
				newOrder.setPostalCode(postalCodeText.getText());
				newOrder.setCountry(countryText.getText());
				newOrder.setPayment(paymentText.getText());
				newOrder.setPaymentDetail(paymentDetailText.getText());
				newOrder.setReturned(returnStatusText.getText().toLowerCase().equals("returned") ? 1 : 0);
				newOrder.setWarehouse(order.getWarehouse());
				
	            orderDatabase.updateOrder(order, newOrder);
	            
	            // old order
	            String movieIds = order.getMovieIds();
	            String[] movieIdsArray = movieIds.split(",");
	            List<Long> movieIdsList = new ArrayList<>();
	            for (int i = 0; i < movieIdsArray.length; i++) {
	            	movieIdsList.add(Long.valueOf(movieIdsArray[i]));
	            }
	            
	            if (Constant.LOYALTY_POINTS.equals(order.getPayment())) {
	            	orderUser.increaseLoyaltyPoints(movieIdsList.size() * 10);
	            }
	            orderUser.decreaseLoyaltyPoints(1);
	            userDatabase.updateUser(orderUser);
	            
	            movieDatabase.increaseMovieCount(movieIdsList);
	            
	            // new order
	            if (Constant.LOYALTY_POINTS.equals(newOrder.getPayment())) {
	            	orderUser.decreaseLoyaltyPoints(newMovieIds.size() * 10);
	            }
	            orderUser.increaseLoyaltyPoints(1);
	            userDatabase.updateUser(orderUser);
	            
	            movieDatabase.decreaseMovieCount(newMovieIds);
	            
	            PageUtils.changeToAllCustomerAccountPage(event, user);
	            
			}
		});
	}
	

}

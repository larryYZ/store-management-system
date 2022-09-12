package org.openjfx.eecs3311_project;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class CheckoutController implements Initializable {
	
	private static final String CREDIT_CARD = "Credit Card";
	private Pattern pattern = Pattern.compile("[0-9]{16}");
	
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
	private Button placeOrderButton;
	@FXML
	private Label welcomeLabel;
	@FXML
	private RadioButton creditCardRadio;
	@FXML
	private RadioButton loyaltyPointsRadio;
	@FXML
	private HBox paymentHBox1;
	@FXML
	private HBox paymentHBox2;
	@FXML
	private HBox paymentHBox3;
	@FXML
	private TextField addressText;
	@FXML
	private TextField cityText;
	@FXML
	private TextField provinceText;
	@FXML
	private TextField postalCodeText;
	@FXML
	private TextField countryText;
	@FXML
	private TextField cardNumText;
	@FXML
	private TextField cardNameText;
	@FXML
	private TextField expiryDateText;
	@FXML
	private TextField cvcText;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logoutButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				 PageUtils.changePage(event, "login.fxml", "Login", null);
			}
		});
		
	}
	
	public void setUserAndMovies(User user, ObservableList<Long> movieIds) {
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
		
		placeOrder(user, movieIds);
	}
	
	public void placeOrder(User user, ObservableList<Long> movieIds) {
		ToggleGroup toggleGroup = new ToggleGroup();
		creditCardRadio.setToggleGroup(toggleGroup);
		loyaltyPointsRadio.setToggleGroup(toggleGroup);
		creditCardRadio.setSelected(true);
		
		buildCreditCardUI();
		
		toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
		    		Toggle oldToggle, Toggle newToggle) {
	            if (toggleGroup.getSelectedToggle() != null) {
	            	RadioButton selectedRadio = (RadioButton) toggleGroup.getSelectedToggle();
	            	paymentHBox1.getChildren().clear();
            		paymentHBox2.getChildren().clear();
            		paymentHBox3.getChildren().clear();
            		
	            	if (CREDIT_CARD.equals(selectedRadio.getText())) {
	            		buildCreditCardUI();
	            	} else {
	            		buildLoyaltyPointsUI(user.getLoyaltyPoints(), movieIds.size());
	            	}
	            }
		    }
		});
		
		placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!isShippingInfoValid()) {
					return;
				}
				RadioButton selectedRadio = (RadioButton) toggleGroup.getSelectedToggle();
				if (CREDIT_CARD.equals(selectedRadio.getText())) {
					if (!isCreditCardInfoValid()) {
						return;
					}
				}
				
				// clear cart - remove user's entry in TentitiveOrderDatabase
				TentitiveOrderDatabase tentitiveOrderDatabase = new TentitiveOrderDatabase();
				tentitiveOrderDatabase.load();
				tentitiveOrderDatabase.removeOrder(user.getName());
				
				// update inventory in MovieDtabase
				MovieDatabase movieDatabase = new MovieDatabase();
				movieDatabase.load();
				movieDatabase.decreaseMovieCount(movieIds);
				
				// add new entry in OrderDatabase (username (map key), orderNumber, timestamp, returned (0/1), address, city, province, postalCode, country, payment, paymentDetail (cardNum or points string))
				long orderNumber = CommonUtils.generateNewOrderNumber();
				long time = System.currentTimeMillis();
				StringBuffer orderMovies = new StringBuffer(String.valueOf(movieIds.get(0)));
				for (int i = 1; i < movieIds.size(); i++) {
					orderMovies.append("," + movieIds.get(i));
				}
				
				String warehouse = findClosestWarehouse(postalCodeText.getText());
				
				Order newOrder = new Order(user.getName(), orderNumber, time, 0, addressText.getText(), cityText.getText(), provinceText.getText(), postalCodeText.getText(), countryText.getText(), "", "", orderMovies.toString(), warehouse);
				if (CREDIT_CARD.equals(selectedRadio.getText())) {
            		newOrder.setPayment(Constant.CREDIT_CARD);
            		newOrder.setPaymentDetail(cardNumText.getText());
            	} else {
            		int points = movieIds.size() * 10;
            		newOrder.setPayment(Constant.LOYALTY_POINTS);
            		newOrder.setPaymentDetail(String.valueOf(points));
            		// decrease loyalty points if pay by loyalty points
            		user.decreaseLoyaltyPoints(movieIds.size() * 10);
            	}
				OrderDatabase.addNewOrder(newOrder);
				
				// add 1 loyalty point to user in UserDatabase
				user.increaseLoyaltyPoints();
				UserDatabase userDatabase = new UserDatabase();
				userDatabase.load();
				userDatabase.updateUser(user);
				
				PageUtils.changeToConfirmationPage(event, user, orderNumber);
				
			}
		});
	}
	
	private void buildCreditCardUI() {
		Label cardName = new Label("Name on Card");
		cardName.setPrefWidth(200);
		cardName.setFont(new Font("Arial Rounded MT Bold", 14));
		cardName.setStyle("-fx-text-fill: #88888b;");
		cardName.setPadding(new Insets(0, 0, 0, 30));
		cardNameText = new TextField();
		paymentHBox1.getChildren().addAll(cardName, cardNameText);
		
		Label cardNum = new Label("Card Number");
		cardNum.setPrefWidth(200);
		cardNum.setFont(new Font("Arial Rounded MT Bold", 14));
		cardNum.setStyle("-fx-text-fill: #88888b;");
		cardNum.setPadding(new Insets(0, 0, 0, 30));
		cardNumText = new TextField();
		Label expiryDate = new Label("MM/YY");
		expiryDate.setPadding(new Insets(0, 0, 0, 30));
		expiryDate.setPrefWidth(100);
		expiryDate.setFont(new Font("Arial Rounded MT Bold", 14));
		expiryDate.setStyle("-fx-text-fill: #88888b;");
		expiryDateText = new TextField();
		expiryDateText.setPrefWidth(70);
		Label cvc = new Label("CVC");
		cvc.setPadding(new Insets(0, 0, 0, 30));
		cvc.setPrefWidth(100);
		cvc.setFont(new Font("Arial Rounded MT Bold", 14));
		cvc.setStyle("-fx-text-fill: #88888b;");
		cvcText = new TextField();
		cvcText.setPrefWidth(70);
		paymentHBox2.getChildren().addAll(cardNum, cardNumText, expiryDate, expiryDateText, cvc, cvcText);
		
		placeOrderButton.setVisible(true);
	}
	
	private void buildLoyaltyPointsUI(int userLoyaltyPoints, int movieNum) {
		Label loyaltyPointsLabel = new Label("Total loyalty points: " + userLoyaltyPoints);
		loyaltyPointsLabel.setFont(new Font("Arial Rounded MT Bold", 14));
		loyaltyPointsLabel.setStyle("-fx-text-fill: #88888b;");
		loyaltyPointsLabel.setPadding(new Insets(0, 0, 0, 30));
		paymentHBox1.getChildren().add(loyaltyPointsLabel);
		
		int requiredPoints = movieNum * 10;
		Label loyaltyPointsNeededLabel = new Label("Loyalty points needed for this order: " + requiredPoints);
		loyaltyPointsNeededLabel.setFont(new Font("Arial Rounded MT Bold", 14));
		loyaltyPointsNeededLabel.setStyle("-fx-text-fill: #88888b;");
		loyaltyPointsNeededLabel.setPadding(new Insets(0, 0, 0, 30));
		paymentHBox2.getChildren().add(loyaltyPointsNeededLabel);
		
		if (userLoyaltyPoints < requiredPoints) {
			Label insufficientAmountLabel = new Label("You have insufficient amount of points, please pay by credit card.");
			insufficientAmountLabel.setFont(new Font("Arial Rounded MT Bold", 14));
			insufficientAmountLabel.setStyle("-fx-text-fill: #88888b;");
			insufficientAmountLabel.setPadding(new Insets(0, 0, 0, 30));
			paymentHBox3.getChildren().add(insufficientAmountLabel);
			
			placeOrderButton.setVisible(false);
		}
		
	}
	
	private boolean isValidCardNum(String str) {
		if (str == null) {
			return false;
		}
		return pattern.matcher(str).matches();
	}
	
	private boolean checkCreditCardValid() {
		return true;
	}
	
	private boolean isShippingInfoValid() {
		if (!addressText.getText().isEmpty() &&
				!cityText.getText().isEmpty() &&
				!provinceText.getText().isEmpty() &&
				!postalCodeText.getText().isEmpty() &&
				!countryText.getText().isEmpty()) {
			if (!"canada".equals(countryText.getText().toLowerCase())) {
				Alert userAlert = new Alert(Alert.AlertType.ERROR);
				userAlert.setContentText("Sorry, we can only ship to Canada.");
				userAlert.show();
				return false;
			}					
		} else {
			Alert userAlert = new Alert(Alert.AlertType.ERROR);
			userAlert.setContentText("Please fill in shipping information.");
			userAlert.show();
			return false;
		}
		return true;
	}
	
	private boolean isCreditCardInfoValid() {
		if (!cardNumText.getText().isEmpty() &&
				!cardNameText.getText().isEmpty() &&
				!expiryDateText.getText().isEmpty() &&
				!cvcText.getText().isEmpty()) {
			if (!isValidCardNum(cardNumText.getText())) {
				Alert userAlert = new Alert(Alert.AlertType.ERROR);
				userAlert.setContentText("Please enter a valid credit card number.");
				userAlert.show();
				return false;
			}
			if (!checkCreditCardValid()) {
				Alert userAlert = new Alert(Alert.AlertType.ERROR);
				userAlert.setContentText("We cannot process your payment information. Please use a valid credit card.");
				userAlert.show();
				return false;
			}
		} else {
			Alert userAlert = new Alert(Alert.AlertType.ERROR);
			userAlert.setContentText("Please fill in credit card information.");
			userAlert.show();
			return false;
		}
		return true;
	}
	
	private String findClosestWarehouse(String postalCode) {
		Random ran = new Random();
		int num = ran.nextInt(3) + 1;
		
		return "WH" + num;
	}

}

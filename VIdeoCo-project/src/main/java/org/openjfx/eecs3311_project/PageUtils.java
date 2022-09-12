package org.openjfx.eecs3311_project;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PageUtils {
	
	public static void changePage(ActionEvent event, String fxml, String title, User user) {
		Parent root = null;
		
		if (user != null) {
			try {
				FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource(fxml));
				root = loader.load();
				IndexController indexController = loader.getController();
				indexController.setUser(user);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				root = FXMLLoader.load(PageUtils.class.getResource(fxml));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle(title);
		stage.setScene(new Scene(root, 1100, 700));
		stage.show();
	}
	
	public static void changeToMoviePage(MouseEvent event, String title, User user, Movie movie) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("movieDetails.fxml"));
			root = loader.load();
			MovieDetailsController movieDetailsController = loader.getController();
			movieDetailsController.setUserAndMovie(user, movie);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle(title);
		stage.setScene(new Scene(root, 1100, 700));
		stage.show();
	}
	
	public static void changeToCartPage(ActionEvent event, User user) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("cart.fxml"));
			root = loader.load();
			CartController cartController = loader.getController();
			cartController.setUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Cart");
		stage.setScene(new Scene(root, 1100, 700));
		stage.show();
	}
	
	public static void changeToNewMoviePage(ActionEvent event, User user) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("newMovie.fxml"));
			root = loader.load();
			NewMovieController newMovieController = loader.getController();
			newMovieController.setUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("New Movie");
		stage.setScene(new Scene(root, 1100, 700));
		stage.show();
	}
	
	public static void changeToAllCustomerAccountPage(ActionEvent event, User user) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("allCustomerAccount.fxml"));
			root = loader.load();
			AllCustomerAccountController allCustomerAccountController = loader.getController();
			allCustomerAccountController.setUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Customer Accounts");
		stage.setScene(new Scene(root, 1100, 700));
		stage.show();
	}
	
	public static void changeToAllOrdersPage(ActionEvent event, User user) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("allOrders.fxml"));
			root = loader.load();
			AllOrdersController allOrdersController = loader.getController();
			allOrdersController.setUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("All Orders");
		stage.setScene(new Scene(root, 1100, 700));
		stage.show();
	}
	
	public static void changeToAllAdminAccountPage(ActionEvent event, User user) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("allAdminAccount.fxml"));
			root = loader.load();
			AllAdminAccountController allAdminAccountController = loader.getController();
			allAdminAccountController.setUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Admin Accounts");
		stage.setScene(new Scene(root, 1100, 700));
		stage.show();
	}
	
	public static void changeToAddAdminAccountPage(ActionEvent event, User user) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("addAdminAccount.fxml"));
			root = loader.load();
			AddAdminAccountController addAdminAccountController = loader.getController();
			addAdminAccountController.setUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Add Admin Account");
		stage.setScene(new Scene(root, 1100, 700));
		stage.show();
	}
	
	public static void changeToEditableAccountPage(MouseEvent event, User user, User selectedUser) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("editableAccount.fxml"));
			root = loader.load();
			EditableAccountController customerAccountController = loader.getController();
			customerAccountController.setUser(user, selectedUser);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Change Account");
		stage.setScene(new Scene(root, 1100, 700));
		stage.show();
	}
	
	public static void changeToOrdersPage(ActionEvent event, User user) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("orders.fxml"));
			root = loader.load();
			OrdersController ordersController = loader.getController();
			ordersController.setUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Orders");
		stage.setScene(new Scene(root, 1100, 700));
		stage.show();
	}
	
	public static void changeToAccountPage(ActionEvent event, User user) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("account.fxml"));
			root = loader.load();
			AccountController accountController = loader.getController();
			accountController.setUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Orders");
		stage.setScene(new Scene(root, 1100, 700));
		stage.show();
	}
	
	public static void changeToCheckoutPage(ActionEvent event, User user, ObservableList<Long> movieIds) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("checkout.fxml"));
			root = loader.load();
			CheckoutController checkoutController = loader.getController();
			checkoutController.setUserAndMovies(user, movieIds);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Checkout");
		stage.setScene(new Scene(root, 1100, 700));
		stage.show();
	}
	
	public static void changeToConfirmationPage(ActionEvent event, User user, long orderNumber) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("confirmation.fxml"));
			root = loader.load();
			ConfirmationController confirmationController = loader.getController();
			confirmationController.setUserAndOrderNumber(user, orderNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Confirmation");
		stage.setScene(new Scene(root, 1100, 700));
		stage.show();
		
	}
	
	public static void changeToCancelConfirmationPage(ActionEvent event, User user, long orderNumber) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("cancelConfirmation.fxml"));
			root = loader.load();
			CancelConfirmationController cancelConfirmationController = loader.getController();
			cancelConfirmationController.setUserAndOrderNumber(user, orderNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("CancelConfirmation");
		stage.setScene(new Scene(root, 1100, 700));
		stage.show();
		
	}
	
	public static void changeToOrderDetailsPage(MouseEvent event, User user, User selectedUser, Order order) {
		Parent root = null;
		try {
			FXMLLoader loader = new FXMLLoader(PageUtils.class.getResource("orderDetails.fxml"));
			root = loader.load();
			OrderDetailsController orderDetailsController = loader.getController();
			orderDetailsController.setUserAndOrder(user, selectedUser, order);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Order Details");
		stage.setScene(new Scene(root, 1100, 700));
		stage.show();
		
	}
	
	public static void registerUser(ActionEvent event, User user, String username, String email, String password, String permission, String realName) {
		
		UserDatabase userDatabase = new UserDatabase();
		userDatabase.load();
		
		if (userDatabase.users.containsKey(username)) {
			Alert userAlert = new Alert(Alert.AlertType.ERROR);
			userAlert.setContentText("This username already exists. Please use a different one.");
			userAlert.show();
		} else {
			User newUser = new User(username, email, password, permission, 0, realName);
			userDatabase.addNewUser(newUser);
			if (user == null) {
				changePage(event, "index.fxml", "Index", newUser);
			} else {
				changePage(event, "index.fxml", "Index", user);
			}
			
		}
		
	}
	
	public static void loginUser(ActionEvent event, String username, String password) {
		UserDatabase userDatabase = new UserDatabase();
		userDatabase.load();
		
		User user = userDatabase.users.get(username);
		if (user != null && password.equals(user.getPassword())) {
			changePage(event, "index.fxml", "Index", user);
		} else {
			Alert userAlert = new Alert(Alert.AlertType.ERROR);
			userAlert.setContentText("Invalid username or password.");
			userAlert.show();
		}
		
	}

	

}

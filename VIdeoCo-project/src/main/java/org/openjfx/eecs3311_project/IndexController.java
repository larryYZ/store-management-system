package org.openjfx.eecs3311_project;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class IndexController implements Initializable {
	
	@FXML
	private Button logoutButton;
	@FXML
	private Button clearSelectionButton;
	@FXML
	private Button cartButton;
	@FXML
	private Button ordersButton;
	@FXML
	private Button accountButton;
	@FXML
	private Button addNewMovieButton;
	@FXML
	private Button allOrdersButton;
	@FXML
	private Button customerAccountsButton;
	@FXML
	private Button adminAccountsButton;
	@FXML
	private Label welcomeLabel;
	@FXML
	private TableView<Movie> movieTable;
	@FXML
	private TableColumn<Movie, String> titleColumn;
	@FXML
	private TableColumn<Movie, String> genreColumn;
	@FXML
	private TableColumn<Movie, String> releaseDateColumn;
	@FXML
	private TextField searchByNameText;
	@FXML
	private RadioButton actionRadio;
	@FXML
	private RadioButton animationRadio;
	@FXML
	private RadioButton comedyRadio;
	@FXML
	private RadioButton documentaryRadio;
	@FXML
	private RadioButton dramaRadio;
	@FXML
	private RadioButton romanceRadio;
	@FXML
	private RadioButton scienceFictionRadio;
	@FXML
	private RadioButton thrillerRadio;
	@FXML
	private Pane adminPane;
	
	ObservableList<Movie> movieObservableList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		logoutButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				 PageUtils.changePage(event, "login.fxml", "Login", null);
			}
			
		});
		
		displayAndSearchMovies();
	}

	public void setUser(User user) {
		welcomeLabel.setText("Welcome " + user.getName() + "!");
		
		movieTable.setOnMouseClicked(e -> {
			Movie selectedMovie = movieTable.getSelectionModel().getSelectedItem();
			if (selectedMovie != null) {
				PageUtils.changeToMoviePage(e, "Movie Details", user, selectedMovie);
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
		
		if (Constant.CUSTOMER_PERMISSION.equals(user.getPermission())) {
			addNewMovieButton.setVisible(false);
		}
		
		addNewMovieButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				PageUtils.changeToNewMoviePage(event, user);
			}
		});
		
		customerAccountsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				PageUtils.changeToAllCustomerAccountPage(event, user);
			}
		});
		
		if (Constant.CUSTOMER_PERMISSION.equals(user.getPermission())) {
			adminPane.setVisible(false);
		}
		
		if (!Constant.SUPER_ADMIN_PERMISSION.equals(user.getPermission())) {
			adminAccountsButton.setVisible(false);
		}
		
		adminAccountsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				PageUtils.changeToAllAdminAccountPage(event, user);
			}
		});
		
		allOrdersButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				PageUtils.changeToAllOrdersPage(event, user);
			}
		});
	}
	
	public void displayAndSearchMovies() {
		MovieDatabase movieDatabase = new MovieDatabase();
		movieDatabase.load();
		
		movieObservableList.addAll(movieDatabase.movies.values());
			
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
		releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));

		movieTable.setItems(movieObservableList);
		
		FilteredList<Movie> filteredMovies = new FilteredList<>(movieObservableList, b -> b.getQuantity() > 0);
		
		ToggleGroup toggleGroup = new ToggleGroup();
		actionRadio.setToggleGroup(toggleGroup);
		animationRadio.setToggleGroup(toggleGroup);
		comedyRadio.setToggleGroup(toggleGroup);
		documentaryRadio.setToggleGroup(toggleGroup);
		dramaRadio.setToggleGroup(toggleGroup);
		romanceRadio.setToggleGroup(toggleGroup);
		scienceFictionRadio.setToggleGroup(toggleGroup);
		thrillerRadio.setToggleGroup(toggleGroup);
		
		clearSelectionButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				 toggleGroup.selectToggle(null);
			}
		});
		
		searchByNameText.textProperty().addListener((observerable, oldValue, newValue) -> {
			filteredMovies.setPredicate(movie -> {
				if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
					return true;
				}
				String searchName = newValue.toLowerCase();
				if (movie.getTitle().toLowerCase().indexOf(searchName) != -1) {
					return true;
				} else {
					return false;
				}
			});
			toggleGroup.selectToggle(null);
		});
		
		toggleGroup.selectedToggleProperty().addListener((observerable, oldValue, newValue) -> {
			
			filteredMovies.setPredicate(movie -> {
				if (toggleGroup.getSelectedToggle() != null) {
					RadioButton selectedRadio = (RadioButton) toggleGroup.getSelectedToggle();
					if (movie.getGenre().equals(selectedRadio.getText())) {
						
						return true;
					} else {
						return false;
					}
				}
				return true;
			});

		});
		
		movieTable.setItems(filteredMovies);
	}
 
}

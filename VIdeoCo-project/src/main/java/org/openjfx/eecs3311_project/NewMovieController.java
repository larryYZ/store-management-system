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
import javafx.scene.control.TextArea;

public class NewMovieController implements Initializable {
	
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
	private Label saveMessageLabel;
	@FXML
	private TextArea movieTitleText;
	@FXML
	private TextArea movieGenreText;
	@FXML
	private TextArea movieReleaseDateText;
	@FXML
	private TextArea movieActorsText;
	@FXML
	private TextArea movieDirectorsText;
	@FXML
	private TextArea movieDescriptionText;
	@FXML
	private TextArea movieQuantityText;
	
	private MovieDatabase movieDatabase;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		movieDatabase = new MovieDatabase();
		movieDatabase.load();
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
		
		createNewMovie();
		
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
		
	}
	
	public void createNewMovie() {
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				if (!movieTitleText.getText().isEmpty() &&
						!movieGenreText.getText().isEmpty() &&
						!movieReleaseDateText.getText().isEmpty() &&
						!movieActorsText.getText().isEmpty() &&
						!movieDirectorsText.getText().isEmpty() &&
						!movieDescriptionText.getText().isEmpty() &&
						!movieQuantityText.getText().isEmpty()) {
					Movie movie = new Movie();
					movie.setId(CommonUtils.generateNewMovieId());
					movie.setTitle(movieTitleText.getText());
					movie.setGenre(movieGenreText.getText());
					movie.setReleaseDate(movieReleaseDateText.getText());
					movie.setActors(movieActorsText.getText());
					movie.setDirectors(movieDirectorsText.getText());
					movie.setDescription(movieDescriptionText.getText());
					movie.setQuantity(Integer.valueOf(movieQuantityText.getText()));
					
					movieDatabase.update(movie);
					saveMessageLabel.setText("The new movie has been added");
					
				} else {
					Alert userAlert = new Alert(Alert.AlertType.ERROR);
					userAlert.setContentText("Please fill in all required information to add a new movie.");
					userAlert.show();
				}
			}
		});
	}
	

}

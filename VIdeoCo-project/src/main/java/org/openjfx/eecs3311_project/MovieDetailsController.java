package org.openjfx.eecs3311_project;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class MovieDetailsController implements Initializable {
	
	@FXML
	private Button logoutButton;
	@FXML
	private Button backToHomeButton;
	@FXML
	private Button addToCartButton;
	@FXML
	private Button cartButton;
	@FXML
	private Button ordersButton;
	@FXML
	private Button accountButton;
	@FXML
	private Button updateMovieButton;
	@FXML
	private Button removeMovieButton;
	@FXML
	private Label welcomeLabel;
	@FXML
	private Label changeMessageLabel;
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
	
	public void setUserAndMovie(User user, Movie movie) {
		welcomeLabel.setText("Welcome " + user.getName() + "!");
		
		backToHomeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				 PageUtils.changePage(event, "index.fxml", "Index", user);
			}
		});
		
		displayMovieDetails(user, movie);
		
		updateMovie(movie);
		
		removeMovie(user, movie);
		
		addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TentitiveOrderDatabase tentitiveOrderDatabase = new TentitiveOrderDatabase();
				tentitiveOrderDatabase.load();
				tentitiveOrderDatabase.addNewMovie(user.getName(), movie.getId());
				PageUtils.changeToCartPage(event, user);
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
		
	}
	
	public void displayMovieDetails(User user, Movie movie) {
		movieTitleText.setText(movie.getTitle());
		movieGenreText.setText(movie.getGenre());
		movieReleaseDateText.setText(movie.getReleaseDate());
		movieActorsText.setText(movie.getActors());
		movieDirectorsText.setText(movie.getDirectors());
		movieDescriptionText.setText(movie.getDescription());
		
		if (Constant.CUSTOMER_PERMISSION.equals(user.getPermission())) {
			movieTitleText.setEditable(false);
			movieGenreText.setEditable(false);
			movieReleaseDateText.setEditable(false);
			movieActorsText.setEditable(false);
			movieDirectorsText.setEditable(false);
			movieDescriptionText.setEditable(false);
			updateMovieButton.setVisible(false);
			removeMovieButton.setVisible(false);
		}
	}
	
	public void updateMovie(Movie movie) {
		updateMovieButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				movie.setTitle(movieTitleText.getText());
				movie.setGenre(movieGenreText.getText());
				movie.setReleaseDate(movieReleaseDateText.getText());
				movie.setActors(movieActorsText.getText());
				movie.setDirectors(movieDirectorsText.getText());
				movie.setDescription(movieDescriptionText.getText());
				
				movieDatabase.update(movie);
				changeMessageLabel.setText("Changes have been saved");
			}
		});
	}
	
	public void removeMovie(User user, Movie movie) {
		removeMovieButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				movieDatabase.remove(movie);
				PageUtils.changePage(event, "index.fxml", "Index", user);
			}
		});
	}
	
}

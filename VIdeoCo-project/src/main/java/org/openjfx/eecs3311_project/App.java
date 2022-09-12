package org.openjfx.eecs3311_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
    	stage.setTitle("Login");
    	stage.setScene(new Scene(root, 1100, 700));
        stage.show();
    }

	public static void main(String[] args) {
		launch(args);
	}

}
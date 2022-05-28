package edu.cuny.qc.cs348.chatBox;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Driver extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// FXMLLoader loader = new FXMLLoader();
		// loader.setLocation(new URL("/resources/ChatRoom.fxml"));
		Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));

		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

}

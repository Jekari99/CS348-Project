package edu.cuny.qc.cs348.chatBox.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.cuny.qc.cs348.chatBox.Communication;
import edu.cuny.qc.cs348.chatBox.Message;
import edu.cuny.qc.cs348.chatBox.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private TextField username;

	@FXML
	void connect(ActionEvent event) {
		Message message = new Message(this.username.getText(), null, null, null);
		Thread receive = new Thread(Communication.getConnection());
		Communication.getConnection().send(message);
		// Platform.runLater(receive);
		receive.start();
		openChat();
	}

	private void openChat() {

		try {
			User.getInstance().setName(this.username.getText());
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChatRoom.fxml"));
			Parent root = loader.load();
			Scene chatRoom = new Scene(root);
			Stage stage = (Stage) username.getScene().getWindow();
			stage.setScene(chatRoom);
			stage.show();

		} catch (IOException e) {
			Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
		}

		// ChatRoomController.client = client;
		// System.out.println(client.HOST);

	}

}

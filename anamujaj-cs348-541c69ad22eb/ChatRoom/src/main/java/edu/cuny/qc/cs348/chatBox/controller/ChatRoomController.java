package edu.cuny.qc.cs348.chatBox.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.cuny.qc.cs348.chatBox.Communication;
import edu.cuny.qc.cs348.chatBox.FileConversionUtil;
import edu.cuny.qc.cs348.chatBox.Message;
import edu.cuny.qc.cs348.chatBox.SharedData;
import edu.cuny.qc.cs348.chatBox.User;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ChatRoomController {

	private static final long MAX_FILE_SIZE = 65000;
	private Desktop desktop = Desktop.getDesktop();
	final FileChooser fileChooser = new FileChooser();
	private File file;
	private final SharedData sharedData;
	private final Communication communication;
	private final User user;
	private final ObservableList<Message> incomingMessages;

	@FXML
	private ListView<?> usersConnected;

	@FXML
	private ListView<Message> chat; // = SharedData.getListview();

	@FXML
	private TextArea textfield;

	@FXML
	private Button sendBttn;

	@FXML
	private Button audioBttn;

	@FXML
	private Button fileBttn;

	public ChatRoomController() {
		sharedData = SharedData.getInstance();
		communication = Communication.getConnection();
		user = User.getInstance();
		incomingMessages = sharedData.getIncomingMessages();
	}

	public void initialize() {
		chat.setItems(incomingMessages);
		AnimationTimer animationTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				Message message = sharedData.getMessageQueue().poll();
				if (message == null) {
					return;
				}
				incomingMessages.add(message);
			}
		};
		animationTimer.start();
	}

	@FXML
	void audioButtonHandler(ActionEvent event) {

	}

	@FXML
	void fileButtonHandler(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		file = fileChooser.showOpenDialog(stage);

		if (file.length() >= MAX_FILE_SIZE) {
			popUpAlert("File is too large!");
			return;
		}

		String fileHexed = FileConversionUtil.fileToBase64(file);
		Message fileMessage = new Message(user.getName(), null, fileHexed, file.getName());
		communication.send(fileMessage);
	}

	@FXML
	void sendButtonHandler(ActionEvent event) {
		// turn file to hex
		// Message (client.username, textfield, Hex_attachment)

		Message message = new Message(user.getName(), textfield.getText(), null, null);
		communication.send(message);
		incomingMessages.add(message);
		textfield.setText("");
		// chat.setItems(incomingMessages);
		// ChatRoomController.chat.setItems(incomingMessages);
	}

	@FXML
	void fileRetrieve(MouseEvent event) {
		Message messageSelected = chat.getSelectionModel().getSelectedItem();

		if (messageSelected != null && messageSelected.getAttachment() != null) {
			File file = FileConversionUtil.base64ToFile(messageSelected);
			if (file != null) {
				openFile(file);
			}
		}
	}

	private void openFile(File file) {
		try {
			desktop.open(file);
		} catch (IOException e) {
			Logger.getLogger(ChatRoomController.class.getName()).log(Level.SEVERE, null, e);
		}

	}

	private void popUpAlert(String message) {
		Alert onErrorAlert = new Alert(AlertType.NONE);
		onErrorAlert.setAlertType(AlertType.ERROR);
		onErrorAlert.setContentText(message);
		onErrorAlert.show();
	}
}

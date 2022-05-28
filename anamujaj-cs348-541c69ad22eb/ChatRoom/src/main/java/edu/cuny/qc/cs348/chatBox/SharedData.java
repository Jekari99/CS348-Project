package edu.cuny.qc.cs348.chatBox;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SharedData {
	private final ObservableList<Message> incomingMessages = FXCollections.observableList(new ArrayList<>());

	private final BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<>();

	private static final SharedData instance = new SharedData();

	private SharedData() {

	}

	public ObservableList<Message> getIncomingMessages() {
		return incomingMessages;
	}

	public BlockingQueue<Message> getMessageQueue() {
		return this.messageQueue;
	}

	public static SharedData getInstance() {
		return instance;
	}
}

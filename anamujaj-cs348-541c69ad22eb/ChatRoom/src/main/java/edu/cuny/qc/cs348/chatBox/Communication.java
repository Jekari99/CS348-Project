package edu.cuny.qc.cs348.chatBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Communication implements Runnable {

	public static final String HOST = "98.116.192.215";
	public static final int PORT = 30000;

	private static final Communication connInstance = new Communication();
	private final SharedData sharedData;
	private Socket socket;

	private Communication() {
		this.sharedData = SharedData.getInstance();
		this.socket = getSocket();
	}

	public static Communication getConnection() {
		return connInstance;
	}

	private Socket getSocket() {
		if (socket == null) {
			try {
				Socket socket = new Socket(HOST, PORT);
				return socket;
			} catch (IOException e) {
				Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, e);
			}
		}
		return socket;
	}

	@Override
	public void run() {

		while (socket.isConnected()) {
			receive();
		}
	}

	public void send(Message message) {
		try {
			if (!getSocket().isConnected()) {
				// unable to send
				Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, "socket is closed");
				return;
			}
			JsonStreamWriter jsonWriter = new JsonStreamWriter(new DataOutputStream(this.socket.getOutputStream()));
			jsonWriter.write(message);
		} catch (IOException e) {
			Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public void receive() {
		try {
			JsonStreamReader jsonReader = new JsonStreamReader(new DataInputStream(socket.getInputStream()));
			Message incomingMessage = jsonReader.read();
			try {
				sharedData.getMessageQueue().put(incomingMessage);
			} catch (InterruptedException e) {
				Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, e);
				try {
					sharedData.getMessageQueue().put(incomingMessage);
				} catch (InterruptedException e1) {
				}
			}
		} catch (IOException e) {
			Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, e);
		}
	}
}

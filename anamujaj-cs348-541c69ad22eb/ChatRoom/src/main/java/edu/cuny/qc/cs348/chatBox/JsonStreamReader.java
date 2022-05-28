package edu.cuny.qc.cs348.chatBox;

import java.io.DataInputStream;
import java.io.IOException;

import com.google.gson.Gson;

public class JsonStreamReader {
	private DataInputStream inputReader;

	public JsonStreamReader(DataInputStream inputStreamReader) {
		this.inputReader = inputStreamReader;
	}

	public Message read() throws IOException {
		String incomingMessage = inputReader.readUTF();
		Message convertedMessage = convertFromJson(incomingMessage);
		return convertedMessage;
	}

	private Message convertFromJson(String incomingMessage) {
		Gson gson = new Gson();
		return gson.fromJson(incomingMessage, Message.class);
	}
}

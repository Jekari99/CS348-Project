package edu.cuny.qc.cs348.chatBox;

import java.io.DataOutputStream;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonStreamWriter {

	private DataOutputStream outputWriter;

	public JsonStreamWriter(DataOutputStream outputStreamWriter) {
		this.outputWriter = outputStreamWriter;
	}

	public void write(Message message) throws IOException {
		String jsonMessage = json(message);
		outputWriter.writeUTF(jsonMessage);
	}

	private String json(Message message) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		String gsoned = gson.toJson(message);
		return gsoned;
	}
}

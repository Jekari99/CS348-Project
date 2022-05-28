package edu.cuny.qc.cs348.chatBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/*this class makes possible handling each client while server listens for new connections*/
public class ServerClientThread implements Runnable {

	public static ArrayList<ServerClientThread> clients = new ArrayList<>();
	private Socket socket;
	private JsonStreamReader inputReader;
	private JsonStreamWriter writer;
	private String clientName;

	// private Desktop desktop = Desktop.getDesktop();

	public ServerClientThread(Socket socket) {
		try {
			this.socket = socket;
			this.inputReader = new JsonStreamReader(new DataInputStream(socket.getInputStream()));
			this.writer = new JsonStreamWriter(new DataOutputStream(socket.getOutputStream()));
			Message loginMessage = inputReader.read();
			this.clientName = loginMessage.getFrom();
			clients.add(this);
			notifyClients(new Message(this.clientName, "JOINED", null, null));
		} catch (IOException e) {
			Logger.getLogger(ServerClientThread.class.getName()).log(Level.SEVERE, null, e);
			return;
		}
	}

	@Override
	public void run() {
		Message clientMessage;

		while (this.socket.isConnected() && !this.socket.isInputShutdown()) {
			try {
				clientMessage = inputReader.read();
				notifyClients(clientMessage);
			} catch (SocketException socketException) {
				Logger.getLogger(getClass().getName()).log(Level.INFO, "Closing client: " + this.clientName);
				break;
			} catch (IOException e) {
				Logger.getLogger(ServerClientThread.class.getName()).log(Level.SEVERE, null, e);
				break;
			}
		}
	}

	private void notifyClients(Message message) {
		Iterator<ServerClientThread> iterator = clients.iterator();
		while (iterator.hasNext()) {
			ServerClientThread client = iterator.next();
			if (this.clientName.equals(client.clientName)) {
				continue;
			}
			try {
				client.writer.write(message);
			} catch (SocketException socketException) {
				iterator.remove();
			} catch (IOException e) {
				Logger.getLogger(ServerClientThread.class.getName()).log(Level.SEVERE, null, e);
			}
		}
	}

	public void close(Socket socket) {
		clients.remove(this);
		// notifyClients("Chat Room: "+ clientName + " has left!");

		try {
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientName == null) ? 0 : clientName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServerClientThread other = (ServerClientThread) obj;
		if (clientName == null) {
			if (other.clientName != null)
				return false;
		} else if (!clientName.equals(other.clientName))
			return false;
		return true;
	}
}

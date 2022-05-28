package edu.cuny.qc.cs348.chatBox;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static final int PORT = 30000;

	public final ServerSocket serverSocket;

	public Server(ServerSocket socket) {
		this.serverSocket = socket;
	}

	public void start() throws IOException {
		System.out.println("Server is started!");
		try {
			/* Listen for clients to connect */
			while (!serverSocket.isClosed()) {
				Socket socket = serverSocket.accept(); // creates a new socket for the incoming connection request

				System.out.println("A new person joined the Chat Room!");

				/*
				 * In order to not block the server from accepting requests a new thread takes
				 * care of the incoming messages from a client
				 */
				ServerClientThread clientThread = new ServerClientThread(socket);
				Thread thread = new Thread(clientThread);
				thread.start();
			}
		} catch (IOException e) {
			if (serverSocket != null) {
				serverSocket.close();
			} else {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(PORT);
		Server server = new Server(socket);
		server.start();
	}
}

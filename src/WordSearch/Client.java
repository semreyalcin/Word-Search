package WordSearch;

import java.io.*;
import java.net.*;


public class Client {
	
	// IO streams
	private DataOutputStream outToServer;
	private DataInputStream inFromServer;
	
	public static void main(String[] args) {
		new Client();
	}
	
	public Client() {
		try {
			// Create a socket to connect to the server
			Socket connection = new Socket("localhost", 8008);
				
			// Create an input stream to receive data from the server
			inFromServer = new DataInputStream(connection.getInputStream());
				
			// Create an output stream to send data to the server
			outToServer = new DataOutputStream(connection.getOutputStream());
			
			// Sending and reading dummy data from server
			// For test purposes
			int x = System.in.read();
			outToServer.writeInt(x);
			x = inFromServer.readInt();
			System.out.println(x);
		}

		catch (IOException e) {
			System.err.println(e);
		}
	}
}

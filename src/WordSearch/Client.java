/*
 * Multi-Thread Server for Word Search
 * 
 * Selim Emre Yalcin - 2152205
 * Nisa Nur Odabas - 2152122
 * 
 * */

package WordSearch;

import java.io.*;
import java.net.*;


public class Client {
	
	// IO streams
	private DataOutputStream outToServer;
	private BufferedReader inFromServer;
	
	public static void main(String[] args) {
		new Client();
	}
	
	public Client() {
		try {
			// Create a socket to connect to the server
			Socket connection = new Socket("localhost", 8008);
				
			// Create an input stream to receive data from the server
			inFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				
			// Create an output stream to send data to the server
			outToServer = new DataOutputStream(connection.getOutputStream());
			
			// To get input form user
            BufferedReader inFromUser = 
            		new BufferedReader(new InputStreamReader(System.in));
            
			// Getting menu from server
			String str = null;
			str = readMultipleLines(inFromServer);
			System.out.println(str);
			
			// Getting and sending user choice
			str = inFromUser.readLine(); 
			outToServer.writeBytes(str + "\n");

			// Depending on the option different communication
			// with the server will be done
			if(str.contentEquals("1"))
			{
				// Printing server response
				str = inFromServer.readLine();
				System.out.println(str);
				
				// Getting and sending word to be searched
				str = inFromUser.readLine();
				outToServer.writeBytes(str + "\n");
			
				// Printing search result
				str = readMultipleLines(inFromServer);
				System.out.println(str);
			}
			else if(str.contentEquals("2"))
			{
				// Printing server response
				str = inFromServer.readLine();
				System.out.println(str);
				
				// Getting and sending file name
				str = inFromUser.readLine();
				outToServer.writeBytes(str + "\n");
			
				// Printing server response
				str = inFromServer.readLine();
				System.out.println(str);
				
				// Getting and sending string to be appended
				str = inFromUser.readLine();
				outToServer.writeBytes(str + "\n");
			}
			
			System.out.println("Connection is closed!\n");
			connection.close();
		}

		catch (IOException e) {
			System.err.println(e);
		}
	}
	
	// Helper function to read multiple lines from server
	// Called when there is a possibility of server to 
	// send multiple lines.
	String readMultipleLines (BufferedReader inFromServer) {
		String str;
		StringBuilder result = new StringBuilder();
		int count;
		try {
			count = inFromServer.read();
			for(int i=0; i<count; i++) {
				str = inFromServer.readLine();
				result.append(str+"\n");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.toString();
	}
}
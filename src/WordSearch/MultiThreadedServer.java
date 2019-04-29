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


public class MultiThreadedServer 
{
	
	public static void main(String[] args) 
	{
		// Running the server
		new MultiThreadedServer();
	}

	public MultiThreadedServer() 
	{
		
		try 
		{
			// Creating a server socket
			ServerSocket socket = new ServerSocket(8008);
			System.out.println("Multi-threaded Server Started!\n");
			
			// Giving each client an id
			int clientId = 0;
			
			while (true)
			{
				// Incrementing the clientId
				clientId++;
				
				// Accepting new connection requests
				Socket connection = socket.accept();
				
				// Displaying client information
				System.out.println("Thread started for client " + clientId + " with IP Adress: " 
				+ connection.getInetAddress().getHostAddress() + "\n");
				
				// Will create and run a new thread here
				ClientHandler handler = new ClientHandler(connection);
				new Thread(handler).start();
			}
		}
		catch(IOException e) 
		{
			System.err.println(e);
		}
	}
	
}
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MultiThreadedServer 
{
	private int clientLimit = 2;
	ExecutorService pool;
	ServerSocket socket;
	
	public static void main(String[] args) 
	{
		// Running the server
		try {
		new MultiThreadedServer();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	public MultiThreadedServer() throws IOException 
	{
		
		try 
		{
			// Creating a server socket
			ServerSocket socket = new ServerSocket(8008);
			System.out.println("Multi-threaded Server Started!\n");
			
			// Creating thread pool
			pool = Executors.newFixedThreadPool(clientLimit);
			
			// Giving each client an id
			int clientId = 0;

			while (true)
			{
				// Incrementing the clientId
				clientId++;
				
				// Accepting new connection requests
				Socket connection = socket.accept();
				
				// Displaying client information
				System.out.println("Thread queued for client " + clientId + " with IP Adress: " 
				+ connection.getInetAddress().getHostAddress() + "\n");
				
				// Will create and run a new thread here
				ClientHandler handler = new ClientHandler(connection,clientId);
				pool.execute(new Thread(handler));
			}
		}
		catch(IOException e) 
		{
			System.err.println(e);
		}
		finally {
			pool.shutdown();
			socket.close();
		}
	}
	
}
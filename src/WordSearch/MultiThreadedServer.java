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
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class MultiThreadedServer 
{
	private int clientLimit = 2;
	ExecutorService pool;
	ServerSocket socket;
	
	// A lock that is used when synchronising
	// threads read write operations
	// Simply doesn't allow any read operations
	// while a write is active
	private ReadWriteLock lock;
	
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
			
			// Creating new read write lock
			// It is safe to read as long as there is no one writing
			lock = new ReentrantReadWriteLock();
			
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
				// Also sending the lock to the handlers
				ClientHandler handler = new ClientHandler(connection,clientId,lock);
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
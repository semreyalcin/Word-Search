package WordSearch;

import java.io.*;
import java.net.*;


public class ClientHandler implements Runnable
{
	private Socket connection; //
	
	public ClientHandler(Socket c)
	{
		this.connection = c;
	}
	
	// The part that will run in the thread
	public void run() 
	{
		try
		{
			// Creating the input and output streams
			DataInputStream inFromClient = new DataInputStream(
					connection.getInputStream());
	        DataOutputStream outToClient = new DataOutputStream(
	        		connection.getOutputStream());
	     
	        while (true)
	        { 	
	            // Sending and reading dummy data from client
	        	// for test purposes
	        	System.out.println("Dummy Handler\n");
	        	int read = inFromClient.readInt();
	        	System.out.println(read);
	        	outToClient.writeInt(1234);
	        }
	        
		}
		catch(SocketException e)
		{
			System.out.println("Connection closed!");
		}
		catch (IOException e)
		{
			System.err.println(e);
		}
	}
}

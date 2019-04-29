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
import java.nio.file.FileSystems;


public class ClientHandler implements Runnable
{
	private Socket connection; //
	private int id;
	private String menu = "Please enter one of the following options:\n"
			+ "1- Search word\n"
			+ "2- Write to file\n"
			+ "3- Exit\n";

	public ClientHandler(Socket c, int id)
	{
		this.connection = c;
		this.id = id;
	}
	
	// The part that will run in the thread
	public void run() 
	{
		try
		{
			// Creating the input and output streams
			BufferedReader inFromClient = new BufferedReader(new
						InputStreamReader(connection.getInputStream()));
	        DataOutputStream outToClient = new DataOutputStream(
	        			connection.getOutputStream());
	
			// Giving a message stating client started to run
			System.out.println("Thread started for client " + id + " with IP Adress: " 
			+ connection.getInetAddress().getHostAddress() + "\n");
        	
			// Sending menu to client
			sendMultipleLines(menu,outToClient);
		
			// Reading option from client
			String str = inFromClient.readLine();
			
			if(str.contentEquals("1"))
			{
				// Getting word to be searched
				outToClient.writeBytes("Please enter a word to search: \n");
				str = inFromClient.readLine();
				
				System.out.println("Client " + id + " made a search with word " + str + "\n");
				str = searchFiles(str);
				
				// Sending search results
				sendMultipleLines(str,outToClient);
			}
			else if(str.contentEquals("2"))
			{
				// Getting file name from client
				outToClient.writeBytes("Please enter the file name: \n");
				String filename = inFromClient.readLine();
				
				// Getting string to be appended form client
				outToClient.writeBytes("Please enter a string to write: \n");
				str = inFromClient.readLine();
				
				// Appending string at the end of given file
				writeFile(str, filename);
			
			}
			else if(str.contentEquals("3"))
			{
				outToClient.writeBytes("Bye Bye!\n");
			}
			else
			{
				outToClient.writeBytes("Invalid input Exiting\n");
			} 	
        	
			// Giving a message stating client stopped running
			System.out.println("Thread stopped for client " + id + " with IP Adress: " 
			+ connection.getInetAddress().getHostAddress() + "\n");
			connection.close();
		}
		catch (IOException e)
		{
			System.err.println(e);
		}
	}
	
	// Helper function to send multiple lines to client
	void sendMultipleLines(String str, DataOutputStream outToClient) {
		int count = str.split("\n").length;
		try {
			outToClient.writeByte(count);
			outToClient.writeBytes(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Combines the results of all files and returns it
	String searchFiles(String word) {
		
		String wordLocations = "";
		
		try
		{
			// Setting path as same as program
			// The files that will be searched will be in files folder
			String path = FileSystems.getDefault().getPath(".").toAbsolutePath().toString();
			
			// Setting files folder
			path = path.substring(0, path.length()-1) + "files";
			
			// Getting all the files under the directory
			File directory = new File(path);
			String[] allFiles = directory.list();
			
			// If the directory is empty return a error message
			if(allFiles == null)
			{
				return "Directory has no files!\n";
			}
			else
			{
				// Search through all the files
				for(int i = 0; i < allFiles.length; i++)
				{
					String fileName = allFiles[i];
					if(fileName != null)
					{
						String filePath = directory + File.separator + fileName;
						String result = findWord(word, filePath);
						if(result!=null)
							wordLocations += result + "\n";
					}
				}
			}
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
		// Return all files result
		return wordLocations;
	}
	
	// Searches the word in the given file
	// Returns a string which contains path and line info
	String findWord(String word, String path)
	{
		// Result string to be returned
		String result = path + " ---> Lines: ";
		boolean found = false;
		try(BufferedReader fileReader = new BufferedReader(new FileReader(path)))
		{
			String line = null;
			int lineNum = 0;
			while((line = fileReader.readLine()) != null)
			{
				lineNum++;
				if(line.contains(word))
				{
					found = true;
					result += lineNum + ",";
				}
			}
			// Deleting comma at the end
			result = (String) result.subSequence(0, result.length()-1);
		}
		catch(IOException e)
		{
			System.err.println(e);
		}
		// Return single file result if found any
		if(found) 
			return result;
		return null;
	}

	// A function to append given string to end of given file
	void writeFile(String str, String fileName)
	{
		try
		{
			// Getting file path
			String path = FileSystems.getDefault().getPath(".").toAbsolutePath().toString();
			path = path.substring(0, path.length()-1) + "files" + File.separator + fileName;
			
			// Creating the file if it is not created
			File file = new File(path);
			file.createNewFile();
			
			// Appending at the end of file
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(path,true));
			fileWriter.write(str+"\n");
			fileWriter.close();

		}
		catch(IOException e)
		{
			System.err.println(e);
		}
	}
}
package rescuecore2.standard.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PyServer {
	private Socket socket;
	
	public PyServer(int port) throws IOException {
		socket = new Socket("localhost", port);
	}
	
	public void sendMessage(String message) throws IOException {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(message);
		out.flush();
		out.close();
	}
	
	public String receiveMessage() throws IOException {
		String message = null;
		String str;
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		while((str = in.readLine()) != null) 
		{
			message+=str;
		}
		in.close();
		return message;
	}
	
	public void closeConnection() throws IOException {
		socket.close();
	}
	
}

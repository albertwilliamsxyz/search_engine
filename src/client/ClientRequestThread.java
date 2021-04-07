package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import com.google.gson.JsonObject;


public class ClientRequestThread extends Thread{
	
	private String query;
	private int depth;
	public static void main(String[] args) {
		ClientRequestThread client0 = new ClientRequestThread("Variedades-Monica");
		ClientRequestThread client1 = new ClientRequestThread("Facebook");
		ClientRequestThread client2 = new ClientRequestThread("Hola");
		ClientRequestThread client3 = new ClientRequestThread("Abuelo");

		client0.start();
		client1.start();
		client2.start();
		client3.start();
	}
	
	public ClientRequestThread(String query) {
		this(query, 0);
	}
	
	
	
	public ClientRequestThread(String query, int depth){
		this.query = query;
		this.depth = depth;

	}
	
	public void run() {
		Socket socket = null;
		BufferedWriter writer = null;
		BufferedReader reader = null;
		try {
			
			String serverHost = InetAddress.getLocalHost().getHostAddress();
			socket = new Socket(serverHost, 1213);
		
            // Create input and output streams to read from and write to the server
			writer = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()) );
			reader = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
			
			JsonObject jObject = new JsonObject();
    		jObject.addProperty("query", query );
    		jObject.addProperty("depth", depth );
    		
			writer.write(jObject.toString() + "\n%END%\n");
			writer.flush();

			 String line;
	            String recibido = "";
	            while(true) {
	            	line = reader.readLine();
	            	if(line == null || line.equals("%END%"))
	            		break;
	               recibido += line+'\n';
	            }
	            System.out.println(recibido);
            
            socket.close();
            reader.close();
            writer.close();
            
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (reader != null)
					reader.close();
				if (writer != null)
					writer.close();
				if (socket != null)
					socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}

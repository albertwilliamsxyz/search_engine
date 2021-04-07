package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import hits.core.Page;


public class ClientRequest {
	

	public static void main(String[] args) {
		ClientRequest client1 = new ClientRequest("Variedades-Monica");
//		ClientRequest client2 = new ClientRequest("Facebook");

	}
	public ClientRequest(String request) {
		this(request, 0);
	}
	
	public ClientRequest(String query, int depth) {
		Gson gson = new Gson();
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

            
            String[] rec = recibido.split("\n");
            System.out.println(rec[1]);
            Page pages[] = gson.fromJson(rec[1], Page[].class);
            
            for (Page page : pages) {
				System.out.println(page);
			}
            
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

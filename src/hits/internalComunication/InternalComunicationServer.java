package hits.internalComunication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class InternalComunicationServer {
	
	private ServerSocket server;
	
	public InternalComunicationServer() throws IOException {
		
		server = new ServerSocket(1214);
		System.out.println("InternalComunicationServer Encendido");
		Socket client;
		while (true) {
			client = server.accept();
			HitsClient crawlerClient = new HitsClient(client);
			crawlerClient.start();
		}

	}
	

}

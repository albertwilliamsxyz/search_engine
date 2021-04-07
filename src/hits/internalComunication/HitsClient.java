package hits.internalComunication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.SQLException;

import hits.core.HitsImplementation;

/**
 * Clase que se encarga de manejar a 
 * @author ujarky
 */
public class HitsClient extends Thread {
	
	private Socket clientSocket;
	private BufferedReader clientReader;
	private BufferedWriter clientWriter;
	
	HitsClient(Socket s) {
		
		clientSocket = s;
		System.out.println("1) Connected: " + clientSocket);
		
	}
	
	public void run() {
		try {
			startHitting(getClientInformation());
			sendTableHeaders();
			
		} catch (Exception e) {
			e.printStackTrace();
			sendBadResult();
		}finally {
			closeClient();
		}
	}
	
	//obtener principalmente la base de datos del cliente
	private String getClientInformation() throws Exception {
		String recibido = "";

		clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		clientWriter = new BufferedWriter( new OutputStreamWriter(clientSocket.getOutputStream()) );
		String line;
        
        while(true) {
        	line = clientReader.readLine();
        	if(line == null || line.equals("%END%"))
        		break;
           recibido += line;
        }

		if(recibido.length()==0)
			throw new Exception();

		System.out.println("2) Base de datos del cliente: " + recibido);
		return recibido;
	}
	
	private void startHitting(String dataBaseName) throws SQLException, IOException {
		System.out.println("3) Comenzar con el Hits");
		HitsImplementation hits = new HitsImplementation(dataBaseName);
		hits.startHitting();	
		System.out.println("4) Hits terminado");

	}
	
	
	private void closeClient() {
		try {
			if (clientWriter != null)
				clientWriter.close();
			if (clientReader != null)
				clientReader.close();
			if (clientSocket != null)
				clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("6) Cierre del cliente");

	}
	
	private void sendTableHeaders() throws IOException {
		clientWriter.write("%url;authority;hub%\n");
		clientWriter.write("%END%\n");
		clientWriter.flush();
		
		System.out.println("5) Headers enviados");
	}
	
	private void sendBadResult() {
		//reenviar un mal resultado al cliente
		
		try {
			clientWriter.write("%ERROR%\n");
			clientWriter.write("%END%\n");
			clientWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

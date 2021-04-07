package crawler.internalComunication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import crawler.core.DBConnectionManager;
import crawler.core.WebCrawlingManager;

/**
 * Esta clase se encrga de manejar a cada cliente
 * obtiene la informacion del cliente (query a buscar en google para obtener la(s) url(s))
 * hace el craweling
 * obtiene la base de datos
 * llama al hits para que la analice
 * @author ujarky
 */
public class CrawlerClient extends Thread {
	
	//Objeto Socket del cliente
	private Socket clientSocket;
	//Objeto BufferedReader para leer la informacion enviada por cliente
	private BufferedReader clientReader;
	//Objeto BufferedWriter para enviar informacion al cliente
	private BufferedWriter clientWriter;
	
	//Objeto DBConnectionManager para conectarse con la base de datos del cliente
	private DBConnectionManager dbConnectionManager;
	//Objeto InternalComunicationClient para hacer la comunicacion con el programa encargado de hacer el hits
	private InternalComunicationClient internalComunicationClient;
	
	//Cantidad maxima de urls iniciales que pueden ser tratadas por cliente
	private int maxURLS = 1;
	//Profundidad de los WebCrawles de este cliente
	private int depth = 0;
	//index del cliente
	private int clientIndex;

	/**
	 * obtiene el socket del cliente, y crea la comunicacion interna
	 * @param s
	 * @param clientIndex
	 * @throws IOException 
	 */
	CrawlerClient(Socket clientSocket, int clientIndex) throws IOException {
		this.clientSocket = clientSocket;
		this.clientIndex = clientIndex;
		this.internalComunicationClient = new InternalComunicationClient(clientIndex);
	
		System.out.println("1) Connected: client-" + clientIndex+ " " + clientSocket);
	}
	

	public void run() {
		try {
			this.clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.clientWriter = new BufferedWriter( new OutputStreamWriter(clientSocket.getOutputStream()) );
			//hace el web Crawler
			startCraweling( makeGoogleSearch( getClientInformation() ) );
			
			//Informa al programa encargado de hacer el hits que ya puede comenzar
			if(internalComunicationClient.comunicate()) {
				System.out.println(internalComunicationClient.getAnswer());
				
				//envio de las paginas clasificadas al cliente en forma de json
				DBHitsResultsReader dbHitsResultsReader = new DBHitsResultsReader(dbConnectionManager);
				dbConnectionManager.connect();
				String packageToSend = dbHitsResultsReader.getJsonHitsTable();
				dbConnectionManager.close();
				if(packageToSend!= null) {
					clientWriter.write( "client-"+clientIndex+": SUCCES\n");
					clientWriter.write(packageToSend);
					clientWriter.write( "\n%END%");
					clientWriter.flush();
				}else {
					sendBadResult();
				}
			}

			System.out.println("6) Mensaje enviado al cliente");
		} catch (Exception e) {
			e.printStackTrace();
			sendBadResult();
		}finally {
			closeClient();
		}

	}
	
	/**
	 * Obtencion de la informacion emitida por el cliente
	 * la informacion debe se emitida en formato Json con los valores "depth" (entero opcional) "query" (String)
	 * depth prifundidad
	 * query busqueda del cliente
	 * @return String busqueda del cliente
	 * @throws Exception
	 */
	private String getClientInformation() throws Exception {
		String line;
		String received  = "";
        while(true) {
        	line = clientReader.readLine();
        	if(line == null || line.equals("%END%"))
        		break;
        	received  += line;
        }
        
        JsonElement jelement = new JsonParser().parse(received );
		JsonObject  rootObject = jelement.getAsJsonObject();
		
		String query = rootObject.get("query").getAsString();  

		try {
		depth = rootObject.get("depth").getAsInt();
		}catch(NullPointerException e) {
			System.out.println("profundidad no transmitida por el cliente");
		}
		
		System.out.println("2) El cliente esta buscando: " + query + " con una profundidad de: "+ depth);
		return query;
	}
	
	/**
	 * Realiza la busqueda en google del query enviado por el cliente y devuelve las urls encontradas
	 * @param query String busqueda del cliente
	 * @return String[] URLS encontradas por Google
	 * @throws Exception
	 */
	private String[] makeGoogleSearch(String query) throws Exception {
			ArrayList<String>  results = (ArrayList<String>) GoogleSearch.search(query);
			String roots[] = new String[ (results.size()<=maxURLS)?results.size():maxURLS ];
			for (int i = 0; i<roots.length; i++) {
				roots[i] = results.get(i);
			}
			
			if(roots.length == 0)
				throw new Exception();
			
		System.out.println("3) primer root: " + roots[0]);
		return roots;

	}
	
	/**
	 * Comienza el crawling con las urls pasadas por parametro
	 * @param roots String urls 
	 * @throws SQLException
	 * @throws IOException
	 */
	private void startCraweling(String[] roots) throws SQLException, IOException {
		System.out.println("4) Comenzando el crawling");

		WebCrawlingManager webCrawlingManager = new WebCrawlingManager(clientIndex);
		webCrawlingManager.startCraweling(roots, depth);
		dbConnectionManager = webCrawlingManager.getDBConnectionManager();
		
		System.out.println("5) crawling terminado");
	}
	
	/**
	 * cierra la coneccion con el cliente
	 */
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
		System.out.println("7) Cierre del cliente");
	}
	 
	/**
	 * Envia un error al cliente en caso de tener problemas
	 */
	private void sendBadResult() {
		try {
			clientWriter.write( "Client-"+clientIndex+": ERROR\n");
			clientWriter.write("Some problems happends during crawling module\n");
			clientWriter.write("%END%\n");
			clientWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
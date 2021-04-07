package crawler.core;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;

/**
 * Clase encargada de gestionar los WebCrawler
 * @author ujarky
 *
 */
public class WebCrawlingManager {

	//Objeto DBConnectionManager para comunicarse con la base de datos
	private DBConnectionManager dbConnectionManager;
	//Objeto DBTriadWriter para escribir en la base de dato
	private DBTriadWriter dbTriadWriter;
		
	/**
	 * Crea la base de datos correspondiente al cliente pasado por parametro
	 * crea el DBTriadWriter
	 * @param clientIndex int indoce del cliente
	 * @throws IOException
	 * @throws SQLException
	 */
	public WebCrawlingManager(int clientIndex) throws IOException, SQLException {
		dbConnectionManager = new DBConnectionManager("Data-client-" + clientIndex);
		dbConnectionManager.connect();
		dbTriadWriter = new DBTriadWriter( dbConnectionManager );
		dbConnectionManager.close();
	}
	
	/**
	 * inicia el crawling creando los WebCrawels y poniendolos a funcionar
	 * @param roots url da las paginas iniciales (se creara un WebCrawel para cada url)
	 * @param depth profundidas que pueden alcanzar los WebCrawels
	 * @throws SQLException
	 */
	public void startCraweling( String [] roots, int depth ) throws SQLException {
		dbConnectionManager.connect();
		WebCrawler []wcs = new WebCrawler[roots.length];
		for (int i =0; i<roots.length; i++) {
			wcs[i] = new WebCrawler(dbTriadWriter, roots[i], depth);
			wcs[i].start();
		}
		
		for (int i =0; i<wcs.length; i++)  {
			try{
				wcs[i].join();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		dbConnectionManager.close();
	}
	
	
//	ESPERAR HASTA LA CREACION DE UNA CLASE CLASE DBTriadrEADER
	/**
	 * Devuelve la tabla triad en forma de HashSet de triads
	 * @return HashSet de triads
	 * @throws SQLException
	 */
	/*
	public HashSet<Triad> getTriadTable() throws SQLException {
		dbConnectionManager.connect();
		HashSet<Triad> triadTable = dbTriadWriter.getTable();
		dbConnectionManager.close();
		return triadTable;
	}
	*/
	
	/**
	 * Devuelve el objeto DBConnectionManager
	 * @return
	 */
	public DBConnectionManager getDBConnectionManager() {
		return dbConnectionManager;
	}
}

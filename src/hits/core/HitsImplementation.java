package hits.core;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map.Entry;

import java.util.Set;

/**
 * Clase con los metodos necesarios para implementar el metodo hits
 * y obtener la informacion resultante luego de haberlo implementado 
 * @author ujarky
 */
public class HitsImplementation {
	
	//Objeto DBConnectionManager para comunicarse con la base de datos
	private DBConnectionManager dbConnectionManager;
	//Objeto DBCrawlerResults para obtener los datos del crawler de la base datos
	private DBCrawlerResultsReader dbCrawlerResultsReader;
	//Objeto DBCrawlerResults para escribir los resultados luego del hits
	private DBHitsWriter dbHitsWriter;
	//Objeto PagesManager para gestionar la relacion entre paginas
	private PagesManager pagesManager;
	
	/**
	 * Obtiene el nombre de la base de datos del cliente
	 * inicializa los Objetos para parer el Hits
	 * @param dataBaseName
	 * @throws SQLException
	 * @throws IOException
	 */
	public HitsImplementation(String dataBaseName) throws SQLException, IOException {
		dbConnectionManager = new DBConnectionManager(dataBaseName);
		dbConnectionManager.connect();
		dbCrawlerResultsReader = new DBCrawlerResultsReader(dbConnectionManager);
		dbHitsWriter = new DBHitsWriter(dbConnectionManager);
		pagesManager = new PagesManager(dbCrawlerResultsReader);
		dbConnectionManager.close();

	}
	
	/**
	 * Comienza el Hits
	 * @throws SQLException
	 */
	public void startHitting() throws SQLException {
//		System.out.println("Hits Started");
		dbConnectionManager.connect();
		hubsAndAuthorities();
//		hubsAndAuthoritiesNonConvergin();
		
		//writing results in the database
		writeResults();
		dbConnectionManager.close();
	}
		
	/**
	 * Metodo que se encarga de ejecutar el Algoritmo Hits
	 * para clasificar las paginas
	 * @throws SQLException
	 */
	private void hubsAndAuthorities() throws SQLException{
		Set<Entry<String, Page>> pages = pagesManager.getPagesEntrySet();
		for (int i = 0; i < 2; i++) {
			double norm = 0;
			for (Entry<String, Page> entry : pages) {
				Page p = entry.getValue();
				p.setAuth(0);
				HashSet<Page> incomingNeighbors = pagesManager.getIncomingNeighbors(p);
				for (Page q : incomingNeighbors) 
					p.setAuth( p.getAuth() + q.getHub()); 
				
				norm += Math.pow(p.getAuth(), 2);
			}
			norm = Math.sqrt(norm);
			
			for (Entry<String, Page> entry : pages) {
				Page p = entry.getValue();
				p.setAuth(p.getAuth() / norm);
			}
			
			
			for (Entry<String, Page> entry : pages) {
				Page p = entry.getValue();
				p.setHub(0);
				HashSet<Page> outgoinggNeighbors = pagesManager.getOutgoingNeighbors(p);
				for (Page r : outgoinggNeighbors) 
					p.setHub( p.getHub() + r.getAuth()); 
				
				norm += Math.pow(p.getHub(), 2);
			}
			norm = Math.sqrt(norm);
			
			for (Entry<String, Page> entry : pages) {
				Page p = entry.getValue();
				p.setHub(p.getHub() / norm);
			}
		}	
	}
	
	/**
	 * Metodo que se encarga de ejecutar el Algoritmo Hits (sin convergencia)
	 * para clasificar las paginas
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	private void hubsAndAuthoritiesNonConvergin() throws SQLException {
		Set<Entry<String, Page>> pages = pagesManager.getPagesEntrySet();

		for (int i = 0; i < 1; i++) {
			for (Entry<String, Page> entry : pages) {
				Page p = entry.getValue();
				p.setAuth(0);
				HashSet<Page> incomingNeighbors = pagesManager.getIncomingNeighbors(p);
				for (Page q : incomingNeighbors) 
					p.setAuth( p.getAuth() + q.getHub()); 
				
			}
			
			for (Entry<String, Page> entry : pages) {
				Page p = entry.getValue();
				p.setHub(0);
				HashSet<Page> outgoinggNeighbors = pagesManager.getOutgoingNeighbors(p);
				
				for (Page r : outgoinggNeighbors) 
					p.setHub( p.getHub() + r.getAuth()); 
				
			}
		}	
	}
	
	/**
	 * Escribe los resultados en en la base de datos
	 */
	public void writeResults() {
		Set<Entry<String, Page>> pages = pagesManager.getPagesEntrySet();
		for (Entry<String, Page> entry : pages) {
			this.dbHitsWriter.insertPage(entry.getValue());
		}
			
		
	}
	
	/*
	public HashSet<Page> getExistingURLS() throws SQLException {
		dbConnectionManager.connect();
		Set<Entry<String, Page>> pages = pagesManager.getPagesEntrySet();
		HashSet<Page> result = new HashSet<>();
		for (Entry<String, Page> entry : pages) {
			result.add(entry.getValue());
		}
		dbConnectionManager.close();
		return result;
	}
	
	public HashSet<Page> getHits_resultTable() throws SQLException {
		dbConnectionManager.connect();
		HashSet<Page> results = dbWriterResults.getTable();
		dbConnectionManager.close();
		return results;
	}
	*/
	
}

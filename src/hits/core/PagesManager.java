package hits.core;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Clase encargada de gestionar las paginas y las relaciones entre ellas
 * contiene unas series de tablas que permiten buscar las paginas de distintas maneras
 * @author ujarky
 */
public class PagesManager {
	//Tabla Hash que contiene todas las paginar (sin repetidos) encontradas por el programa crawler
	//la llave es la url de la pagina
	private Hashtable<String, Page> pages;
	//Tabla hash que contiene todas las paginas source de las paginas destino
	private Hashtable<String, HashSet<Page>> sourcePages;
	//Tabla hash que contiene todas las paginas destino de las paginas source
	private Hashtable<String, HashSet<Page>>  destinyPages;
	//Objeto DBCrawlerResultsReader que permite leer los resultados del webcrawler del cliente
	private DBCrawlerResultsReader dbResult;

	/**
	 * Construye un PagesManager con un DBCrawlerResultsReader y un DBHitsWriter
	 * al momento de la contruccion obtiene todas las paginas en la base de dato
	 * @param dbResults
	 * @param dbWriterResults
	 */
	public PagesManager(DBCrawlerResultsReader dbResults) {
		this.dbResult = dbResults;
		this.pages = new Hashtable<>();
		this.sourcePages = new Hashtable<>();
		this.destinyPages = new Hashtable<>();
		initPages();
	}
	
	/**
	 * Obtencion de las paginas en la base de dato
	 */
	private void initPages() {
		HashSet<String> urls;
		try {
			urls = dbResult.getExistingURLS();
			for (String url : urls) {
				pages.put( url, new Page(url));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Anade una url source y una lista de paginas (punteros a las paginas ya existentes)
	 * a la lista destinyPages
	 * @param source
	 */
	private void addToDestinyHashtable(String source) {
		HashSet<String> urlsDestiny= dbResult.getURLSDestinyFromURLSource(source);
		HashSet<Page> pagesDestiny = new HashSet<>();
		for (String url : urlsDestiny) {
			pagesDestiny.add(pages.get(url));
		}
		destinyPages.put(source, pagesDestiny);
	}
	
	/**
	 * Anade una url destiny y una lista de paginas (punteros a las paginas ya existentes)
	 * a la lista sourcePages
	 * @param source
	 */
	private void addToSourceHashtable(String destiny) {
		HashSet<String> urlsSource= dbResult.getURLSSourceFromURLDestiny(destiny);
		HashSet<Page> pagesSource = new HashSet<>();
		for (String url : urlsSource) {
			pagesSource.add(pages.get(url));
		}
		sourcePages.put(destiny, pagesSource);
	}
	
	/**
	 * Devuelve la tabla de paginas existentes
	 * @return
	 */
	public Hashtable<String, Page> getPages(){
		return this.pages;
	}
	
	/**
	 * Devuelve un Set con las paginas existentes
	 * @return
	 */
	public Set<Entry<String, Page>> getPagesEntrySet(){
		return this.pages.entrySet();
	}
	
	/**
	 * Devuelve la lista de paginas que estan contenidas en una pagina origen
	 * @param p Pagina origen
	 * @return HashSet de Page lista de paginas destino
	 */
	public HashSet<Page> getIncomingNeighbors(Page p) {
		if( !destinyPages.containsKey(p.getUrl()))
			addToDestinyHashtable(p.getUrl());
		
		return destinyPages.get(p.getUrl());
	}
	
	/**
	 * Devuelve la lista de paginas que llaman o que contienen a la pagina destino p
	 * @param p Pagina destino
	 * @return HashSet de Page lista de paginas origen
	 */
	public HashSet<Page> getOutgoingNeighbors(Page p) {
		if( !sourcePages.containsKey(p.getUrl()))
			addToSourceHashtable(p.getUrl());
		
		return sourcePages.get(p.getUrl());
	}
	

}

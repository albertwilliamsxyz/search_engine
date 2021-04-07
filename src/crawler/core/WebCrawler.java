package crawler.core;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Clace encargada de crear los WebCrawler
 *  de cada cliente que recorreran las URL
 * @author ujarky
 */
public class WebCrawler extends Thread{
	
	//Objeto DBTriadWriter para escribir los resultados
	private DBTriadWriter dbTriadWriter;
	
	//URL en la que va a comenzar este WebCrawler
	private String source;
	
	//profundida a la cual pude llegar el WebCrawler
	private int depth;
	
	/**
	 * Crea el WebCrawler inciando su escritor, pagina inicial, y su profundidad
	 * @param dbTriadWriter
	 * @param source
	 * @param depth
	 */
	public WebCrawler( DBTriadWriter dbTriadWriter, String source, int depth ) {
		this.dbTriadWriter = dbTriadWriter;
		this.source = source;
		this.depth = depth;
	}
	
	/**
	 * Iniciar la ejecucion de WebCrawler
	 */
	public void run(){
		try {
			indexSetOfURLs(source, depth);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Obtencion de todas la etiquetas a con href de la pgin demarcada por la url
	 * @param url URL de la pagina
	 * @return Elements grupo de etiquetas a
	 * @throws IOException
	 */
	public Elements getHyperlinks(String url) throws IOException {
		Document document = Jsoup.connect(url).get();
		Elements hyperlinks = document.select("a[href]");
		return hyperlinks;
	}

	/**
	 * Metodo recursivo encargado de hacer el crawling de paginas
	 * @param url URL de la pagina a hacer el crawling
	 * @param depth profundidad maxima del crawling
	 * @throws IOException
	 */
	public void indexSetOfURLs(String url, int depth) throws IOException {
		//si aun se puede ir mas profundo
		if (depth > 0) {
			try {
				//obtengo todas las urls contenidas en la pagina y
				//llamo nuevamente al metodo por cada url disminuyende la profundidad
				for (Element element: getHyperlinks(url)) {
					String href = element.attr("href");
					if (href.startsWith("https")) {
						indexSetOfURLs(href, depth - 1);
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		//si no se puede ir mas profundo se obtiene todas las urls contenidas en la pagina
		//y se guardan en la base de datos 
		} else {
			try {
				for (Element element: getHyperlinks(url)) {
					String href = element.attr("href");
					if (href.startsWith("https")) {
						dbTriadWriter.insertTriad( new Triad(url, href, element.text() ) );
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
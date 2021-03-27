package sinNombre;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OldWebCrawler {

	public static void main(String[] args) {
		try {
			String[] rootURLs = {
				"https://en.wikipedia.org/"
				//"https://www.reddit.com/",
				//"https://techterms.com/definition/recursive_function",
			};
			for (String rootURL: rootURLs) {
				ArrayList<String> listOfURLs = getListOfURLs(rootURL, 1, new ArrayList<String>());
				System.out.println("Imprimiendo respuestas");
				for (String url: listOfURLs) {
					System.out.println(url);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	/**
	 * Devuelve el HTML de una pagina web en formato texto
	 * @param url URL de la pagina
	 * @return String que contiene todo el HTML
	 */
	private static String getHTML( String url) {
		URL u;
		try {
			u = new URL(url);
			java.net.URLConnection conn = u.openConnection();
			conn.setRequestProperty("User-Agent", "BBot/1.0");
			conn.setRequestProperty("Accept-Cherset", "UTF-8");
			
			InputStream  is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			
			String line;
			String html = "";
			while ( (line = reader.readLine()) != null)  {
				html += line +"\n";
			}
			html = html.trim();
			
			return html;
			
			
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Devuelve un objeto de tipo Element que contiene todos las etiquetas 'a' de la pagina web
	 * @param url url de la pagina web
	 * @return Objeto Element con todas las etiquetas a
	 * @throws IOException
	 */
	public static Elements getHyperlinks(String url) throws IOException {
		String textHTML = getHTML(url);
		if (textHTML!= null ) {
			Document document = Jsoup.parse(textHTML);
			Elements hyperlinks = document.select("a[href]");
			return hyperlinks;
		}
		throw new IOException("************** MAAAAAAMALOOOOOOO *************");
	}
	
	
	/**
	 * Devuelve una lista de urls provenientes de una pogina con url
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<String> getUrlsFromUrl(String url) throws IOException {
		ArrayList<String> urls = new ArrayList<String>();
		try {
			for (Element element: getHyperlinks(url)) {
				String href = element.attr("href");
				if (href.startsWith("https")) {
					urls.add(href);
				}
			}
		} catch (HttpStatusException e) {
			System.out.println(e);
		}
		return urls;
	}
	
	public static ArrayList<String> getListOfURLs(String url, int depth, ArrayList<String> listOfURLs) throws IOException {
		if (depth > 0) {
			for (Element hyperlink: getHyperlinks(url)) {
				String href = hyperlink.attr("href");
				if (href.startsWith("https")) {
					listOfURLs.add(href);
					listOfURLs.addAll(getListOfURLs(href, depth - 1, listOfURLs));
				}
			}
			return listOfURLs;
		} else {
			return getUrlsFromUrl(url);
		}
	}
}
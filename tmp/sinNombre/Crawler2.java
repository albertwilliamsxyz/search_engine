package sinNombre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Un crawler se va a mater en cada pagina va a obtener todos los links de las etiquetas a
 * y por cada etiqueta a va a llamar a otro craewelr para que verifique esa pagina
 * @author ujarky
 *
 */
public class Crawler2 extends Thread{
	
	private String source;
	private HashSet<Triad> cibles;
	private Sender sender;
	
	public Crawler2(String source, Sender sender) {
		this.source = source;
		cibles = new HashSet<>();
		this.sender = sender;
	}
	
	public static Elements getHyperlinks(String url) throws IOException {
		Document document = Jsoup.connect(url).get();
		Elements hyperlinks = document.select("a[href]");
		return hyperlinks;
	}
	
	
	public void catchUrlsFromUrl(String url) throws IOException {
		try {
			for (Element element: getHyperlinks(url)) {
				String href = element.attr("href");
				String text = element.text();
				if (href.startsWith("https")) {
					cibles.add( new Triad(href, text, null) );
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	
	public void run() {
		try {
			catchUrlsFromUrl(source);
			if(cibles.size() > 0)
				sender.send(cibles);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}

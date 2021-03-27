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
public class Crawler extends Thread{
	
	private String source;
	private HashSet<Triad> cibles;
	private Sender sender;
	
	public Crawler(String source, Sender sender) {
		this.source = source;
		cibles = new HashSet<>();
		this.sender = sender;
	}
	
	public static Elements getHyperlinks(String url) throws IOException {
		Document document = Jsoup.connect(url).get();
		Elements hyperlinks = document.select("a[href]");
		return hyperlinks;
	}

	public static HashSet<Triad> getUrlsFromUrl(String url) throws IOException {
		HashSet<Triad> urls = new HashSet<>();
		try {
			for (Element element: getHyperlinks(url)) {
				String href = element.attr("href");
				String text = element.text();

				if (href.startsWith("https")) {
					urls.add(new Triad(href,text ,null) );
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return urls;
	}

	public static HashSet<Triad> getSetOfURLs(String url, int depth, HashSet<Triad> setOfURLs) throws IOException {
		if (depth > 0) {
			try {
				for (Element hyperlink: getHyperlinks(url)) {
					String href = hyperlink.attr("href");
					String text = hyperlink.text();
					if (href.startsWith("https")) {
						setOfURLs.add( new Triad(href,text ,url) );
						setOfURLs.addAll(getSetOfURLs(href, depth - 1, setOfURLs));
					}
				}
			} catch (Exception e) {
			}
			return setOfURLs;
		} else {
			return getUrlsFromUrl(url);
		}
	}

	
	public void run() {
		try {
			getSetOfURLs(source,1,cibles);
			if(cibles.size() > 0)
				sender.send(cibles);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}

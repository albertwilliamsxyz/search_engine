package crawler;

import java.io.IOException;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler2 extends Thread{
	
	private TriadManager tm;
	String source;
	public WebCrawler2( TriadManager tm, String source ) {
		this.tm = tm;
		this.source = source;
	}
	

	public  Elements getHyperlinks(String url) throws IOException {
		Document document = Jsoup.connect(url).get();
		Elements hyperlinks = document.select("a[href]");
		return hyperlinks;
	}

	

	public HashSet<Triad> getUrlsFromUrl(String url) throws IOException {
		HashSet<Triad> urls = new HashSet<>();
		try {
			for (Element element: getHyperlinks(url)) {
				String href = element.attr("href");
				if (href.startsWith("https")) {
					Triad triad = new Triad(url,href , element.text());
					if(urls.add(triad) ) {
						urls.add( new Triad(url,href , element.text()) );
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return urls;
	}
/*
	public HashSet<Triad> getSetOfURLs(String url, int depth, HashSet<Triad> setOfURLs) throws IOException {
		if (depth > 0) {
			try {
				for (Element hyperlink: getHyperlinks(url)) {
					String href = hyperlink.attr("href");
					if (href.startsWith("https")) {
						
						Triad triad = new Triad(url,href , hyperlink.text());
						if(setOfURLs.add(triad) ) {
							tm.insertTriad(triad);
							setOfURLs.addAll(getSetOfURLs(href, depth - 1, setOfURLs));
						}
						
					}
				}
			} catch (Exception e) {
			}
			return setOfURLs;
		} else {
			return getUrlsFromUrl(url);
		}
	}

	*/
	public void run() {
		try {
			getSetOfURLs(source,1,new HashSet<Triad>() );
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

package crawler;

import java.io.IOException;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler extends Thread{
	
	private TriadManager tm;
	private String source;
	private int deep;
	public WebCrawler( TriadManager tm, String source, int deep ) {
		this.tm = tm;
		this.source = source;
		this.deep = deep;
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
					urls.add( new Triad(url,href , element.text()) );
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return urls;
	}

	public HashSet<Triad> getSetOfURLs(String url, int depth, HashSet<Triad> setOfURLs) throws IOException {
		if (depth > 0) {
			try {
				for (Element hyperlink: getHyperlinks(url)) {
					String href = hyperlink.attr("href");
					if (href.startsWith("https")) {
						
						Triad triad = new Triad(url,href , hyperlink.text());
						if(setOfURLs.add(triad) ) {
							tm.insertTriad(triad);
							System.out.println(triad.toString());
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

	
	public void run() {
		try {
			getSetOfURLs(source,deep,new HashSet<Triad>() );
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

class App {
	public static void main(String[] args) throws IOException {
		try {
			String[] rootURLs = {
				"https://en.wikipedia.org/",
				"https://www.reddit.com/",
			};
			String[] urls = getListOfURLs(rootURLs);
			System.out.println(urls);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static String[] getListOfURLs(String[] rootURLs) throws IOException {
		for (String url: rootURLs) {
			Document document = Jsoup.connect(url).get();
		} 
		return rootURLs;
	}
}

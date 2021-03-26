import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class App {
	public static void main(String[] args) throws IOException {
		try {
			String[] rootURLs = {
				"https://en.wikipedia.org/",
				// "https://www.reddit.com/",
			};
			for (String rootURL: rootURLs) {
				ArrayList<String> listOfURLs = getListOfURLs(rootURL, 1, new ArrayList<String>());
				System.out.println("Imprimiendo respuestas");
				for (String url: listOfURLs) {
				}
			}
		} catch (Exception e) {
		}
	}

	public static ArrayList<String> getListOfURLs(String url, int depth, ArrayList<String> listOfURLs) throws IOException {
		System.out.println("----------------------------------------------------------------------------------------------------");
		System.out.println("Entrando en getListOfURLs. url: " + url + " depth: " + depth);
		if (depth > 0) {
			Document document = Jsoup.connect(url).get();
			Elements hyperlinks = document.select("a[href]");
			for (Element hyperlink: hyperlinks) {
				String href = hyperlink.attr("href");
				if (href.startsWith("https")) {
					System.out.println("href: " + href);
					ArrayList<String> response = getListOfURLs(href, depth - 1, listOfURLs);
					listOfURLs.addAll(response);
				}
			}
		}
		System.out.println(listOfURLs.size());
		return listOfURLs;
	}
}

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class App {
	public static void main(String[] args) {
		try {
			String[] rootURLs = {
				"https://en.wikipedia.org/",
				"https://www.reddit.com/",
				"https://techterms.com/definition/recursive_function",
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

	public static Elements getHyperlinks(String url) throws IOException {
			Document document = Jsoup.connect(url).get();
			Elements hyperlinks = document.select("a[href]");
			return hyperlinks;
	}
	
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

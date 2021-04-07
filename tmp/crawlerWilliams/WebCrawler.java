package search_engine;

import java.io.IOException;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class WebCrawler {
	public static void run(String rootURL) throws IOException {
		try {
			getSetOfURLs(rootURL, 1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static Elements getHyperlinks(String url) throws IOException {
		Document document = Jsoup.connect(url).get();
		Elements hyperlinks = document.select("a[href]");
		return hyperlinks;
	}

	public static void indexSetOfURLs(String url, int depth) throws IOException {
		if (depth > 0) {
			try {
				for (Element hyperlink: getHyperlinks(url)) {
					String href = hyperlink.attr("href");
					if (href.startsWith("https")) {
						insertTriad(url, source, destiny, content);
						getSetOfURLs(href, depth - 1, setOfURLs);
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			try {
				for (Element element: getHyperlinks(url)) {
					String href = element.attr("href");
					if (href.startsWith("https")) {
						insertTriad(url, source, destiny, content);
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}

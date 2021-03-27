package sinNombre;

import java.io.IOException;
import java.io.File;
import java.util.HashSet;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jsoup.Jsoup;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class App {
	public static void main(String[] args) throws IOException {
		String databaseURL = buildDatabaseURL("testing.db");
		createDatabase(databaseURL);
		createTriadsTable(databaseURL);
		insertTriad(databaseURL, "testing1", "testing2", "testing3");
		insertTriad(databaseURL, "testing2", "testing3", "testing4");
		insertTriad(databaseURL, "testing3", "testing4", "testing5");
		insertTriad(databaseURL, "testing5", "testing5", "testing6");
	}

	/*
	public static String buildDatabaseURL(String databaseName) throws IOException {
		String currentDirectory = new File(".").getCanonicalPath();
		String url = "jdbc:sqlite:" + currentDirectory + "/" + databaseName;
		return url;
	}

	public static void insertTriad(String url, String source, String destiny, String content) throws IOException {
		String sql = "INSERT INTO triads (source, destiny, content) VALUES (?,?,?)";
		try (
				Connection connection = DriverManager.getConnection(url);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)
		) {
			if (connection != null) {
				preparedStatement.setString(1, source);
				preparedStatement.setString(2, destiny);
				preparedStatement.setString(3, content);
				preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
	}

	public static void createDatabase(String url) throws IOException {
		try (Connection connection = DriverManager.getConnection(url)) {
			if (connection != null) {
				DatabaseMetaData metadata = connection.getMetaData();
				System.out.println("The driver name is " + metadata.getDriverName());
				System.out.println("A new database has been created.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
	}

	public static void createTriadsTable(String url) throws IOException {
		String sql = "CREATE TABLE triads (" + 
			"  source VARCHAR(64) NOT NULL," + 
			"  destiny VARCHAR(64) NOT NULL," + 
			"  content VARCHAR(255)," +
			"	 PRIMARY KEY (source, destiny)" +
			")";
		try (
				Connection connection = DriverManager.getConnection(url);
				Statement statement = connection.createStatement()
		) {
			if (connection != null) {
				statement.execute(sql);
				System.out.println("Table triads created.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
	}
*/
	public static void execute() throws IOException {
		try {
			String[] rootURLs = {
				"https://www.wikipedia.com/",
				"https://techterms.com/definition/recursive_function",
			};
			for (String rootURL: rootURLs) {
				HashSet<String> setOfURLs = getSetOfURLs(rootURL, 1, new HashSet<String>());
				for (String url: setOfURLs) {
				}
			}
		} catch (Exception e) {
		}
	}

	public static Elements getHyperlinks(String url) throws IOException {
		Document document = Jsoup.connect(url).get();
		Elements hyperlinks = document.select("a[href]");
		return hyperlinks;
	}

	public static HashSet<String> getUrlsFromUrl(String url) throws IOException {
		HashSet<String> urls = new HashSet<String>();
		try {
			for (Element element: getHyperlinks(url)) {
				String href = element.attr("href");
				if (href.startsWith("https")) {
					urls.add(href);
				}
			}
		} catch (Exception e) {
		}
		return urls;
	}

	public static HashSet<String> getSetOfURLs(String url, int depth, HashSet<String> setOfURLs) throws IOException {
		if (depth > 0) {
			try {
				for (Element hyperlink: getHyperlinks(url)) {
					String href = hyperlink.attr("href");
					if (href.startsWith("https")) {
						setOfURLs.add(href);
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
}
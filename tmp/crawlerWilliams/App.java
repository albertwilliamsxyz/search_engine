package search_engine;

import java.io.IOException;

class App {
	public static void main(String[] args) throws IOException {
		String[] rootURLs = {
			"https://www.wikipedia.com/",
			"https://techterms.com/definition/recursive_function",
		};
		WebCrawler.run(rootURLs);
	}
	
	public static void testing() throws IOException {
		String databaseURL = buildDatabaseURL("testing.db");
		DatabaseConnectionManager.createDatabase(databaseURL);
		DatabaseConnectionManager.createTriadsTable(databaseURL);
		DatabaseConnectionManager.insertTriad(databaseURL, "testing1", "testing2", "testing3");
		DatabaseConnectionManager.insertTriad(databaseURL, "testing2", "testing3", "testing4");
		DatabaseConnectionManager.insertTriad(databaseURL, "testing3", "testing4", "testing5");
		DatabaseConnectionManager.insertTriad(databaseURL, "testing5", "testing5", "testing6");
	}
}

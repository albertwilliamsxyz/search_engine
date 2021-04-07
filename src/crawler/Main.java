package crawler;

import java.io.IOException;

import crawler.internalComunication.CrawlerServer;

public class Main {
	
	//Start Crawler server for getting clients communication
	public static void main(String[] args) {
		try {
			CrawlerServer crawlerServer = new CrawlerServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

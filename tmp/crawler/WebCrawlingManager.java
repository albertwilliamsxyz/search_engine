package crawler;

import java.util.LinkedList;
import java.util.Queue;

public class WebCrawlingManager {

		private DBConnectionManager dbcm;
		private TriadManager tm;
	
		
	public WebCrawlingManager(String [] roots) {
		try {
			dbcm = new DBConnectionManager("Data");
			tm = new TriadManager(dbcm);
			startCrawels( roots );
//			printDatabase();
			tm.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public void startCrawels( String [] roots ) {
		WebCrawler []wcs = new WebCrawler[roots.length];
		for (int i =0; i<roots.length; i++) {
			wcs[i] = new WebCrawler(tm, roots[i],2);
			wcs[i].start();
		}
		
		for (int i =0; i<wcs.length; i++)  {
			try{
				wcs[i].join();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
        System.out.println("Donnes: " +tm.getCounter());

	}
	
	public void printDatabase() {
		tm.printTable();
	}
}

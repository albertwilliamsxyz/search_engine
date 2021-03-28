package crawler;

import java.io.File;
import java.io.IOException;


public class DBConnectionManager {
	
	String url;
	public DBConnectionManager( String DBName ) {
		File dir = new File("DDBB");
		if(!dir.exists()) {
			dir.mkdir();
			System.out.println("Data base Folder created");
		}
		url = buildDatabaseURL(DBName);
	}
	
	private String buildDatabaseURL(String DDBBName){
		if(!DDBBName.endsWith(".db"))
			DDBBName += ".db";
		try {		
			return ("jdbc:sqlite:" +  new File("DDBB").getCanonicalPath()  +"/"+ DDBBName);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getURL() {
		return this.url;
	}

}

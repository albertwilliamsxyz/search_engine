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
	
	public String getURL() {
		return this.url;
	}

	private String buildDatabaseURL(String DDBBName){
		if(!DDBBName.endsWith(".db")) DDBBName += ".db";
		try {		
			return ("jdbc:sqlite:" +  new File("DDBB").getCanonicalPath()  +"/"+ DDBBName);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean connect(){
		try {
			connection = DriverManager.getConnection(this.dbcm.getURL());
			if (connection != null) {
				System.out.println("Connected to: " + connection.getMetaData().getDriverName());
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void close() {
		if(connection != null) {
			try {
				connection.close();
				connection = null;
				System.out.println("Data base disconected");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

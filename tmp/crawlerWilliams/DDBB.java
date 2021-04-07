package sinNombre;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DDBB {
	public static final String DDBB_FOLDER_NAME = "DDBB";
	
	public static final String DDBB_TRIADS_NAME = "Data";
	
	public static final String TRIADS_TABLE_NAME = "triads";

	private Connection connection;
	
	public DDBB(){
		File dir = new File("DDBB");
		if(!dir.exists()) {
			dir.mkdir();
			System.out.println("Data base Folder created");
		}
		connection = null;
	}

	public static String buildDatabaseURL(String DDBBName) throws IOException {
		if(!DDBBName.endsWith(".db"))
			DDBBName += ".db";
		String currentDirectory = new File(DDBB_FOLDER_NAME).getCanonicalPath();
		String url = "jdbc:sqlite:" + currentDirectory  +"/"+ DDBBName;
		
		return url;
	}
	
	private boolean connection(String DDBBName){
		try {
			connection = DriverManager.getConnection( buildDatabaseURL(DDBBName) );
			if (connection != null) {
				System.out.println("Connected to: " + connection.getMetaData().getDriverName());
				System.out.println("Connected to: " + connection.getMetaData().getDatabaseProductName());

				return true;
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean connection() {
		return connection(DDBB_TRIADS_NAME);
	}
	

	public void close() {
		if(connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isClosed() {
		return connection == null;
	}

	
	public boolean createTriadsTable(){
	
		if( connection!= null )
		{
			String sql = "CREATE TABLE if not exists "+ TRIADS_TABLE_NAME +" (" + 
				"  source VARCHAR(64) NOT NULL," + 
				"  destiny VARCHAR(64) NOT NULL," + 
				"  content VARCHAR(255)," +
				"	 PRIMARY KEY (source, destiny)" +
				")";

			try {
				Statement statement = connection.createStatement();
				statement.execute(sql);
				System.out.println("Table triads created.");

				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	
	public boolean insertTriad(String source, String destiny, String content) {
		if (connection != null) {
			try {
				String sql = "INSERT INTO "+ TRIADS_TABLE_NAME +" (source, destiny, content) VALUES (?,?,?)";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
	
				preparedStatement.setString(1, source);
				preparedStatement.setString(2, destiny);
				preparedStatement.setString(3, content);
				preparedStatement.executeUpdate();
				System.out.println("elementos incertados en la table: " + source + "\t" + destiny +"\t" + content );

				return true;
			} catch (SQLException e) {
				 e.printStackTrace();
				System.out.println("**************** Duplicated element **************");
			}
		}
		return false;
	}
}

package crawler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class TriadManager {
	
	private DBConnectionManager dbcm ;
	private int counter;
	private static int maxData = 10000;
	private Connection connection;
	
	public TriadManager( DBConnectionManager dbcm) throws IOException {
		this.dbcm = dbcm;
		connect();
		createTriadsTable();
		counter = 0;
	}
	
	private boolean connect(){
		
		try {
			connection = DriverManager.getConnection( this.dbcm.getURL() );
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
	
	public synchronized boolean insertTriad( Triad triad) {
		if(counter < maxData) {
			String sql = "INSERT INTO triads (source, destiny, content) VALUES (?,?,?)";
//			Connection connection = null;
			try {
//					connection = DriverManager.getConnection(this.dbcm.getURL());
					PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
				if (connection != null) {				
					preparedStatement.setString(1, triad.getSource());
					preparedStatement.setString(2, triad.getCible());
					preparedStatement.setString(3, triad.getMot());
					preparedStatement.executeUpdate();
				}
				counter++;
				return true; //
	
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}/*
			finally {
				if (connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}	*/
		}
		return false;
	}
	
	
	public void createTriadsTable() throws IOException {
		String sql = "CREATE TABLE if not exists triads (" + 
			"  source VARCHAR(64) NOT NULL," + 
			"  destiny VARCHAR(64) NOT NULL," + 
			"  content VARCHAR(255)," +
			"	 PRIMARY KEY (source, destiny)" +
			")";
//		Connection connection = null;
		try 
		{
//			System.out.println( this.dbcm.getURL());
//			connection = DriverManager.getConnection( this.dbcm.getURL() );
			Statement statement = connection.createStatement();
		
			if (connection != null) {
				statement.execute(sql);
				System.out.println("Table triads created.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}/*
		finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}	*/
	}
	
	public boolean isEnaugh() {
		return counter >= maxData;
	}
	
	public void printTable() {
		
	        String sql = "SELECT source, destiny, content FROM triads";
	        
//			Connection connection = null;
		try {
//			connection = DriverManager.getConnection( this.dbcm.getURL() );
	             Statement stmt  = this.connection.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql);
	            
	            // loop through the result set
	            while (rs.next()) {
	                System.out.println(rs.getString("source") +  "\t" + 
	                                   rs.getString("destiny") + "\t" +
	                                   rs.getString("content"));
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
    }

	public int getCounter() {
		return counter;
	}
	
	
	
	
	 
}

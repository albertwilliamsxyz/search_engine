package crawler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class TriadManager {
	private DBConnectionManager databaseConnectionManager;
	
	public TriadManager(DBConnectionManager databaseConnectionManager) throws IOException {
		this.databaseConnectionManager = databaseConnectionManager;
		createTriadsTable();
	}
	
	public synchronized boolean insertTriad(Triad triad) {
		String sql = "INSERT INTO triads (source, destiny, content) VALUES (?,?,?)";
		try {
			PreparedStatement preparedStatement = databaseConnectionManager.getConnection().prepareStatement(sql);
			if (connection != null) {				
				preparedStatement.setString(1, triad.getSource());
				preparedStatement.setString(2, triad.getCible());
				preparedStatement.setString(3, triad.getMot());
				preparedStatement.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
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
		try {
			Statement statement = connection.createStatement();
			if (connection != null) {
				statement.execute(sql);
				System.out.println("Table triads created.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public boolean isEnaugh() {
		return counter >= maxData;
	}
	
	public void printTable() {
		String sql = "SELECT source, destiny, content FROM triads";
		try {
				Statement statement  = this.connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql);
				while (resultSet.next()) {
					System.out.println(
						resultSet.getString("source") + "\t" + 
						resultSet.getString("destiny") + "\t" +
						resultSet.getString("content")
					);
				}
		} catch (SQLException e) {
				System.out.println(e.getMessage());
		}
	}

	public int getCounter() {
		return counter;
	}
}

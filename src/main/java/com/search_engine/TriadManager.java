package search_engine;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class TriadManager {
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
}

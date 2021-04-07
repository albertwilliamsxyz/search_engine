package crawler.core;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

/**
 * Clase encargada de escribir en la base de datos del cliente la
 * tabla que contiene todos los triads encontrados por su busqueda
 * @author ujarky
 */
public class DBTriadWriter {
	
	//Objeto DBConnectionManager para comunicarse con la bese de datos
	private DBConnectionManager dbConnectionManager ;
	
	//contador de elementos anadidos (Posiblemente no va a ir aqui)
	private static int counter = 0;

	/**
	 * Crea el DBConnectionManager
	 * y crea la tabla triads para guardar la informacion
	 * @param dbConnectionManager
	 * @throws IOException
	 * @throws SQLException
	 */
	public DBTriadWriter( DBConnectionManager dbConnectionManager) throws IOException, SQLException {
		this.dbConnectionManager = dbConnectionManager;
		createTriadsTable();
	
	}
	
	/**
	 * Crea la tabla triads en la base de datos del cliente
	 * esta tabla contiene 3 clumnas (source, destiny, content)
	 * las llaves para acceder a los elementos de esta tabla son (source, destiny)
	 * @throws IOException
	 * @throws SQLException
	 */
	private void createTriadsTable() throws IOException, SQLException {
		String sql = "CREATE TABLE if not exists triads (" + 
			"  source VARCHAR(255) NOT NULL," + 
			"  destiny VARCHAR(255) NOT NULL," + 
			"  content VARCHAR(255)," +
			"	 PRIMARY KEY (source, destiny)" +
			")";

		Statement statement = dbConnectionManager.getConnection().createStatement();
		statement.execute(sql);
	}

	/**
	 * Agrega un Triad en la tabla
	 * no se permite agregar triads repetidos
	 * @param triad Triad a agegar en la tabla
	 */
	public synchronized void insertTriad(Triad triad) {
			String sql = "INSERT INTO triads (source, destiny, content) VALUES (?,?,?)";
			try {
					PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(sql);
					
				if (dbConnectionManager.getConnection() != null) {				
					preparedStatement.setString(1, triad.getSource());
					preparedStatement.setString(2, triad.getCible());
					preparedStatement.setString(3, triad.getMot());
					preparedStatement.executeUpdate();
				}
//				counter++;
//				System.out.println(counter + " Treads");
			} catch (SQLException e) {
//				System.out.println(e.getMessage());
			}
	}
	
//	ESTO LO DEBE HACER UNA CLASE DBTriadrEADER
	/**
	 * Devuelve la tabla en forma de HashSet de Triad
	 * @return HashSet de Triad
	 * @throws SQLException
	 */
	/*
	public HashSet<Triad> getTable() throws SQLException {
		HashSet<Triad> table = new HashSet<>();

		String sql = "SELECT source, destiny, content FROM triads";
        Statement statement  = this.dbConnectionManager.getConnection().createStatement();
        ResultSet resultSet    = statement.executeQuery(sql);

        while (resultSet.next()) {
        	table.add( new Triad( resultSet.getString("source"),
        							resultSet.getString("destiny"),
        							resultSet.getString("content")
        						));
        }
		return table;
    }
    */

}

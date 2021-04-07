package hits.core;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase encargada de escribir en la base de datos del cliente la
 * tabla que contiene todas las Paginas resultantes con sus clasificaciones
 * luego de implementer el algoritmo Hits
 * @author ujarky
 */
public class DBHitsWriter {
	
	//Objeto DBConnectionManager para comunicarse con la bese de datos
	private DBConnectionManager dbConnectionManager ;
	
	/**
	 * Obtiene el DBConnectionManager
	 * y crea la tabla hits_results para guardar la informacion
	 * @param dbConnectionManager
	 * @throws IOException
	 * @throws SQLException
	 */
	public DBHitsWriter(DBConnectionManager dbConnectionManager) throws IOException, SQLException {
		this.dbConnectionManager = dbConnectionManager;
		createHits_resultsTable();
	}
	
	/**
	 * Crea la tabla hits_results en la base de datos del cliente
	 * esta tabla contiene 3 clumnas (url, authority, hub)
	 * cada fila represente una pagina web
	 * la llave para acceder a los elementos de esta tabla es url
	 * @throws IOException
	 * @throws SQLException
	 */
	public void createHits_resultsTable() throws IOException, SQLException {
		String sql = "CREATE TABLE if not exists hits_results (" + 
			"  url VARCHAR(255) NOT NULL," + 
			"  authority REAL," + 
			"  hub REAL," +
			"  PRIMARY KEY (url)" +
			")";

		Statement statement = dbConnectionManager.getConnection().createStatement();
		statement.execute(sql);
	}
	
	/**
	 * Agrega una pagina en la tabla hits_results
	 * no se permite agregar paginas repetidas
	 * @param page Page a agegar en la tabla
	 */
	public boolean insertPage(Page page) {
		String sql = "INSERT INTO hits_results (url, authority, hub) VALUES (?,?,?)";
		try {
				PreparedStatement preparedStatement = dbConnectionManager.getConnection().prepareStatement(sql);
		
			if (dbConnectionManager.getConnection() != null) {				
				preparedStatement.setString(1, page.getUrl());
				preparedStatement.setDouble(2, page.getAuth());
				preparedStatement.setDouble(3, page.getHub());
				preparedStatement.executeUpdate();
			}
			return true; 

		} catch (SQLException e) {
//			System.out.println(e.getMessage());
		}
	return false;
	}
	
	/**
	 * Devuelve un HashSet de Page con todas las paginas de la tabla
	 * @return HashSet de Page con todas las paginas de la tabla
	 * @throws SQLException
	 */
	/*
	public HashSet<Page> getTable() throws SQLException {
		HashSet<Page> table = new HashSet<>();
		
		String sql = "SELECT url, authority, hub FROM hits_results";
        Statement statement  = this.dbConnectionManager.getConnection().createStatement();
        ResultSet resultSet    = statement.executeQuery(sql);
        
        while (resultSet.next()) {
        	Page page = new Page(resultSet.getString("url"));
        	page.setAuth(resultSet.getDouble("authority"));
        	page.setHub(resultSet.getDouble("hub"));
        	table.add(page);
        }
		return table;
    }
*/
}

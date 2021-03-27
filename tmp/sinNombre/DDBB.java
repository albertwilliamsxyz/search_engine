package sinNombre;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

/**
 * 
 * Clase encargada de hacer la connecion con la base de datos
 * @author ujarky
 *
 */
public class DDBB {
	
	//Nombre de la carpeta contenedora de la base de datos
	public static final String DDBB_FOLDER_NAME = "DDBB";
	
	//Nombre de la base de datos principal
	public static final String DDBB_TRIADS_NAME = "Data";
	
	//Nombre de la tabla de trios
	public static final String TRIADS_TABLE_NAME = "triads";

	
	//conexion de la base de datos
	private Connection connection;
	

	/**
	 * Constructor, crea la carpeta DDBB en el directorio del programa si esta no existe
	 */
	public DDBB(){
		File dir = new File("DDBB");
		if(!dir.exists()) {
			dir.mkdir();
			System.out.println("Data base Folder created");
		}
		connection = null;
	}

	

	/**
	 * Construye una URL valida para la creacion de una base de datos
	 * @param DDBBName String nombre de la base de datos a crear
	 * @return String con la url
	 * @throws IOException
	 */
	public static String buildDatabaseURL(String DDBBName) throws IOException {
		if(!DDBBName.endsWith(".db"))
			DDBBName += ".db";
		String currentDirectory = new File(DDBB_FOLDER_NAME).getCanonicalPath();
		String url = "jdbc:sqlite:" + currentDirectory  +"/"+ DDBBName;
		
		return url;
	}
	

	/**
	 * Crea una conexion con una base de datos
	 * @param DDBBName nombre de la base de datos a crear
	 * @return Boolean true si se logro hacer la conexion
	 */
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
	
	/**
	 * Crea una conexion con la base de datos Data
	 * @return Boolean true si se logro hacer la conexion
	 */
	public boolean connection() {
		return connection(DDBB_TRIADS_NAME);
	}
	

	/**
	 * Cierra la conexion con la base de datos
	 */
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
	
	/**
	 * verifica si la conexion ha sido cerrada
	 * @return Boolean true si la conexion ha sido cerrada
	 */
	public boolean isClosed() {
		return connection == null;
	}

	
	/**
	 * Crea la tabla de triplets en la base de datos
	 * @return Boolean true si la tabla fue creada o ya existia
	 */
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
	
	
	/**
	 * Inserta un tripet en la tabla de triplets
	 * @param source URL source
	 * @param destiny URL cible
	 * @param content mot cliquable
	 * @return
	 */
	public boolean insertTriad( Triad triad) {
		if (connection != null) {
			try {
				String sql = "INSERT INTO "+ TRIADS_TABLE_NAME +" (source, destiny, content) VALUES (?,?,?)";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
	
				preparedStatement.setString(1, triad.getSource());
				preparedStatement.setString(2, triad.getCible());
				preparedStatement.setString(3, triad.getMot());
				preparedStatement.executeUpdate();
				System.out.println("elementos incertados en la table: " + triad.getSource() + "\t" + triad.getCible() +"\t" +  triad.getMot() );

				return true;
			} catch (SQLException e) {
				 e.printStackTrace();
				System.out.println("**************** Duplicated element **************");
			}
		}
		return false;
	}
	
	
	
	public void selectAll(){
        String sql = "SELECT source, destiny, content FROM "+ TRIADS_TABLE_NAME;
        
        connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" + 
                                   rs.getString("name") + "\t" +
                                   rs.getDouble("capacity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	public HashSet<Triad>  getTriadTable() {
		return null;
	}

}

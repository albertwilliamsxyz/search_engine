package search_engine;

import java.io.IOException;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

// TODO: Crear builder, de tal forma de que tenga contenido en la clase todo lo que necesito (osease, el URL nada mas)

class DatabaseConnectionManager {
	public static String buildDatabaseURL(String databaseName) throws IOException {
		String currentDirectory = new File(".").getCanonicalPath();
		String url = "jdbc:sqlite:" + currentDirectory + "/" + databaseName;
		return url;
	}
}

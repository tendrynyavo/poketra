package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionToDatabase {
    public static String user = "postgres";
    public static String password = "changetheworld";

    public static Connection getConnection() throws Exception {
            Connection connectionPostgreSQL = null;
            String postgreSQL = "jdbc:postgresql://localhost:5432/poketra";

            connectionPostgreSQL = DriverManager.getConnection(postgreSQL, user, password);
            
            connectionPostgreSQL.setAutoCommit(false);

        return connectionPostgreSQL;
    }
}
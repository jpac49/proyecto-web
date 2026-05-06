import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDatos {

    public static Connection getConexion(String rutaBD) throws SQLException {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver UCanAccess", e);
        }

        String url = "jdbc:ucanaccess://" + rutaBD + ";memory=false";
        return DriverManager.getConnection(url);
    }
}
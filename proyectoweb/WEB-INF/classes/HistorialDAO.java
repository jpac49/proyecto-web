import java.sql.*;
import java.util.ArrayList;

public class HistorialDAO {

    /**
     * Inserta un nuevo registro en el historial. 
     * Se mantiene igual, ya que el precio real se calcula en el Servlet antes de llamar a este método.
     */
    public static void insertar(
        String rutaBD,
        String nombre,
        int numPlantas,
        double nivelPrimeraPlanta,
        double nivelUltimaPlanta,
        double precioTotal,
        int idCable,
        int idDistribuidor,
        int idToma,
        String idsDerivadores,
        double cabecera,
        double distPlantas,
        double distDerDist,
        double distDistToma
    ) throws Exception {

        Connection conexion = null;
        PreparedStatement ps = null;

        try {
            conexion = BaseDatos.getConexion(rutaBD);

            String sql = "INSERT INTO [Historial] " +
                "([Nombre], [NumPlantas], [NivelPrimeraPlanta], [NivelUltimaPlanta], [PrecioTotal], [Fecha], " +
                "[IdCable], [IdDistribuidor], [IdToma], [IdsDerivadores], " +
                "[Cabecera], [DistPlantas], [DistDerDist], [DistDistToma]) " +
                "VALUES (?, ?, ?, ?, ?, Now(), ?, ?, ?, ?, ?, ?, ?, ?)";

            ps = conexion.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setInt(2, numPlantas);
            ps.setDouble(3, nivelPrimeraPlanta);
            ps.setDouble(4, nivelUltimaPlanta);
            ps.setDouble(5, precioTotal);
            ps.setInt(6, idCable);
            ps.setInt(7, idDistribuidor);
            ps.setInt(8, idToma);
            ps.setString(9, idsDerivadores);
            ps.setDouble(10, cabecera);
            ps.setDouble(11, distPlantas);
            ps.setDouble(12, distDerDist);
            ps.setDouble(13, distDistToma);

            ps.executeUpdate();

        } finally {
            if (ps != null) ps.close();
            if (conexion != null) conexion.close();
        }
    }

    /**
     * Lista el historial vinculando (JOIN) con la tabla Componentes 
     * para obtener los nombres de los modelos en lugar de solo los IDs.
     */
    public static ArrayList<HistorialEntrada> listar(String rutaBD) throws Exception {

        ArrayList<HistorialEntrada> lista = new ArrayList<HistorialEntrada>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = BaseDatos.getConexion(rutaBD);

            // Consulta con TRIPLE JOIN para traer los nombres de serie de los 3 componentes principales
            String sql = "SELECT H.*, " +
                         "C.modelo AS NomCable, " +
                         "D.modelo AS NomDist, " +
                         "T.modelo AS NomToma " +
                         "FROM (([Historial] H " +
                         "INNER JOIN [Componentes] C ON H.IdCable = C.Id) " +
                         "INNER JOIN [Componentes] D ON H.IdDistribuidor = D.Id) " +
                         "INNER JOIN [Componentes] T ON H.IdToma = T.Id " +
                         "ORDER BY H.Fecha DESC";

            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                HistorialEntrada h = new HistorialEntrada();
                h.id                 = rs.getInt("Id");
                h.nombre             = rs.getString("Nombre");
                h.numPlantas         = rs.getInt("NumPlantas");
                h.nivelPrimeraPlanta = rs.getDouble("NivelPrimeraPlanta");
                h.nivelUltimaPlanta  = rs.getDouble("NivelUltimaPlanta");
                h.precioTotal        = rs.getDouble("PrecioTotal");
                h.fecha              = rs.getString("Fecha");
                h.idCable            = rs.getInt("IdCable");
                h.idDistribuidor     = rs.getInt("IdDistribuidor");
                h.idToma             = rs.getInt("IdToma");
                h.idsDerivadores     = rs.getString("IdsDerivadores");
                h.cabecera           = rs.getDouble("Cabecera");
                h.distPlantas        = rs.getDouble("DistPlantas");
                h.distDerDist        = rs.getDouble("DistDerDist");
                h.distDistToma       = rs.getDouble("DistDistToma");

                // NUEVOS: Guardamos los nombres recuperados del JOIN en el objeto
                h.modeloCable        = rs.getString("NomCable");
                h.modeloDistribuidor = rs.getString("NomDist");
                h.modeloToma         = rs.getString("NomToma");

                lista.add(h);
            }

        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conexion != null) conexion.close();
        }

        return lista;
    }

    public static int contarEntradas(String rutaBD) throws Exception {
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;

        try {
            conexion = BaseDatos.getConexion(rutaBD);
            ps = conexion.prepareStatement("SELECT COUNT(*) FROM [Historial]");
            rs = ps.executeQuery();
            if (rs.next()) count = rs.getInt(1);
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conexion != null) conexion.close();
        }

        return count;
    }
    public static HistorialEntrada obtenerPorId(String rutaBD, int idBuscado) throws Exception {
        ArrayList<HistorialEntrada> todos = listar(rutaBD);

        for (HistorialEntrada h : todos) {
            if (h.id == idBuscado) {
                return h;
            }
        }

        return null;
    }
}
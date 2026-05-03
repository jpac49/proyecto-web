import java.sql.*;
import java.util.ArrayList;

public class ComponenteDAO {

    public static void insertar(
        String rutaBD,
        String tipo,
        String modelo,
        String precioTexto,
        String at470Texto,
        String at862Texto,
        String atPasoTexto
    ) throws Exception {

        Connection conexion = null;
        PreparedStatement ps = null;

        try {
            conexion = BaseDatos.getConexion(rutaBD);

            String sql = "INSERT INTO [Componentes] ([tipo], [modelo], [precio], [at470], [at862], [atPaso]) VALUES (?, ?, ?, ?, ?, ?)";

            ps = conexion.prepareStatement(sql);

            ps.setString(1, tipo);
            ps.setString(2, modelo);

            ponerDoubleONull(ps, 3, precioTexto);
            ponerDoubleONull(ps, 4, at470Texto);
            ponerDoubleONull(ps, 5, at862Texto);
            ponerDoubleONull(ps, 6, atPasoTexto);

            ps.executeUpdate();

        } finally {
            if (ps != null) {
                ps.close();
            }

            if (conexion != null) {
                conexion.close();
            }
        }
    }

    public static ArrayList<Componente> listar(String rutaBD) throws Exception {

        ArrayList<Componente> lista = new ArrayList<Componente>();

        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = BaseDatos.getConexion(rutaBD);

            String sql = "SELECT [Id], [tipo], [modelo], [precio], [at470], [at862], [atPaso] FROM [Componentes] ORDER BY [Id] DESC";

            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Id");
                String tipo = rs.getString("tipo");
                String modelo = rs.getString("modelo");

                Double precio = leerDouble(rs, "precio");
                Double at470 = leerDouble(rs, "at470");
                Double at862 = leerDouble(rs, "at862");
                Double atPaso = leerDouble(rs, "atPaso");

                Componente componente = new Componente(id, tipo, modelo, precio, at470, at862, atPaso);
                lista.add(componente);
            }

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (ps != null) {
                ps.close();
            }

            if (conexion != null) {
                conexion.close();
            }
        }

        return lista;
    }

    public static void eliminar(String rutaBD, int id) throws Exception {

        Connection conexion = null;
        PreparedStatement ps = null;

        try {
            conexion = BaseDatos.getConexion(rutaBD);

            String sql = "DELETE FROM [Componentes] WHERE [Id] = ?";

            ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();

        } finally {
            if (ps != null) {
                ps.close();
            }

            if (conexion != null) {
                conexion.close();
            }
        }
    }

    private static void ponerDoubleONull(PreparedStatement ps, int posicion, String texto)
        throws SQLException {

        if (texto == null || texto.trim().equals("")) {
            ps.setNull(posicion, Types.DOUBLE);
        } else {
            texto = texto.replace(",", ".");
            double valor = Double.parseDouble(texto);
            ps.setDouble(posicion, valor);
        }
    }

    private static Double leerDouble(ResultSet rs, String campo) throws SQLException {
        double valor = rs.getDouble(campo);

        if (rs.wasNull()) {
            return null;
        }

        return valor;
    }
}
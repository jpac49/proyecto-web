import java.sql.*;
import java.util.ArrayList;

public class ComponenteDAO {

    public static void insertar(
        String rutaBD, String tipo, String modelo, String precioTexto,
        String at470Texto, String at862Texto, String atPasoTexto,
        String atDerivacionTexto, String numSalidasTexto, String atSalidaTexto
    ) throws Exception {

        Connection conexion = null;
        PreparedStatement psComp = null;
        PreparedStatement psDetalle = null;
        ResultSet rsId = null;

        try {
            conexion = BaseDatos.getConexion(rutaBD);
            conexion.setAutoCommit(false);

            String sqlComp = "INSERT INTO [Componentes] ([tipo], [modelo], [precio]) VALUES (?, ?, ?)";
            psComp = conexion.prepareStatement(sqlComp, Statement.RETURN_GENERATED_KEYS);
            psComp.setString(1, tipo);
            psComp.setString(2, modelo);
            ponerDoubleONull(psComp, 3, precioTexto);
            psComp.executeUpdate();

            rsId = psComp.getGeneratedKeys();
            int nuevoId = 0;
            if (rsId.next()) { nuevoId = rsId.getInt(1); }

            String sqlDetalle = "";
            if (tipo.equals("Cable Coaxial")) {
                sqlDetalle = "INSERT INTO [Datos_Cables] ([Id_Componente], [At470], [At862]) VALUES (?, ?, ?)";
                psDetalle = conexion.prepareStatement(sqlDetalle);
                psDetalle.setInt(1, nuevoId);
                ponerDoubleONull(psDetalle, 2, at470Texto);
                ponerDoubleONull(psDetalle, 3, at862Texto);
            } else if (tipo.equals("Toma")) {
                sqlDetalle = "INSERT INTO [Datos_Tomas] ([Id_Componente], [AtDerivacion]) VALUES (?, ?)";
                psDetalle = conexion.prepareStatement(sqlDetalle);
                psDetalle.setInt(1, nuevoId);
                ponerDoubleONull(psDetalle, 2, atDerivacionTexto);
            } else if (tipo.equals("Derivador")) {
                sqlDetalle = "INSERT INTO [Datos_Derivadores] ([Id_Componente], [NumSalidas], [AtSalida], [AtPaso]) VALUES (?, ?, ?, ?)";
                psDetalle = conexion.prepareStatement(sqlDetalle);
                psDetalle.setInt(1, nuevoId);
                ponerIntONull(psDetalle, 2, numSalidasTexto);
                ponerDoubleONull(psDetalle, 3, atSalidaTexto);
                ponerDoubleONull(psDetalle, 4, atPasoTexto);
            } else if (tipo.equals("Distribuidor")) {
                sqlDetalle = "INSERT INTO [Datos_Distribucion] ([Id_Componente], [NumSalidas], [AtSalida]) VALUES (?, ?, ?)";
                psDetalle = conexion.prepareStatement(sqlDetalle);
                psDetalle.setInt(1, nuevoId);
                ponerIntONull(psDetalle, 2, numSalidasTexto);
                ponerDoubleONull(psDetalle, 3, atSalidaTexto);
            }

            if (psDetalle != null) { psDetalle.executeUpdate(); }
            conexion.commit();

        } catch (Exception e) {
            if (conexion != null) conexion.rollback();
            throw e;
        } finally {
            if (rsId != null) rsId.close();
            if (psComp != null) psComp.close();
            if (psDetalle != null) psDetalle.close();
            if (conexion != null) conexion.close();
        }
    }

    public static ArrayList<Componente> listar(String rutaBD) throws Exception {
        ArrayList<Componente> lista = new ArrayList<Componente>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = BaseDatos.getConexion(rutaBD);
            String sql = "SELECT C.*, Ca.At470, Ca.At862, T.AtDerivacion, D.AtPaso, D.AtSalida as AtSalidaDeriv, " +
                         "Dist.AtSalida as AtSalidaDist, D.NumSalidas as NumSalDeriv, Dist.NumSalidas as NumSalDist " +
                         "FROM (([Componentes] C " +
                         "LEFT JOIN [Datos_Cables] Ca ON C.Id = Ca.Id_Componente) " +
                         "LEFT JOIN [Datos_Tomas] T ON C.Id = T.Id_Componente) " +
                         "LEFT JOIN [Datos_Derivadores] D ON C.Id = D.Id_Componente " +
                         "LEFT JOIN [Datos_Distribucion] Dist ON C.Id = Dist.Id_Componente " +
                         "ORDER BY C.Id DESC";

            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                
                // Variables específicas inicializadas a null
                Double at470 = leerDouble(rs, "At470");
                Double at862 = leerDouble(rs, "At862");
                Double atPaso = leerDouble(rs, "AtPaso");
                Double atDeriv = leerDouble(rs, "AtDerivacion");
                Integer numSal = null;
                Double atSal = null;

                // Lógica de asignación limpia por tipo
                if ("Derivador".equals(tipo)) {
                    numSal = leerInt(rs, "NumSalDeriv");
                    atSal = leerDouble(rs, "AtSalidaDeriv");
                } else if ("Distribuidor".equals(tipo)) {
                    numSal = leerInt(rs, "NumSalDist");
                    atSal = leerDouble(rs, "AtSalidaDist");
                }

                Componente comp = new Componente(
                    rs.getInt("Id"), tipo, rs.getString("modelo"), leerDouble(rs, "precio"),
                    at470, at862, atPaso, atDeriv, numSal, atSal
                );
                lista.add(comp);
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conexion != null) conexion.close();
        }
        return lista;
    }

    // --- MÉTODOS AUXILIARES ---

    private static void ponerDoubleONull(PreparedStatement ps, int posicion, String texto) throws SQLException {
        if (texto == null || texto.trim().equals("")) {
            ps.setNull(posicion, Types.DOUBLE);
        } else {
            ps.setDouble(posicion, Double.parseDouble(texto.replace(",", ".")));
        }
    }

    private static void ponerIntONull(PreparedStatement ps, int posicion, String texto) throws SQLException {
        if (texto == null || texto.trim().equals("")) {
            ps.setNull(posicion, Types.INTEGER);
        } else {
            ps.setInt(posicion, Integer.parseInt(texto.trim()));
        }
    }

    private static Double leerDouble(ResultSet rs, String campo) throws SQLException {
        double valor = rs.getDouble(campo);
        return rs.wasNull() ? null : valor;
    }

    private static Integer leerInt(ResultSet rs, String campo) throws SQLException {
        int valor = rs.getInt(campo);
        return rs.wasNull() ? null : valor;
    }

    public static void eliminar(String rutaBD, int id) throws Exception {
        Connection conexion = null;
        try {
            conexion = BaseDatos.getConexion(rutaBD);
            conexion.setAutoCommit(false);

            String[] tablasDetalle = {"Datos_Cables", "Datos_Tomas", "Datos_Derivadores", "Datos_Distribucion"};
            for (String tabla : tablasDetalle) {
                try (PreparedStatement ps = conexion.prepareStatement("DELETE FROM [" + tabla + "] WHERE Id_Componente = ?")) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }
            }

            try (PreparedStatement ps = conexion.prepareStatement("DELETE FROM [Componentes] WHERE Id = ?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
            conexion.commit();
        } catch (Exception e) {
            if (conexion != null) conexion.rollback();
            throw e;
        } finally {
            if (conexion != null) conexion.close();
        }
    }
    
    public static Componente obtenerPorId(String rutaBD, int id) throws Exception {
        ArrayList<Componente> todos = listar(rutaBD);
        for(Componente c : todos) {
            if(c.getId() == id) return c;
        }
        return null;
    }
    
    public static ArrayList<Componente> listarPorTipo(String rutaBD, String tipoBuscado) {
        ArrayList<Componente> filtrados = new ArrayList<>();
        try {
            // Aprovechamos el método listar que YA saca todos los datos correctamente de todas las tablas
            ArrayList<Componente> todos = listar(rutaBD);
            
            for (Componente c : todos) {
                if (c.getTipo().equalsIgnoreCase(tipoBuscado)) {
                    filtrados.add(c);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al filtrar por tipo: " + e.getMessage());
            e.printStackTrace();
        }
        return filtrados;
    }

    // Método auxiliar para evitar errores de PreparedStatement si no lo tenías
    private static PreparedStatement psConParametro(Connection con, String sql, String param) throws Exception {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, param);
        return ps;
    }
}
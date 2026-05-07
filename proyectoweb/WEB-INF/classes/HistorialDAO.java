import java.sql.*;
import java.util.ArrayList;

public class HistorialDAO {

    // ─────────────────────────────────────────────────────────────────
    // Guarda un nuevo edificio en la tabla Historial de la base de datos.
    // Lo llama GuardarEdificio.java cuando el usuario pulsa "Guardar Edificio".
    // ─────────────────────────────────────────────────────────────────
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
            // Abre la conexión a la base de datos Access
            conexion = BaseDatos.getConexion(rutaBD);

            // Los '?' son marcadores de posición que se rellenan justo debajo.
            // Usar '?' en vez de concatenar strings evita errores e inyecciones SQL.
            // 'Now()' es una función de Access que inserta la fecha y hora actual
            // automáticamente, por eso no tiene '?' ni se pasa como parámetro.
            String sql = "INSERT INTO [Historial] " +
                "([Nombre], [NumPlantas], [NivelPrimeraPlanta], [NivelUltimaPlanta], [PrecioTotal], [Fecha], " +
                "[IdCable], [IdDistribuidor], [IdToma], [IdsDerivadores], " +
                "[Cabecera], [DistPlantas], [DistDerDist], [DistDistToma]) " +
                "VALUES (?, ?, ?, ?, ?, Now(), ?, ?, ?, ?, ?, ?, ?, ?)";

            ps = conexion.prepareStatement(sql);

            // Cada línea rellena un '?' en orden. El número indica la posición
            // (el 6º '?' lo ocupa Now() automáticamente, por eso aquí saltamos del 5 al 6)
            ps.setString(1, nombre);
            ps.setInt(2, numPlantas);
            ps.setDouble(3, nivelPrimeraPlanta);
            ps.setDouble(4, nivelUltimaPlanta);
            ps.setDouble(5, precioTotal);
            ps.setInt(6, idCable);
            ps.setInt(7, idDistribuidor);
            ps.setInt(8, idToma);
            ps.setString(9, idsDerivadores);   // Ej: "3,5" si se usaron dos derivadores
            ps.setDouble(10, cabecera);
            ps.setDouble(11, distPlantas);
            ps.setDouble(12, distDerDist);
            ps.setDouble(13, distDistToma);

            // Ejecuta el INSERT en la base de datos
            ps.executeUpdate();

        } finally {
            // El bloque finally se ejecuta siempre, haya error o no.
            // Es importante cerrar siempre la conexión para no dejar recursos abiertos.
            if (ps != null) ps.close();
            if (conexion != null) conexion.close();
        }
    }

    // ─────────────────────────────────────────────────────────────────
    // Devuelve todos los edificios guardados como una lista de objetos
    // HistorialEntrada. Lo llama HistorialServlet.java para mostrar la tabla.
    // ─────────────────────────────────────────────────────────────────
    public static ArrayList<HistorialEntrada> listar(String rutaBD) throws Exception {

        ArrayList<HistorialEntrada> lista = new ArrayList<HistorialEntrada>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = BaseDatos.getConexion(rutaBD);

            // H.* significa "trae todas las columnas de la tabla Historial".
            // Además trae el campo 'modelo' de la tabla Componentes tres veces,
            // una por cada componente principal (cable, distribuidor, toma),
            // usando alias (AS) para distinguirlos: NomCable, NomDist, NomToma.
            // Sin este JOIN habría que hacer consultas extra para obtener los nombres.
            //
            // Los INNER JOIN unen Historial con Componentes tres veces con alias distintos
            // (C, D, T). La condición ON indica cómo emparejar las filas:
            // ej: H.IdCable = C.Id → busca en Componentes el registro cuyo Id
            // coincida con el IdCable guardado en el historial.
            // Los paréntesis anidados son obligatorios en Access para encadenar varios JOINs.
            //
            // ORDER BY H.Fecha DESC → ordena del más reciente al más antiguo.
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

            // rs contiene el resultado de la consulta, como una tabla temporal en memoria
            rs = ps.executeQuery();

            // Cada llamada a rs.next() avanza una fila. Cuando no quedan más, el bucle termina.
            while (rs.next()) {
                // Creamos un objeto vacío y rellenamos cada campo con el valor
                // de la columna correspondiente usando getInt, getString o getDouble
                // según el tipo de dato de cada columna.
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
                h.idsDerivadores     = rs.getString("IdsDerivadores"); // Ej: "3,5"
                h.cabecera           = rs.getDouble("Cabecera");
                h.distPlantas        = rs.getDouble("DistPlantas");
                h.distDerDist        = rs.getDouble("DistDerDist");
                h.distDistToma       = rs.getDouble("DistDistToma");
                // Estos tres vienen del JOIN, no de la tabla Historial directamente
                h.modeloCable        = rs.getString("NomCable");
                h.modeloDistribuidor = rs.getString("NomDist");
                h.modeloToma         = rs.getString("NomToma");

                // Añadimos el objeto relleno a la lista
                lista.add(h);
            }

        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conexion != null) conexion.close();
        }

        return lista;
    }

    // ─────────────────────────────────────────────────────────────────
    // Devuelve el número total de edificios guardados en el historial.
    // Lo usa EjecutarCalculo.java para generar el nombre por defecto
    // del edificio, ej: "Edificio 4" si ya hay 3 guardados.
    // ─────────────────────────────────────────────────────────────────
    public static int contarEntradas(String rutaBD) throws Exception {
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;

        try {
            conexion = BaseDatos.getConexion(rutaBD);
            // COUNT(*) es una función SQL que devuelve el número de filas de la tabla
            ps = conexion.prepareStatement("SELECT COUNT(*) FROM [Historial]");
            rs = ps.executeQuery();
            // El resultado es una sola fila con una sola columna, por eso rs.getInt(1)
            if (rs.next()) count = rs.getInt(1);
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conexion != null) conexion.close();
        }

        return count;
    }

    // ─────────────────────────────────────────────────────────────────
    // Busca y devuelve un único edificio del historial por su ID.
    // Lo usa VerGraficoHistorial.java cuando el usuario pulsa "Ver gráfico".
    // En vez de hacer una consulta nueva, reutiliza listar() y filtra el resultado.
    // ─────────────────────────────────────────────────────────────────
    public static HistorialEntrada obtenerPorId(String rutaBD, int idBuscado) throws Exception {
        ArrayList<HistorialEntrada> todos = listar(rutaBD);
        for (HistorialEntrada h : todos) {
            if (h.id == idBuscado) return h; // Devuelve en cuanto lo encuentra
        }
        return null; // Si no existe ninguno con ese ID, devuelve null
    }

    // ─────────────────────────────────────────────────────────────────
    // Elimina un edificio del historial por su ID.
    // Lo llama EliminarHistorial.java cuando el usuario pulsa "Eliminar".
    // ─────────────────────────────────────────────────────────────────
    public static void eliminar(String rutaBD, int id) throws Exception {
        Connection conexion = null;
        PreparedStatement ps = null;

        try {
            conexion = BaseDatos.getConexion(rutaBD);
            // El '?' se sustituye por el id en la línea siguiente
            ps = conexion.prepareStatement("DELETE FROM [Historial] WHERE [Id] = ?");
            ps.setInt(1, id);
            ps.executeUpdate(); // Ejecuta el DELETE
        } finally {
            if (ps != null) ps.close();
            if (conexion != null) conexion.close();
        }
    }
}
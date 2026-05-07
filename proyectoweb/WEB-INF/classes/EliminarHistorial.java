import java.io.*;
import java.net.URLEncoder;
import javax.servlet.*;
import javax.servlet.http.*;

// 'extends HttpServlet' hace que esta clase funcione como servlet en Tomcat,
// heredando toda la capacidad de recibir y responder peticiones web.
public class EliminarHistorial extends HttpServlet {

    // doGet se activa automáticamente cuando el navegador hace una petición GET,
    // es decir, cuando el usuario pulsa el enlace "Eliminar" en la tabla del historial.
    // La URL que lo activa sería algo como: eliminarHistorial?id=3
    // 'throws ServletException, IOException' delega la gestión de errores graves a Tomcat.
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // Indica a Tomcat que los parámetros que llegan pueden contener
        // caracteres especiales como tildes o ñ
        request.setCharacterEncoding("UTF-8");

        // Obtiene la ruta física del archivo .accdb dentro del servidor
        String rutaBD = getServletContext().getRealPath("/WEB-INF/bd/SimuladorICT.accdb");

        try {
            // El id llega como String desde la URL (ej: "3"), hay que convertirlo
            // a entero con parseInt() antes de pasárselo al DAO
            int id = Integer.parseInt(request.getParameter("id"));

            // Llama al DAO para que ejecute el DELETE en la base de datos
            HistorialDAO.eliminar(rutaBD, id);

            // URLEncoder.encode() convierte el mensaje a formato seguro para URLs,
            // por ejemplo los espacios se convierten en '+' y las tildes en %C3%B3, etc.
            // Sin esto, la URL se rompería con caracteres especiales.
            String msg = URLEncoder.encode("Edificio eliminado correctamente.", "UTF-8");

            // Redirige al usuario de vuelta al historial mostrando el mensaje de éxito
            response.sendRedirect("historial?mensaje=" + msg);

        } catch (Exception e) {
            // Si algo falla (ej: id inválido, error de BD), redirige igualmente
            // al historial pero con un mensaje de error
            String msg = URLEncoder.encode("Error al eliminar: " + e.getMessage(), "UTF-8");
            response.sendRedirect("historial?mensaje=" + msg);
        }
    }
}
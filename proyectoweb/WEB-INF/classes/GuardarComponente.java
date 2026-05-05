import java.io.*;
import java.net.URLEncoder;
import javax.servlet.*;
import javax.servlet.http.*;

public class GuardarComponente extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Datos generales
        String tipo = request.getParameter("tipo");
        String modelo = request.getParameter("modelo");
        String precio = request.getParameter("precio");

        // Datos específicos (recogemos todos los posibles del formulario)
        String at470 = request.getParameter("at470");
        String at862 = request.getParameter("at862");
        String atPaso = request.getParameter("atPaso");
        String atDerivacion = request.getParameter("atDerivacion");
        String numSalidas = request.getParameter("numSalidas");
        String atSalida = request.getParameter("atSalida");

        String rutaBD = getServletContext().getRealPath("/WEB-INF/bd/SimuladorICT.accdb");

        if ("Distribuidor".equals(tipo)) {
        numSalidas = request.getParameter("numSalidasDist");
        atSalida = request.getParameter("atSalidaDist");
        }

        try {
            // Llamamos al DAO con la nueva firma que incluye todos los parámetros
            ComponenteDAO.insertar(
                rutaBD, 
                tipo, 
                modelo, 
                precio, 
                at470, 
                at862, 
                atPaso, 
                atDerivacion, 
                numSalidas, 
                atSalida
            );

            String mensaje = URLEncoder.encode("Componente guardado correctamente", "UTF-8");
            response.sendRedirect("componentes?mensaje=" + mensaje);

        } catch (Exception e) {
            // Imprimimos el error en la consola del servidor para poder depurar
            e.printStackTrace(); 
            String mensaje = URLEncoder.encode("Error al guardar: " + e.getMessage(), "UTF-8");
            response.sendRedirect("componentes?mensaje=" + mensaje);
        }
    }
}
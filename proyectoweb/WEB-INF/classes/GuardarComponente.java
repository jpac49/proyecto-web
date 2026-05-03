import java.io.*;
import java.net.URLEncoder;
import javax.servlet.*;
import javax.servlet.http.*;

public class GuardarComponente extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String tipo = request.getParameter("tipo");
        String modelo = request.getParameter("modelo");
        String precio = request.getParameter("precio");
        String at470 = request.getParameter("at470");
        String at862 = request.getParameter("at862");
        String atPaso = request.getParameter("atPaso");

        String rutaBD = getServletContext().getRealPath("/WEB-INF/bd/SimuladorICT.accdb");

        try {
            ComponenteDAO.insertar(rutaBD, tipo, modelo, precio, at470, at862, atPaso);

            String mensaje = URLEncoder.encode("Componente guardado correctamente", "UTF-8");
            response.sendRedirect("componentes?mensaje=" + mensaje);

        } catch (Exception e) {
            String mensaje = URLEncoder.encode("Error al guardar: " + e.getMessage(), "UTF-8");
            response.sendRedirect("componentes?mensaje=" + mensaje);
        }
    }
}
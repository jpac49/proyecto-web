import java.io.*;
import java.net.URLEncoder;
import javax.servlet.*;
import javax.servlet.http.*;

public class EliminarComponente extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idTexto = request.getParameter("id");

        String rutaBD = getServletContext().getRealPath("/WEB-INF/bd/SimuladorICT.accdb");

        try {
            int id = Integer.parseInt(idTexto);

            ComponenteDAO.eliminar(rutaBD, id);

            String mensaje = URLEncoder.encode("Componente eliminado correctamente", "UTF-8");
            response.sendRedirect("componentes?mensaje=" + mensaje);

        } catch (Exception e) {
            String mensaje = URLEncoder.encode("Error al eliminar: " + e.getMessage(), "UTF-8");
            response.sendRedirect("componentes?mensaje=" + mensaje);
        }
    }
}
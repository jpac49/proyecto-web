import java.io.*;
import java.net.URLEncoder;
import javax.servlet.*;
import javax.servlet.http.*;

public class EliminarHistorial extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String rutaBD = getServletContext().getRealPath("/WEB-INF/bd/SimuladorICT.accdb");

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            HistorialDAO.eliminar(rutaBD, id);

            String msg = URLEncoder.encode("Edificio eliminado correctamente.", "UTF-8");
            response.sendRedirect("historial?mensaje=" + msg);

        } catch (Exception e) {
            String msg = URLEncoder.encode("Error al eliminar: " + e.getMessage(), "UTF-8");
            response.sendRedirect("historial?mensaje=" + msg);
        }
    }
}
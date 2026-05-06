import java.io.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;

public class HistorialServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String rutaBD = getServletContext().getRealPath("/WEB-INF/bd/SimuladorICT.accdb");

        ArrayList<HistorialEntrada> entradas = new ArrayList<HistorialEntrada>();
        String mensaje = request.getParameter("mensaje");

        try {
            entradas = HistorialDAO.listar(rutaBD);
        } catch (Exception e) {
            mensaje = "Error al cargar el historial: " + e.getMessage();
        }

        PrintWriter out = response.getWriter();
        out.println(HistorialHTML.generarPagina(entradas, mensaje, rutaBD));
    }
}

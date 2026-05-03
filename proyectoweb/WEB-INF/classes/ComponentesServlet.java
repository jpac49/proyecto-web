import java.io.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;

public class ComponentesServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        String rutaBD = getServletContext().getRealPath("/WEB-INF/bd/SimuladorICT.accdb");

        ArrayList<Componente> componentes = new ArrayList<Componente>();

        String mensaje = request.getParameter("mensaje");

        try {
            componentes = ComponenteDAO.listar(rutaBD);
        } catch (Exception e) {
            mensaje = "Error al leer la base de datos: " + e.getMessage();
        }

        String html = ComponentesHTML.generarPagina(componentes, mensaje);

        out.println(html);
    }
}
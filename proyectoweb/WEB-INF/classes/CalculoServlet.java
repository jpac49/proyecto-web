import java.io.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;

public class CalculoServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String rutaBD = getServletContext().getRealPath("/WEB-INF/bd/SimuladorICT.accdb");

        // Creamos listas vacías por seguridad
        ArrayList<Componente> cables = new ArrayList<Componente>();
        ArrayList<Componente> tomas = new ArrayList<Componente>();
        ArrayList<Componente> distribuidores = new ArrayList<Componente>();
        ArrayList<Componente> derivadores = new ArrayList<Componente>();

        try {
            // Usamos ComponenteDAO.listar() para traer todos
            ArrayList<Componente> todos = ComponenteDAO.listar(rutaBD);

            // Filtramos los componentes por tipo para rellenar cada selector del storyboard
            for (Componente c : todos) {
                if ("Cable Coaxial".equals(c.getTipo())) {
                    cables.add(c);
                } else if ("Toma".equals(c.getTipo())) { // Ajustado a 'Toma' como en ComponentesHTML.java
                    tomas.add(c);
                } else if ("Distribuidor".equals(c.getTipo())) {
                    distribuidores.add(c);
                } else if ("Derivador".equals(c.getTipo())) {
                    derivadores.add(c);
                }
            }

        } catch (Exception e) {
            // En producción, esto debería ir a un log
            System.err.println("Error al cargar componentes para cálculo: " + e.getMessage());
        }

        // Recuperamos el resultado del cálculo si venimos de ejecutarCalculo (explicado más abajo)
        Integer maxPlantasResult = null;
        if (request.getAttribute("maxPlantas") != null) {
            maxPlantasResult = (Integer) request.getAttribute("maxPlantas");
        }

        // Generamos el HTML dinámico
        String html = CalculoHTML.generarPagina(cables, tomas, distribuidores, derivadores, null, maxPlantasResult);

        out.println(html);
    }
}
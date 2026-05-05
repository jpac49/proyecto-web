import java.io.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;

public class CalculoServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // Configuración de cabeceras para evitar problemas con tildes y ñ
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String rutaBD = getServletContext().getRealPath("/WEB-INF/bd/SimuladorICT.accdb");

        ArrayList<Componente> cables = new ArrayList<>();
        ArrayList<Componente> tomas = new ArrayList<>();
        ArrayList<Componente> distribuidores = new ArrayList<>();
        ArrayList<Componente> derivadores = new ArrayList<>();

        try {
            // Traemos todos los componentes de la base de datos relacional
            ArrayList<Componente> todos = ComponenteDAO.listar(rutaBD);

            // Clasificación por tipo para los selectores del HTML
            for (Componente c : todos) {
                String tipo = (c.getTipo() != null) ? c.getTipo() : "";
                
                switch (tipo) {
                    case "Cable Coaxial":
                        cables.add(c);
                        break;
                    case "Toma":
                        tomas.add(c);
                        break;
                    case "Distribuidor":
                        distribuidores.add(c);
                        break;
                    case "Derivador":
                        derivadores.add(c);
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); // Para ver el error detallado en la consola de Tomcat
        }

        // Si venimos de un cálculo fallido o exitoso, recogemos el mensaje o resultado
        String mensaje = request.getParameter("mensaje");
        Integer maxPlantasResult = (Integer) request.getAttribute("maxPlantas");

        // Generamos la página pasándole las listas filtradas
        // Nota: Asegúrate de que el nombre de la clase en CalculoHTML.java coincida con la llamada
        String html = CalculoHTML.generarPagina(
            cables, 
            tomas, 
            distribuidores, 
            derivadores, 
            mensaje, 
            maxPlantasResult
        );

        out.println(html);
    }
}
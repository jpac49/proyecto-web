import java.io.*;
import java.net.URLEncoder;
import javax.servlet.*;
import javax.servlet.http.*;

public class GuardarEdificio extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String rutaBD = getServletContext().getRealPath("/WEB-INF/bd/SimuladorICT.accdb");

        try {
            String nombre           = request.getParameter("nombre");
            int    numPlantas       = Integer.parseInt(request.getParameter("numPlantas"));
            double nivelPrimera     = Double.parseDouble(request.getParameter("nivelPrimera"));
            double nivelUltima      = Double.parseDouble(request.getParameter("nivelUltima"));
            double precioTotal      = Double.parseDouble(request.getParameter("precioTotal"));
            int    idCable          = Integer.parseInt(request.getParameter("cable"));
            int    idDistribuidor   = Integer.parseInt(request.getParameter("distribuidor"));
            int    idToma           = Integer.parseInt(request.getParameter("toma"));
            String idsDerivadores   = request.getParameter("idsDerivadores");
            double cabecera         = Double.parseDouble(request.getParameter("cabecera"));
            double distPlantas      = Double.parseDouble(request.getParameter("dist_plantas"));
            double distDerDist      = Double.parseDouble(request.getParameter("dist_der_dist"));
            double distDistToma     = Double.parseDouble(request.getParameter("dist_dist_toma"));
            // 1. Recoger los nuevos datos del formulario
            int vivPorPlanta = Integer.parseInt(request.getParameter("viv_planta"));
            int tomasPorViv  = Integer.parseInt(request.getParameter("tomas_viv"));

            // 2. Cálculo del precio real (ejemplo lógico)
            double precioTotalBruto = Double.parseDouble(request.getParameter("precioTotal"));
            // El precio total real sería: (Precio componentes vivienda * total viviendas) + troncal
            // Para simplificar, puedes multiplicar el precio que ya tienes por el volumen:
            double precioAjustado = precioTotalBruto * (vivPorPlanta * numPlantas); 

            // 3. Llamar al DAO con el precio ajustado
            HistorialDAO.insertar(
                rutaBD, nombre, numPlantas,
                nivelPrimera, nivelUltima, precioAjustado, // <--- PRECIO REAL
                idCable, idDistribuidor, idToma, idsDerivadores,
                cabecera, distPlantas, distDerDist, distDistToma
            );

            String msg = URLEncoder.encode("Edificio \"" + nombre + "\" guardado correctamente.", "UTF-8");
            response.sendRedirect("historial?mensaje=" + msg);

        } catch (Exception e) {
            String msg = URLEncoder.encode("Error al guardar: " + e.getMessage(), "UTF-8");
            response.sendRedirect("historial?mensaje=" + msg);
        }
    }
}
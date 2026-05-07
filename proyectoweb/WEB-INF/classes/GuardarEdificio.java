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
            // Datos generales del edificio
            String nombre         = request.getParameter("nombre");
            int    numPlantas     = Integer.parseInt(request.getParameter("numPlantas"));
            double nivelPrimera   = Double.parseDouble(request.getParameter("nivelPrimera"));
            double nivelUltima    = Double.parseDouble(request.getParameter("nivelUltima"));

            // IDs de componentes y parámetros de configuración (para poder reproducir el gráfico)
            int    idCable        = Integer.parseInt(request.getParameter("cable"));
            int    idDistribuidor = Integer.parseInt(request.getParameter("distribuidor"));
            int    idToma         = Integer.parseInt(request.getParameter("toma"));
            String idsDerivadores = request.getParameter("idsDerivadores");
            double cabecera       = Double.parseDouble(request.getParameter("cabecera"));
            double distPlantas    = Double.parseDouble(request.getParameter("dist_plantas"));
            double distDerDist    = Double.parseDouble(request.getParameter("dist_der_dist"));
            double distDistToma   = Double.parseDouble(request.getParameter("dist_dist_toma"));

            // Datos introducidos por el usuario en la caja de guardar
            int vivPorPlanta  = Integer.parseInt(request.getParameter("viv_planta"));
            int tomasPorViv   = Integer.parseInt(request.getParameter("tomas_viv"));

            // Costes unitarios calculados en EjecutarCalculo y pasados como campos ocultos
            double costeTroncal        = Double.parseDouble(request.getParameter("costeTroncal"));
            double costePorDistribuidor= Double.parseDouble(request.getParameter("costePorDistribuidor"));
            double costePorToma        = Double.parseDouble(request.getParameter("costePorToma"));

            // Número de salidas del distribuidor, pasado como campo oculto desde CalculoHTML
            // para evitar una consulta extra a la BD
            int numSalidasDist = Integer.parseInt(request.getParameter("numSalidasDist"));

            // ── CÁLCULO DEL PRECIO FINAL ──

            // Distribuidores por planta: si hay más viviendas que salidas del distribuidor,
            // hacemos el redondeo hacia arriba con Math.ceil para no dejar viviendas sin señal
            // Ej: 5 viviendas y distribuidor de 4 salidas → necesitamos 2 distribuidores
            int distPorPlanta = (int) Math.ceil((double) vivPorPlanta / numSalidasDist);

            // Coste de distribuidores para todo el edificio
            double costeDistribuidores = costePorDistribuidor * distPorPlanta * numPlantas;

            // Coste de tomas para todo el edificio
            double costeTomas = costePorToma * tomasPorViv * vivPorPlanta * numPlantas;

            // Precio final = coste troncal (cable + derivadores) + distribuidores + tomas
            double precioFinal = costeTroncal + costeDistribuidores + costeTomas;

            // Guardamos en la BD con el precio final calculado
            HistorialDAO.insertar(
                rutaBD, nombre, numPlantas,
                nivelPrimera, nivelUltima, precioFinal,
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
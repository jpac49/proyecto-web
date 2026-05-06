import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class EjecutarCalculo extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String rutaBD = getServletContext().getRealPath("/WEB-INF/bd/SimuladorICT.accdb");

        // Cargamos siempre los componentes para los selectores
        ArrayList<Componente> cables = ComponenteDAO.listarPorTipo(rutaBD, "Cable Coaxial");
        ArrayList<Componente> tomas  = ComponenteDAO.listarPorTipo(rutaBD, "Toma");
        ArrayList<Componente> dists  = ComponenteDAO.listarPorTipo(rutaBD, "Distribuidor");
        ArrayList<Componente> derivs = ComponenteDAO.listarPorTipo(rutaBD, "Derivador");

        String idCableStr = request.getParameter("cable");

        if (idCableStr == null) {
            // Abrir pestaña vacía
            response.getWriter().println(CalculoHTML.generarPagina(
                cables, tomas, dists, derivs,
                "", null, null, null, null,
                null, null, null, null, null, null, null, null
            ));
            return;
        }

        // Recogemos todos los parámetros del formulario para devolverlos al HTML
        String idDistStr     = request.getParameter("distribuidor");
        String idTomaStr     = request.getParameter("toma");
        String[] idsDerivArr = request.getParameterValues("derivadores");
        String cabeceraStr   = request.getParameter("cabecera");
        String distPlantasStr = request.getParameter("dist_plantas");
        String distDerDistStr = request.getParameter("dist_der_dist");
        String distDistTomaStr = request.getParameter("dist_dist_toma");

        try {
            double nivelCabecera = Double.parseDouble(cabeceraStr);
            double distPlantas   = Double.parseDouble(distPlantasStr);
            double distDerDist   = Double.parseDouble(distDerDistStr);
            double distDistToma  = Double.parseDouble(distDistTomaStr);

            Componente cableSel = ComponenteDAO.obtenerPorId(rutaBD, Integer.parseInt(idCableStr));
            Componente distSel  = ComponenteDAO.obtenerPorId(rutaBD, Integer.parseInt(idDistStr));
            Componente tomaSel  = ComponenteDAO.obtenerPorId(rutaBD, Integer.parseInt(idTomaStr));

            List<Componente> poolDerivs = new ArrayList<>();
            for (String id : idsDerivArr) {
                poolDerivs.add(ComponenteDAO.obtenerPorId(rutaBD, Integer.parseInt(id)));
            }

            // Ordenar derivadores de mayor a menor atenuación de salida
            poolDerivs.sort((d1, d2) -> Double.compare(d2.getAtSalida(), d1.getAtSalida()));

            // Cálculo
            List<Double> niveles = new ArrayList<>();
            List<String> modelos = new ArrayList<>();
            List<String> logs    = new ArrayList<>();

            double nivelTroncal  = nivelCabecera;
            Componente derivActual = poolDerivs.get(0);
            int planta = 1;

            while (planta <= 50) {
                double perdVivienda = (cableSel.getAt862() * distDerDist / 100.0)
                                    + distSel.getAtSalida()
                                    + (cableSel.getAt862() * distDistToma / 100.0)
                                    + tomaSel.getAtDerivacion();

                double nivelToma = nivelTroncal - derivActual.getAtSalida() - perdVivienda;

                if (nivelToma < 47.0) {
                    boolean cambiado = false;
                    for (Componente d : poolDerivs) {
                        double testNivel = nivelTroncal - d.getAtSalida() - perdVivienda;
                        if (testNivel >= 47.0 && testNivel <= 77.0) {
                            logs.add("Planta " + planta + ": Cambio a " + d.getModelo());
                            derivActual = d;
                            nivelToma   = testNivel;
                            cambiado    = true;
                            break;
                        }
                    }
                    if (!cambiado) break;
                }

                if (nivelToma > 77.0 || nivelToma < 47.0) break;

                niveles.add(nivelToma);
                modelos.add(derivActual.getModelo());

                nivelTroncal -= (derivActual.getAtPaso() + (cableSel.getAt862() * distPlantas / 100.0));
                planta++;
            }

            // Calcular precio total (cable + distribuidor + toma + derivadores únicos usados)
            double precioTotal = 0;
            Set<Integer> derivUsados = new HashSet<>();
            for (String id : idsDerivArr) derivUsados.add(Integer.parseInt(id));

            if (cableSel.getPrecio() != null) precioTotal += cableSel.getPrecio();
            if (distSel.getPrecio()  != null) precioTotal += distSel.getPrecio();
            if (tomaSel.getPrecio()  != null) precioTotal += tomaSel.getPrecio();
            for (Componente d : poolDerivs) {
                if (d.getPrecio() != null) precioTotal += d.getPrecio();
            }

            // Nombre por defecto: "Edificio X" donde X = total entradas + 1
            int totalEntradas = HistorialDAO.contarEntradas(rutaBD);
            String nombreDefecto = "Edificio " + (totalEntradas + 1);

            // IDs de derivadores como string separado por comas
            String idsDerivStr = String.join(",", idsDerivArr);

            double nivelPrimera = niveles.isEmpty() ? 0 : niveles.get(0);
            double nivelUltima  = niveles.isEmpty() ? 0 : niveles.get(niveles.size() - 1);

            response.getWriter().println(CalculoHTML.generarPagina(
                cables, tomas, dists, derivs,
                "C\u00e1lculo finalizado",
                niveles.size(),
                niveles, modelos, logs,
                // Parámetros para mantener el formulario y el botón guardar
                idCableStr, idDistStr, idTomaStr, idsDerivArr,
                cabeceraStr, distPlantasStr, distDerDistStr, distDistTomaStr,
                nombreDefecto, nivelPrimera, nivelUltima, precioTotal, idsDerivStr
            ));

        } catch (Exception e) {
            response.getWriter().println(CalculoHTML.generarPagina(
                cables, tomas, dists, derivs,
                "Error: " + e.getMessage(),
                null, null, null, null,
                idCableStr, idDistStr, idTomaStr, idsDerivArr,
                cabeceraStr, distPlantasStr, distDerDistStr, distDistTomaStr
            ));
        }
    }
}
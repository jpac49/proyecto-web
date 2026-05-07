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

        // Si no hay parámetros, el usuario acaba de abrir la página: devolvemos la página vacía
        if (idCableStr == null) {
            response.getWriter().println(CalculoHTML.generarPagina(
                cables, tomas, dists, derivs,
                "", null, null, null, null,
                null, null, null, null, null, null, null, null
            ));
            return;
        }

        // Recogemos todos los parámetros del formulario para devolverlos al HTML
        String idDistStr      = request.getParameter("distribuidor");
        String idTomaStr      = request.getParameter("toma");
        String[] idsDerivArr  = request.getParameterValues("derivadores");
        String cabeceraStr    = request.getParameter("cabecera");
        String distPlantasStr = request.getParameter("dist_plantas");
        String distDerDistStr = request.getParameter("dist_der_dist");
        String distDistTomaStr= request.getParameter("dist_dist_toma");

        try {
            double nivelCabecera = Double.parseDouble(cabeceraStr);
            double distPlantas   = Double.parseDouble(distPlantasStr);
            double distDerDist   = Double.parseDouble(distDerDistStr);
            double distDistToma  = Double.parseDouble(distDistTomaStr);

            // Obtenemos los objetos Componente completos desde la BD usando sus IDs
            Componente cableSel = ComponenteDAO.obtenerPorId(rutaBD, Integer.parseInt(idCableStr));
            Componente distSel  = ComponenteDAO.obtenerPorId(rutaBD, Integer.parseInt(idDistStr));
            Componente tomaSel  = ComponenteDAO.obtenerPorId(rutaBD, Integer.parseInt(idTomaStr));

            // Construimos la lista de derivadores disponibles (pool)
            List<Componente> poolDerivs = new ArrayList<>();
            for (String id : idsDerivArr) {
                poolDerivs.add(ComponenteDAO.obtenerPorId(rutaBD, Integer.parseInt(id)));
            }

            // Ordenar derivadores de mayor a menor atenuación de salida.
            // Así el algoritmo empieza siempre con el que más atenúa (plantas bajas
            // con más señal) e irá cambiando a los que menos atenúan según baje el nivel.
            poolDerivs.sort((d1, d2) -> Double.compare(d2.getAtSalida(), d1.getAtSalida()));

            // ── ALGORITMO DE CÁLCULO ──
            List<Double> niveles = new ArrayList<>(); // Nivel en la toma de cada planta
            List<String> modelos = new ArrayList<>(); // Modelo de derivador usado en cada planta
            List<String> logs    = new ArrayList<>(); // Registro de cambios de derivador

            double nivelTroncal    = nivelCabecera;
            Componente derivActual = poolDerivs.get(0); // Empezamos con el de mayor atenuación
            int planta = 1;

            while (planta <= 50) {
                // Pérdida desde el derivador hasta la toma:
                // cable(deriv→dist) + atenuación distribuidor + cable(dist→toma) + atenuación toma
                double perdVivienda = (cableSel.getAt862() * distDerDist / 100.0)
                                    + distSel.getAtSalida()
                                    + (cableSel.getAt862() * distDistToma / 100.0)
                                    + tomaSel.getAtDerivacion();

                // Nivel que llega a la toma en esta planta
                double nivelToma = nivelTroncal - derivActual.getAtSalida() - perdVivienda;

                // Si el nivel está por debajo del mínimo (47 dBµV), intentamos cambiar
                // a un derivador con menos atenuación de salida para recuperar señal
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
                    // Si ningún derivador mantiene el nivel en rango, el edificio termina aquí
                    if (!cambiado) break;
                }

                // Si el nivel está fuera del rango válido (47-77 dBµV) terminamos
                if (nivelToma > 77.0 || nivelToma < 47.0) break;

                // Guardamos los resultados de esta planta
                niveles.add(nivelToma);
                modelos.add(derivActual.getModelo());

                // Actualizamos el nivel troncal para la siguiente planta:
                // restamos la atenuación de paso del derivador y la del cable entre plantas
                nivelTroncal -= (derivActual.getAtPaso() + (cableSel.getAt862() * distPlantas / 100.0));
                planta++;
            }

            // ── CÁLCULO DE COSTES ──
            int numPlantas = niveles.size();

            // Metros totales de cable en el edificio:
            // troncal entre plantas + ramal derivador→distribuidor + ramal distribuidor→toma
            // (uno de cada por planta)
            double metrosCable = (distPlantas * numPlantas)
                               + (distDerDist * numPlantas)
                               + (distDistToma * numPlantas);

            // Coste troncal: cable + derivadores (uno por planta según modelo usado)
            double costeTroncal = 0;
            if (cableSel.getPrecio() != null) costeTroncal += cableSel.getPrecio() * metrosCable;

            // Contamos cuántas veces se usó cada modelo de derivador planta a planta
            // ya que el modelo puede cambiar a lo largo del edificio
            Map<Integer, Integer> conteoDerivs = new HashMap<>();
            for (String modelo : modelos) {
                for (Componente d : poolDerivs) {
                    if (d.getModelo().equals(modelo)) {
                        conteoDerivs.put(d.getId(), conteoDerivs.getOrDefault(d.getId(), 0) + 1);
                    }
                }
            }
            for (Componente d : poolDerivs) {
                if (d.getPrecio() != null && conteoDerivs.containsKey(d.getId())) {
                    costeTroncal += d.getPrecio() * conteoDerivs.get(d.getId());
                }
            }

            // Coste unitario del distribuidor y de la toma.
            // No se multiplica aquí porque todavía no sabemos cuántas viviendas
            // hay por planta ni cuántas tomas por vivienda. Eso lo introduce el
            // usuario en la caja de guardar y GuardarEdificio hace el cálculo final.
            double costePorDistribuidor = (distSel.getPrecio() != null) ? distSel.getPrecio() : 0;
            double costePorToma         = (tomaSel.getPrecio() != null) ? tomaSel.getPrecio() : 0;

            // Número de salidas del distribuidor: se pasa como campo oculto para que
            // GuardarEdificio pueda calcular cuántos distribuidores necesita cada planta
            // sin tener que hacer una consulta extra a la BD
            int numSalidasDist = (distSel.getNumSalidas() != null) ? distSel.getNumSalidas() : 1;

            // Nombre por defecto: "Edificio X" donde X = total entradas + 1
            int totalEntradas = HistorialDAO.contarEntradas(rutaBD);
            String nombreDefecto = "Edificio " + (totalEntradas + 1);

            // IDs de derivadores como string separado por comas para guardarlo en la BD
            String idsDerivStr = String.join(",", idsDerivArr);

            double nivelPrimera = niveles.isEmpty() ? 0 : niveles.get(0);
            double nivelUltima  = niveles.isEmpty() ? 0 : niveles.get(niveles.size() - 1);

            response.getWriter().println(CalculoHTML.generarPagina(
                cables, tomas, dists, derivs,
                "C\u00e1lculo finalizado",
                niveles.size(),
                niveles, modelos, logs,
                idCableStr, idDistStr, idTomaStr, idsDerivArr,
                cabeceraStr, distPlantasStr, distDerDistStr, distDistTomaStr,
                nombreDefecto, nivelPrimera, nivelUltima,
                costeTroncal, costePorDistribuidor, costePorToma, numSalidasDist,
                idsDerivStr
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
import java.io.*;
import java.net.URLEncoder;
import javax.servlet.*;
import javax.servlet.http.*;

public class VerGraficoHistorial extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String rutaBD = getServletContext().getRealPath("/WEB-INF/bd/SimuladorICT.accdb");

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            HistorialEntrada h = HistorialDAO.obtenerPorId(rutaBD, id);

            if (h == null) {
                String msg = URLEncoder.encode("No se encontró el edificio en el historial", "UTF-8");
                response.sendRedirect("historial?mensaje=" + msg);
                return;
            }

            StringBuilder url = new StringBuilder();
            url.append("ejecutarCalculo?");
            url.append("cable=").append(h.idCable);
            url.append("&distribuidor=").append(h.idDistribuidor);
            url.append("&toma=").append(h.idToma);

            if (h.idsDerivadores != null && !h.idsDerivadores.trim().equals("")) {
                String[] ids = h.idsDerivadores.split(",");

                for (String did : ids) {
                    if (!did.trim().equals("")) {
                        url.append("&derivadores=").append(URLEncoder.encode(did.trim(), "UTF-8"));
                    }
                }
            }

            url.append("&cabecera=").append(h.cabecera);
            url.append("&dist_plantas=").append(h.distPlantas);
            url.append("&dist_der_dist=").append(h.distDerDist);
            url.append("&dist_dist_toma=").append(h.distDistToma);

            response.sendRedirect(url.toString());

        } catch (Exception e) {
            String msg = URLEncoder.encode("Error al cargar el gráfico: " + e.getMessage(), "UTF-8");
            response.sendRedirect("historial?mensaje=" + msg);
        }
    }
}
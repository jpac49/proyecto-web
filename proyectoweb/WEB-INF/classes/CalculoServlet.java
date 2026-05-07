import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;

public class CalculoServlet extends HttpServlet {

    @SuppressWarnings("unchecked")
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String rutaBD = getServletContext().getRealPath("/WEB-INF/bd/SimuladorICT.accdb");

        ArrayList<Componente> cables = new ArrayList<Componente>();
        ArrayList<Componente> tomas = new ArrayList<Componente>();
        ArrayList<Componente> distribuidores = new ArrayList<Componente>();
        ArrayList<Componente> derivadores = new ArrayList<Componente>();

        try {
            ArrayList<Componente> todos = ComponenteDAO.listar(rutaBD);

            for (Componente c : todos) {
                String tipo = (c.getTipo() != null) ? c.getTipo() : "";

                if ("Cable Coaxial".equals(tipo)) {
                    cables.add(c);
                } else if ("Toma".equals(tipo)) {
                    tomas.add(c);
                } else if ("Distribuidor".equals(tipo)) {
                    distribuidores.add(c);
                } else if ("Derivador".equals(tipo)) {
                    derivadores.add(c);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        String mensaje = obtenerString(request, "mensaje", "mensaje");

        Integer maxPlantasResult = obtenerInteger(request, "maxPlantas");

        List<Double> nivelesPorPlanta =
                (List<Double>) request.getAttribute("nivelesPorPlanta");

        if (nivelesPorPlanta == null) {
            nivelesPorPlanta = new ArrayList<Double>();
        }

        List<String> modelosDerivPorPlanta =
                (List<String>) request.getAttribute("modelosDerivPorPlanta");

        if (modelosDerivPorPlanta == null) {
            modelosDerivPorPlanta = new ArrayList<String>();
        }

        List<String> logsCambios =
                (List<String>) request.getAttribute("logsCambios");

        if (logsCambios == null) {
            logsCambios = new ArrayList<String>();
        }

        String selCable = obtenerString(request, "selCable", "cable");
        String selDistribuidor = obtenerString(request, "selDistribuidor", "distribuidor");
        String selToma = obtenerString(request, "selToma", "toma");

        String[] selDerivadores = (String[]) request.getAttribute("selDerivadores");

        if (selDerivadores == null) {
            selDerivadores = request.getParameterValues("derivadores");
        }

        if (selDerivadores == null) {
            selDerivadores = new String[0];
        }

        String valCabecera = obtenerString(request, "valCabecera", "cabecera");
        String valDistPlantas = obtenerString(request, "valDistPlantas", "dist_plantas");
        String valDistDerDist = obtenerString(request, "valDistDerDist", "dist_der_dist");
        String valDistDistToma = obtenerString(request, "valDistDistToma", "dist_dist_toma");

        String html = CalculoHTML.generarPagina(
                cables,
                tomas,
                distribuidores,
                derivadores,
                mensaje,
                maxPlantasResult,
                nivelesPorPlanta,
                modelosDerivPorPlanta,
                logsCambios,
                selCable,
                selDistribuidor,
                selToma,
                selDerivadores,
                valCabecera,
                valDistPlantas,
                valDistDerDist,
                valDistDistToma
        );

        out.println(html);
    }

    private static String obtenerString(HttpServletRequest request, String nombreAtributo, String nombreParametro) {
        Object valor = request.getAttribute(nombreAtributo);

        if (valor != null) {
            return String.valueOf(valor);
        }

        return request.getParameter(nombreParametro);
    }

    private static Integer obtenerInteger(HttpServletRequest request, String nombreAtributo) {
        Object valor = request.getAttribute(nombreAtributo);

        if (valor == null) {
            return null;
        }

        if (valor instanceof Integer) {
            return (Integer) valor;
        }

        try {
            return Integer.parseInt(String.valueOf(valor));
        } catch (Exception e) {
            return null;
        }
    }
}
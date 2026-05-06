import java.util.ArrayList;

public class HistorialHTML {

    public static String generarPagina(ArrayList<HistorialEntrada> entradas, String mensaje, String rutaBD) {

        String html = "";
        html += "<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>";
        html += "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>";
        html += "<title>Historial - Simulador ICT</title>";

        html += "<style>";
        html += "body { font-family: Arial, sans-serif; margin: 0; background-color: #f5f5f5; }";
        html += ".navbar { background: #333; color: white; padding: 1rem 1.5rem; display: flex; align-items: center; gap: 35px; }";
        html += ".navbar .logo { font-weight: bold; font-size: 1.4rem; margin-right: 20px; }";
        html += ".navbar a { color: white; text-decoration: none; padding: 10px 14px; border-radius: 8px; font-size: 1.1rem; }";
        html += ".navbar a:hover { background: #555; }";
        html += ".navbar a.activo { background: #5a5a5a; }";
        html += ".salir { margin-left: auto; background: #e33629; }";
        html += ".content { padding: 30px; }";
        html += ".contenedor { max-width: 1250px; margin: 0 auto; }";
        html += "h1 { font-size: 2.2rem; margin-bottom: 28px; }";
        html += ".tabla-card { background: white; border-radius: 16px; padding: 28px; border: 1px solid #ddd; box-shadow: 0 2px 8px rgba(0,0,0,0.04); }";
        html += "h2 { margin-top: 0; }";
        html += "hr { border: none; border-top: 1px solid #ddd; margin: 20px 0; }";
        html += "table { width: 100%; border-collapse: collapse; font-size: 0.95rem; }";
        html += "thead tr { background: #333; color: white; }";
        html += "thead th { padding: 12px 14px; text-align: left; font-weight: bold; }";
        html += "tbody tr { border-bottom: 1px solid #eee; }";
        html += "tbody tr:hover { background: #f9f9f9; }";
        html += "tbody td { padding: 11px 14px; }";
        html += ".vacio { color: #888; font-style: italic; text-align: center; padding: 40px; }";
        html += ".btn-ver { background: #1a73e8; color: white; border: none; padding: 7px 14px; border-radius: 6px; cursor: pointer; font-size: 0.88rem; font-weight: bold; text-decoration: none; white-space: nowrap; display: inline-block; }";
        html += ".btn-ver:hover { background: #1557b0; }";
        html += ".mensaje { background: #fff3cd; padding: 12px; border-radius: 8px; border: 1px solid #ffecb5; margin-bottom: 20px; }";
        html += ".etiqueta { display: inline-block; background: #f1f1f1; border: 1px solid #ddd; border-radius: 20px; padding: 3px 10px; font-size: 0.82rem; color: #333; }";
        html += "</style>";

        // Script de cierre de aplicación
        html += "<script>";
        html += "function cerrarApp() {";
        html += "  if (confirm('¿Desea cerrar la aplicación?')) {";
        html += "    window.close();";
        html += "    window.location.href = 'about:blank';";
        html += "  }";
        html += "}";
        html += "</script>";

        html += "</head><body>";

        // Navbar
        html += "<div class='navbar'>";
        html += "<span class='logo'>📡 Simulador TV</span>";
        html += "<a href='componentes'>Ingresar Componentes</a>";
        html += "<a href='calcular'>Calcular Plantas</a>";
        html += "<a href='historial' class='activo'>Historial</a>";
        html += "<button onclick='cerrarApp()' style='margin-left:auto; background:#e33629; color:white; border:none; padding:10px 14px; border-radius:8px; cursor:pointer; font-size:1.1rem;'>Salir</button>";
        html += "</div>";

        html += "<div class='content'><div class='contenedor'>";
        html += "<h1>Historial de Edificios</h1>";

        if (mensaje != null && !mensaje.equals("")) {
            html += "<div class='mensaje'>" + escapar(mensaje) + "</div>";
        }

        html += "<div class='tabla-card'>";
        html += "<h2>Edificios Guardados</h2><hr>";

        if (entradas.isEmpty()) {
            html += "<p class='vacio'>No hay edificios guardados todav&iacute;a.</p>";
        } else {
            html += "<table>";
            html += "<thead><tr>";
            html += "<th>Nombre</th>";
            html += "<th>Plantas</th>";
            html += "<th>Nivel 1&ordf; Planta</th>";
            html += "<th>Nivel &uacute;lt. Planta</th>";
            html += "<th>Precio Total</th>";
            html += "<th>Componentes</th>";
            html += "<th>Fecha</th>";
            html += "<th>Gr&aacute;fico</th>";
            html += "</tr></thead>";
            html += "<tbody>";

            for (HistorialEntrada h : entradas) {

                // Construimos la URL para reproducir el cálculo
                String urlCalculo = "ejecutarCalculo"
                    + "?cable="        + h.idCable
                    + "&distribuidor=" + h.idDistribuidor
                    + "&toma="         + h.idToma
                    + "&derivadores="  + h.idsDerivadores.replace(",", "&derivadores=")
                    + "&cabecera="     + h.cabecera
                    + "&dist_plantas=" + h.distPlantas
                    + "&dist_der_dist="+ h.distDerDist
                    + "&dist_dist_toma="+ h.distDistToma;

                html += "<tr>";
                html += "<td><strong>" + escapar(h.nombre) + "</strong></td>";
                html += "<td>" + h.numPlantas + "</td>";
                html += "<td>" + String.format("%.2f", h.nivelPrimeraPlanta) + " dB&micro;V</td>";
                html += "<td>" + String.format("%.2f", h.nivelUltimaPlanta) + " dB&micro;V</td>";
                html += "<td>" + String.format("%.2f", h.precioTotal) + " &euro;</td>";
                html += "<td>";
                html += "<span class='etiqueta'>Cable: " + escapar(h.modeloCable) + "</span> ";
                html += "<span class='etiqueta'>Dist: " + escapar(h.modeloDistribuidor) + "</span> ";
                html += "<span class='etiqueta'>Toma: " + escapar(h.modeloToma) + "</span> ";
                // Mostramos los derivadores
                // Busca esta sección en tu archivo y reemplázala:
                String[] derivIds = h.idsDerivadores.split(",");
                for (String did : derivIds) {
                    if (!did.trim().isEmpty()) {
                        int idInt = Integer.parseInt(did.trim());
                        // Buscamos el modelo real usando el ID
                        String nombreModelo = ComponenteDAO.obtenerNombrePorId(rutaBD, idInt); 
                        html += "<span class='etiqueta'>Deriv: " + escapar(nombreModelo) + "</span> ";
                    }
                }
                html += "</td>";
                String fechaCorta = (h.fecha != null && h.fecha.length() >= 16) ? h.fecha.substring(0, 16) : h.fecha;
                html += "<td>" + escapar(fechaCorta) + "</td>";
                html += "<td><a href='" + urlCalculo + "' class='btn-ver'>Ver gr&aacute;fico</a></td>";
                html += "</tr>";
            }

            html += "</tbody></table>";
        }

        html += "</div></div></div></body></html>";

        return html;
    }

    private static String escapar(String texto) {
        if (texto == null) return "";
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                    .replace("á", "&aacute;").replace("é", "&eacute;").replace("í", "&iacute;")
                    .replace("ó", "&oacute;").replace("ú", "&uacute;").replace("ñ", "&ntilde;");
    }
}
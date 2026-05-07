import java.util.ArrayList;

public class HistorialHTML {

    public static String generarPagina(ArrayList<HistorialEntrada> entradas, String mensaje, String rutaBD) {

        String html = "";
        html += "<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>";
        html += "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>";
        html += "<title>Historial - Simulador ICT</title>";

// --- BLOQUE DE ESTILOS (CSS) ---
        html += "<style>";
        html += "body { font-family: Arial, sans-serif; margin: 0; background-color: #f5f5f5; }";
        html += ".navbar { background: #333; color: white; padding: 1rem 1.5rem; display: flex; align-items: center; gap: 35px; }";
        html += ".navbar .logo { font-weight: bold; font-size: 1.4rem; margin-right: 20px; }";
        html += ".navbar a { color: white; text-decoration: none; padding: 10px 14px; border-radius: 8px; font-size: 1.1rem; }";
        html += ".navbar a:hover { background: #555; }";
        html += ".navbar a.activo { background: #5a5a5a; }";
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
        html += ".btn-eliminar { background: #d93025; color: white; border: none; padding: 7px 14px; border-radius: 6px; cursor: pointer; font-size: 0.88rem; font-weight: bold; white-space: nowrap; display: inline-block; margin-left: 8px; }";
        html += ".btn-eliminar:hover { background: #b3261e; }";
        html += ".mensaje { background: #fff3cd; padding: 12px; border-radius: 8px; border: 1px solid #ffecb5; margin-bottom: 20px; }";
        html += ".etiqueta { display: inline-block; background: #f1f1f1; border: 1px solid #ddd; border-radius: 20px; padding: 3px 10px; font-size: 0.82rem; color: #333; }";
        html += ".acciones { white-space: nowrap; }";
        html += "</style>";

// --- BLOQUE DE JAVASCRIPT ---
        html += "<script>";
        html += "function cerrarApp() {";
        html += "  if (confirm('¿Desea cerrar la aplicación?')) {";
        html += "    window.close();";
        html += "    window.location.href = 'about:blank';";
        html += "  }";
        html += "}";
        html += "</script>";

        html += "</head><body>";
        
// --- BARRA DE NAVEGACIÓN ---
        html += "<div class='navbar'>";
        html += "<span class='logo'>📡 Simulador TV</span>";
        html += "<a href='componentes'>Ingresar Componentes</a>";
        html += "<a href='calcular'>Calcular Plantas</a>";
        html += "<a href='historial' class='activo'>Historial</a>";
        html += "<button onclick='cerrarApp()' style='margin-left:auto; background:#e33629; color:white; border:none; padding:10px 14px; border-radius:8px; cursor:pointer; font-size:1.1rem;'>Salir</button>";
        html += "</div>";

        html += "<div class='content'><div class='contenedor'>";
        html += "<h1>Historial de Edificios</h1>";
        
// Si el Servlet nos envía un mensaje de "Borrado con éxito", se muestra aquí
        if (mensaje != null && !mensaje.equals("")) {
            html += "<div class='mensaje'>" + escapar(mensaje) + "</div>";
        }

        html += "<div class='tabla-card'>";
        html += "<h2>Edificios Guardados</h2><hr>";

// Lógica de control: Si no hay datos, mostramos un aviso en lugar de una tabla vacía
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
            html += "<th>Acciones</th>";
            html += "</tr></thead>";
            html += "<tbody>";

// RECORRIDO DE DATOS: Por cada edificio en la lista, creamos una fila <tr>
            for (HistorialEntrada h : entradas) {
                
// Generamos la URL única para ver el gráfico de este edificio específico por su ID
                String urlCalculo = "verGraficoHistorial?id=" + h.id;

                html += "<tr>";
                html += "<td><strong>" + escapar(h.nombre) + "</strong></td>";
                html += "<td>" + h.numPlantas + "</td>";
                
    // Formateamos a 2 decimales
                html += "<td>" + String.format("%.2f", h.nivelPrimeraPlanta) + " dB&micro;V</td>";
                html += "<td>" + String.format("%.2f", h.nivelUltimaPlanta) + " dB&micro;V</td>";
                html += "<td>" + String.format("%.2f", h.precioTotal) + " &euro;</td>";
// Celda de componentes usados (Cable, Distribuidor y Toma)
                html += "<td>";
                html += "<span class='etiqueta'>Cable: " + escapar(h.modeloCable) + "</span> ";
                html += "<span class='etiqueta'>Dist: " + escapar(h.modeloDistribuidor) + "</span> ";
                html += "<span class='etiqueta'>Toma: " + escapar(h.modeloToma) + "</span> ";

// PROCESAMIENTO DE DERIVADORES: Al ser una lista de IDs, los separamos por la coma
                if (h.idsDerivadores != null && !h.idsDerivadores.trim().equals("")) {
                    String[] derivIds = h.idsDerivadores.split(",");
                    for (String did : derivIds) {
                        if (!did.trim().isEmpty()) {
                            try {
// Pedimos al DAO el nombre comercial usando el ID
                                int idInt = Integer.parseInt(did.trim());
                                String nombreModelo = ComponenteDAO.obtenerNombrePorId(rutaBD, idInt);
                                html += "<span class='etiqueta'>Deriv: " + escapar(nombreModelo) + "</span> ";
                            } catch (Exception e) {
                                html += "<span class='etiqueta'>Deriv: desconocido</span> ";
                            }
                        }
                    }
                }
                html += "</td>";
// Recortamos la fecha para no mostrar milisegundos (YYYY-MM-DD HH:MM)
                String fechaCorta = (h.fecha != null && h.fecha.length() >= 16) ? h.fecha.substring(0, 16) : h.fecha;
                html += "<td>" + escapar(fechaCorta) + "</td>";

                // Columna de acciones: Ver gráfico + Eliminar
                html += "<td class='acciones'>";
                html += "<a href='" + urlCalculo + "' class='btn-ver'>Ver gr&aacute;fico</a>";
// Al eliminar, pasamos el ID por parámetro GET (?id=...)
                html += "<a href='eliminarHistorial?id=" + h.id + "' class='btn-eliminar' ";
                html += "onclick=\"return confirm('¿Seguro que quieres eliminar " + escapar(h.nombre) + "?');\">Eliminar</a>";
                html += "</td>";

                html += "</tr>";
            }

            html += "</tbody></table>";
        }

        html += "</div></div></div></body></html>";

        return html;
    }

// FUNCIÓN DE SEGURIDAD: Evita errores de visualización con tildes y símbolos HTML
    private static String escapar(String texto) {
        if (texto == null) return "";
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("á", "&aacute;").replace("é", "&eacute;").replace("í", "&iacute;")
                .replace("ó", "&oacute;").replace("ú", "&uacute;").replace("ñ", "&ntilde;");
    }
}
import java.util.ArrayList;
import java.util.List;

public class CalculoHTML {

    // Firma completa: incluye parámetros para repoblar el formulario y guardar
    public static String generarPagina(
        ArrayList<Componente> cables,
        ArrayList<Componente> tomas,
        ArrayList<Componente> distribuidores,
        ArrayList<Componente> derivadores,
        String mensaje,
        Integer maxPlantasResult,
        List<Double> nivelesPorPlanta,
        List<String> modelosDerivPorPlanta,
        List<String> logsCambios,
        // Valores seleccionados para repoblar el formulario (pueden ser null)
        String selCable,
        String selDistribuidor,
        String selToma,
        String[] selDerivadores,
        String valCabecera,
        String valDistPlantas,
        String valDistDerDist,
        String valDistDistToma
    ) {
        return generarPaginaCompleta(
            cables, tomas, distribuidores, derivadores, mensaje,
            maxPlantasResult, nivelesPorPlanta, modelosDerivPorPlanta, logsCambios,
            selCable, selDistribuidor, selToma, selDerivadores,
            valCabecera, valDistPlantas, valDistDerDist, valDistDistToma,
            null, 0, 0, 0, null
        );
    }

    // Firma extendida: incluye datos para el botón guardar
    public static String generarPagina(
        ArrayList<Componente> cables,
        ArrayList<Componente> tomas,
        ArrayList<Componente> distribuidores,
        ArrayList<Componente> derivadores,
        String mensaje,
        Integer maxPlantasResult,
        List<Double> nivelesPorPlanta,
        List<String> modelosDerivPorPlanta,
        List<String> logsCambios,
        String selCable,
        String selDistribuidor,
        String selToma,
        String[] selDerivadores,
        String valCabecera,
        String valDistPlantas,
        String valDistDerDist,
        String valDistDistToma,
        String nombreDefecto,
        double nivelPrimera,
        double nivelUltima,
        double precioTotal,
        String idsDerivStr
    ) {
        return generarPaginaCompleta(
            cables, tomas, distribuidores, derivadores, mensaje,
            maxPlantasResult, nivelesPorPlanta, modelosDerivPorPlanta, logsCambios,
            selCable, selDistribuidor, selToma, selDerivadores,
            valCabecera, valDistPlantas, valDistDerDist, valDistDistToma,
            nombreDefecto, nivelPrimera, nivelUltima, precioTotal, idsDerivStr
        );
    }

    private static String generarPaginaCompleta(
        ArrayList<Componente> cables,
        ArrayList<Componente> tomas,
        ArrayList<Componente> distribuidores,
        ArrayList<Componente> derivadores,
        String mensaje,
        Integer maxPlantasResult,
        List<Double> nivelesPorPlanta,
        List<String> modelosDerivPorPlanta,
        List<String> logsCambios,
        String selCable,
        String selDistribuidor,
        String selToma,
        String[] selDerivadores,
        String valCabecera,
        String valDistPlantas,
        String valDistDerDist,
        String valDistDistToma,
        String nombreDefecto,
        double nivelPrimera,
        double nivelUltima,
        double precioTotal,
        String idsDerivStr
    ) {
        String html = "";
        html += "<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>";
        html += "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>";
        html += "<title>Calcular Plantas - Simulador ICT</title>";

        html += "<style>";
        html += "body { font-family: Arial, sans-serif; margin: 0; background-color: #f5f5f5; }";
        html += ".navbar { background: #333; color: white; padding: 1rem 1.5rem; display: flex; align-items: center; gap: 35px; }";
        html += ".navbar .logo { font-weight: bold; font-size: 1.4rem; margin-right: 20px; }";
        html += ".navbar a { color: white; text-decoration: none; padding: 10px 14px; border-radius: 8px; font-size: 1.1rem; }";
        html += ".navbar a:hover { background: #555; }";
        html += ".navbar a.activo { background: #5a5a5a; }";
        html += ".salir { margin-left: auto; background: #e33629; }";
        html += ".content { padding: 30px; }";
        html += ".contenedor-calculo { max-width: 1250px; margin: 0 auto; }";
        html += ".layout-calculo { display: grid; grid-template-columns: 480px 1fr; gap: 28px; align-items: start; }";
        html += ".config-card, .resultado-card { background: white; border-radius: 16px; padding: 28px; border: 1px solid #ddd; box-shadow: 0 2px 8px rgba(0,0,0,0.04); position: relative; }";
        html += "h1 { font-size: 2.2rem; margin-bottom: 28px; }";
        html += "h2 { margin-top: 0; }";
        html += "hr { border: none; border-top: 1px solid #ddd; margin: 20px 0; }";
        html += "label { display: block; margin-top: 18px; margin-bottom: 6px; font-weight: bold; }";
        html += "input, select { width: 100%; padding: 12px; font-size: 1rem; box-sizing: border-box; border: 1px solid #ccc; border-radius: 10px; }";
        html += ".seccion-titulo { background: #f1f1f1; padding: 8px 12px; border-radius: 8px; margin: 22px 0 6px 0; font-weight: bold; font-size: 0.95rem; color: #333; }";
        html += ".info-lista { background: #f9f9f9; border: 1px solid #e3e3e3; padding: 10px 14px; border-radius: 8px; margin-top: 8px; font-size: 0.85rem; min-height: 36px; color: #555; }";
        html += ".info-lista ul { margin: 0; padding-left: 18px; }";
        html += ".btn-calcular { margin-top: 25px; width: 100%; background: #1a73e8; color: white; border: none; font-weight: bold; padding: 14px; font-size: 1.05rem; border-radius: 8px; cursor: pointer; }";
        html += ".btn-calcular:hover { background: #1557b0; }";
        html += ".btn-agregar { margin-top: 14px; background: #f1f1f1; border: 1px solid #ccc; padding: 9px 14px; border-radius: 8px; cursor: pointer; font-size: 0.95rem; font-weight: bold; width: 100%; }";
        html += ".btn-agregar:hover { background: #e3e3e3; }";
        html += ".btn-guardar { margin-top: 20px; width: 100%; background: #2e7d32; color: white; border: none; font-weight: bold; padding: 14px; font-size: 1.05rem; border-radius: 8px; cursor: pointer; }";
        html += ".btn-guardar:hover { background: #1b5e20; }";
        html += ".grid-2 { display: grid; grid-template-columns: 1fr 1fr; gap: 14px; }";
        html += ".mensaje { background: #fff3cd; padding: 12px; border-radius: 8px; border: 1px solid #ffecb5; margin-bottom: 20px; }";
        html += ".esquema-container { position: relative; display: flex; flex-direction: column; align-items: center; padding: 40px 20px; background: #fff; min-height: 500px; }";
        html += ".bloque-cabecera { width: 140px; background: #333; color: white; padding: 12px; text-align: center; border-radius: 6px; margin-bottom: 40px; font-weight: bold; z-index: 3; }";
        html += ".troncal { width: 6px; background: #555; position: absolute; top: 80px; bottom: 40px; left: 50%; transform: translateX(-50%); z-index: 1; }";
        html += ".planta-row { display: flex; align-items: center; width: 100%; margin-bottom: 40px; position: relative; z-index: 2; }";
        html += ".caja-planta { width: 200px; background: #fff; border: 2px solid #1a73e8; padding: 12px; border-radius: 10px; font-size: 0.9rem; margin-left: auto; margin-right: calc(50% + 30px); box-shadow: 0 4px 6px rgba(0,0,0,0.05); }";
        html += ".dot { width: 16px; height: 16px; background: #1a73e8; border: 3px solid #fff; border-radius: 50%; position: absolute; left: 50%; transform: translateX(-50%); box-shadow: 0 0 0 2px #1a73e8; }";
        html += ".info-cambios-box { position: absolute; top: 20px; right: 20px; width: 240px; background: #fffde7; border: 1px solid #ffe57f; padding: 15px; border-radius: 12px; font-size: 0.85rem; box-shadow: 0 4px 12px rgba(0,0,0,0.1); z-index: 10; }";
        html += ".nivel-valor { font-weight: bold; color: #1a73e8; font-size: 1.1rem; }";
        html += ".resultado-vacio { text-align: center; color: #888; padding: 80px 40px; border: 2px dashed #ddd; border-radius: 12px; margin-top: 10px; }";
        html += ".guardar-box { margin-top: 24px; background: #f1f1f1; border: 1px solid #ddd; border-radius: 12px; padding: 20px; }";
        html += ".guardar-box h3 { margin: 0 0 14px 0; font-size: 1rem; color: #333; }";
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

        // ── JAVASCRIPT ──
        html += "<script>";
        html += "var datosComp = {";
        for (Componente c : cables)
            html += "'c" + c.getId() + "': ['Aten. 862 MHz: " + (c.getAt862() != null ? c.getAt862() : 0) + " dB/100m'],";
        for (Componente c : tomas)
            html += "'t" + c.getId() + "': ['Aten. Derivaci\u00f3n: " + (c.getAtDerivacion() != null ? c.getAtDerivacion() : 0) + " dB'],";
        for (Componente c : derivadores)
            html += "'d" + c.getId() + "': ['Aten. Paso: " + (c.getAtPaso() != null ? c.getAtPaso() : 0) + " dB', 'Aten. Salida: " + (c.getAtSalida() != null ? c.getAtSalida() : 0) + " dB'],";
        for (Componente c : distribuidores)
            html += "'di" + c.getId() + "': ['Aten. Salida: " + (c.getAtSalida() != null ? c.getAtSalida() : 0) + " dB'],";
        html += "};";

        html += "function mostrarInfo(select, idContenedor, prefijo) {";
        html += "  var infoBox = document.getElementById(idContenedor);";
        html += "  if (!select.value) { infoBox.innerHTML = ''; return; }";
        html += "  var datos = datosComp[prefijo + select.value];";
        html += "  if (!datos) { infoBox.innerHTML = '<i>Sin datos</i>'; return; }";
        html += "  var txt = '<ul>';";
        html += "  for (var i = 0; i < datos.length; i++) txt += '<li>' + datos[i] + '</li>';";
        html += "  txt += '</ul>';";
        html += "  infoBox.innerHTML = txt;";
        html += "}";

        html += "var contadorDeriv = 1;";
        html += "function agregarDerivador() {";
        html += "  contadorDeriv++;";
        html += "  var contenedor = document.getElementById('contenedor-derivadores');";
        html += "  var nuevoGrupo = document.createElement('div');";
        html += "  var idInfoNueva = 'info-deriv-' + contadorDeriv;";
        html += "  var label = document.createElement('label');";
        html += "  label.textContent = 'Derivador ' + contadorDeriv + ' (Adicional)';";
        html += "  nuevoGrupo.appendChild(label);";
        html += "  var selectOriginal = document.getElementById('base-derivador');";
        html += "  var nuevoSelect = selectOriginal.cloneNode(true);";
        html += "  nuevoSelect.id = ''; nuevoSelect.value = '';";
        html += "  (function(idTarget) {";
        html += "    nuevoSelect.onchange = function() { mostrarInfo(this, idTarget, 'd'); };";
        html += "  })(idInfoNueva);";
        html += "  nuevoGrupo.appendChild(nuevoSelect);";
        html += "  var divInfo = document.createElement('div');";
        html += "  divInfo.id = idInfoNueva; divInfo.className = 'info-lista';";
        html += "  nuevoGrupo.appendChild(divInfo);";
        html += "  contenedor.appendChild(nuevoGrupo);";
        html += "}";
        html += "</script>";

        html += "</head><body>";

        // Navbar (sin Mi Edificio)
        html += "<div class='navbar'>";
        html += "<span class='logo'>📡 Simulador TV</span>";
        html += "<a href='componentes'>Ingresar Componentes</a>";
        html += "<a href='calcular' class='activo'>Calcular Plantas</a>";
        html += "<a href='historial'>Historial</a>";
        html += "<button onclick='cerrarApp()' style='margin-left:auto; background:#e33629; color:white; border:none; padding:10px 14px; border-radius:8px; cursor:pointer; font-size:1.1rem;'>Salir</button>";
        html += "</div>";

        html += "<div class='content'><div class='contenedor-calculo'><h1>Calcular Plantas</h1>";

        if (mensaje != null && !mensaje.equals("")) {
            html += "<div class='mensaje'>" + escapar(mensaje) + "</div>";
        }

        html += "<div class='layout-calculo'>";

        // ── Columna izquierda: formulario ──
        html += "<div class='config-card'><h2>Configuraci&oacute;n</h2><hr>";
        html += "<form action='ejecutarCalculo' method='GET'>";

        html += "<div class='seccion-titulo'>Equipamiento</div>";

        // Cable
        html += "<label>Cable Coaxial</label>";
        html += "<select name='cable' onchange=\"mostrarInfo(this,'info-cable','c')\" required>";
        html += "<option value=''>-- Seleccionar --</option>";
        for (Componente c : cables) {
            String sel = (selCable != null && selCable.equals(String.valueOf(c.getId()))) ? " selected" : "";
            html += "<option value='" + c.getId() + "'" + sel + ">" + escapar(c.getModelo()) + "</option>";
        }
        html += "</select><div id='info-cable' class='info-lista'></div>";

        // Distribuidor
        html += "<label>Distribuidor</label>";
        html += "<select name='distribuidor' onchange=\"mostrarInfo(this,'info-dist','di')\" required>";
        html += "<option value=''>-- Seleccionar --</option>";
        for (Componente c : distribuidores) {
            String sel = (selDistribuidor != null && selDistribuidor.equals(String.valueOf(c.getId()))) ? " selected" : "";
            html += "<option value='" + c.getId() + "'" + sel + ">" + escapar(c.getModelo()) + "</option>";
        }
        html += "</select><div id='info-dist' class='info-lista'></div>";

        // Toma
        html += "<label>Base de Toma</label>";
        html += "<select name='toma' onchange=\"mostrarInfo(this,'info-toma','t')\" required>";
        html += "<option value=''>-- Seleccionar --</option>";
        for (Componente c : tomas) {
            String sel = (selToma != null && selToma.equals(String.valueOf(c.getId()))) ? " selected" : "";
            html += "<option value='" + c.getId() + "'" + sel + ">" + escapar(c.getModelo()) + "</option>";
        }
        html += "</select><div id='info-toma' class='info-lista'></div>";

        // Derivador principal
        html += "<div id='contenedor-derivadores'>";
        html += "<label>Derivador 1 (Principal)</label>";
        html += "<select name='derivadores' id='base-derivador' onchange=\"mostrarInfo(this,'info-deriv-1','d')\" required>";
        html += "<option value=''>-- Seleccionar --</option>";
        for (Componente c : derivadores) {
            String sel = (selDerivadores != null && selDerivadores.length > 0
                && selDerivadores[0].equals(String.valueOf(c.getId()))) ? " selected" : "";
            html += "<option value='" + c.getId() + "'" + sel + ">" + escapar(c.getModelo()) + "</option>";
        }
        html += "</select><div id='info-deriv-1' class='info-lista'></div>";

        // Derivadores adicionales
        if (selDerivadores != null && selDerivadores.length > 1) {
            for (int i = 1; i < selDerivadores.length; i++) {
                final String idSelDeriv = selDerivadores[i];
                html += "<div><label>Derivador " + (i + 1) + " (Adicional)</label>";
                html += "<select name='derivadores' onchange=\"mostrarInfo(this,'info-deriv-extra-" + i + "','d')\">";
                html += "<option value=''>-- Seleccionar --</option>";
                for (Componente c : derivadores) {
                    String sel = idSelDeriv.equals(String.valueOf(c.getId())) ? " selected" : "";
                    html += "<option value='" + c.getId() + "'" + sel + ">" + escapar(c.getModelo()) + "</option>";
                }
                html += "</select><div id='info-deriv-extra-" + i + "' class='info-lista'></div></div>";
            }
        }

        html += "</div>";
        html += "<button type='button' class='btn-agregar' onclick='agregarDerivador()'>+ A&ntilde;adir Derivador adicional</button>";

        html += "<div class='seccion-titulo'>Dimensiones y Distancias</div>";
        html += "<div class='grid-2'>";

        // Campos vacíos por defecto, repoblados si ya se calculó
        String vCabecera    = (valCabecera    != null && !valCabecera.isEmpty())    ? valCabecera    : "";
        String vDistPlantas = (valDistPlantas != null && !valDistPlantas.isEmpty()) ? valDistPlantas : "";
        String vDistDerDist = (valDistDerDist != null && !valDistDerDist.isEmpty()) ? valDistDerDist : "";
        String vDistDistToma= (valDistDistToma!= null && !valDistDistToma.isEmpty())? valDistDistToma: "";

        html += "<div><label>Nivel Cabecera (dB&micro;V)</label><input type='number' name='cabecera' value='" + vCabecera + "' placeholder='Ej: 105' required></div>";
        html += "<div><label>Dist. Plantas (m)</label><input type='number' name='dist_plantas' value='" + vDistPlantas + "' placeholder='Ej: 3' required></div>";
        html += "</div>";
        html += "<label>Derivador &rarr; Distribuidor (m)</label><input type='number' name='dist_der_dist' value='" + vDistDerDist + "' placeholder='Ej: 15' required>";
        html += "<label>Distribuidor &rarr; Toma (m)</label><input type='number' name='dist_dist_toma' value='" + vDistDistToma + "' placeholder='Ej: 10' required>";

        html += "<button type='submit' class='btn-calcular'>Iniciar C&aacute;lculo</button>";
        html += "</form></div>";

        // ── Columna derecha: resultados ──
        html += "<div class='resultado-card'>";
        html += "<h2>Esquema de Distribuci&oacute;n</h2><hr>";

        if (maxPlantasResult != null && nivelesPorPlanta != null) {

            if (logsCambios != null && !logsCambios.isEmpty()) {
                html += "<div class='info-cambios-box'><strong>📍 Cambios de Modelo:</strong>";
                html += "<ul style='margin:10px 0 0 0; padding-left:15px;'>";
                for (String log : logsCambios) html += "<li>" + escapar(log) + "</li>";
                html += "</ul></div>";
            }

            html += "<div class='esquema-container'>";
            html += "<div class='bloque-cabecera'>CABECERA</div>";
            html += "<div class='troncal'></div>";

            for (int i = 0; i < maxPlantasResult; i++) {
                html += "<div class='planta-row'>";
                html += "<div class='caja-planta'>";
                html += "<strong>PLANTA " + (i + 1) + "</strong><br>";
                html += "<small>Mod: " + escapar(modelosDerivPorPlanta.get(i)) + "</small><br>";
                html += "<span class='nivel-valor'>" + String.format("%.2f", nivelesPorPlanta.get(i)) + " dB&micro;V</span>";
                html += "</div>";
                html += "<div class='dot'></div>";
                html += "</div>";
            }
            html += "</div>";

            // Caja de guardar (solo aparece tras el cálculo)
            if (nombreDefecto != null) {
                html += "<div class='guardar-box'>";
                html += "<h3>💾 Guardar en Historial</h3>";
                html += "<form action='guardarEdificio' method='GET'>";
                
                // Nuevos campos obligatorios en la derecha
                html += "<div class='grid-2'>";
                html += "  <div><label>Nº Viviendas / Planta</label>";
                html += "  <input type='number' name='viv_planta' placeholder='Ej: 4' required></div>";
                html += "  <div><label>Nº Tomas / Vivienda</label>";
                html += "  <input type='number' name='tomas_viv' placeholder='Ej: 2' required></div>";
                html += "</div>";
                
                html += "<label for='nombre'>Nombre del edificio</label>";
                html += "<input type='text' id='nombre' name='nombre' value='" + escapar(nombreDefecto) + "' required>";

                // Campos ocultos
                html += "<input type='hidden' name='numPlantas'   value='" + maxPlantasResult + "'>";
                html += "<input type='hidden' name='nivelPrimera' value='" + nivelPrimera + "'>";
                html += "<input type='hidden' name='nivelUltima'  value='" + nivelUltima + "'>";
                html += "<input type='hidden' name='precioTotal'  value='" + precioTotal + "'>";
                html += "<input type='hidden' name='cable'        value='" + selCable + "'>";
                html += "<input type='hidden' name='distribuidor' value='" + selDistribuidor + "'>";
                html += "<input type='hidden' name='toma'         value='" + selToma + "'>";
                html += "<input type='hidden' name='idsDerivadores' value='" + idsDerivStr + "'>";
                html += "<input type='hidden' name='cabecera'       value='" + valCabecera + "'>";
                html += "<input type='hidden' name='dist_plantas'   value='" + valDistPlantas + "'>";
                html += "<input type='hidden' name='dist_der_dist'  value='" + valDistDerDist + "'>";
                html += "<input type='hidden' name='dist_dist_toma' value='" + valDistDistToma + "'>";

                html += "<button type='submit' class='btn-guardar'>Confirmar y Guardar</button>";
                html += "</form></div>";
            }

        } else {
            html += "<div class='resultado-vacio'>Rellene los datos y pulse calcular para generar el esquema vertical.</div>";
        }

        html += "</div>";
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
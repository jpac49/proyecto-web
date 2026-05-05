import java.util.ArrayList;

public class CalculoHTML {

    public static String generarPagina(
        ArrayList<Componente> cables,
        ArrayList<Componente> tomas,
        ArrayList<Componente> distribuidores,
        ArrayList<Componente> derivadores,
        String mensaje,
        Integer maxPlantasResult
    ) {

        String html = "";
        html += "<!DOCTYPE html><html lang='es'><head><meta charset='UTF-8'>";
        html += "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>";
        html += "<title>Calcular Plantas - Simulador ICT</title>";

        // ── ESTILOS (misma base que ComponentesHTML) ──────────────────────────
        html += "<style>";
        html += "body { font-family: Arial, sans-serif; margin: 0; background-color: #f5f5f5; }";

        // Navbar — idéntica
        html += ".navbar { background: #333; color: white; padding: 1rem 1.5rem; display: flex; align-items: center; gap: 35px; }";
        html += ".navbar .logo { font-weight: bold; font-size: 1.4rem; margin-right: 20px; }";
        html += ".navbar a { color: white; text-decoration: none; padding: 10px 14px; border-radius: 8px; font-size: 1.1rem; }";
        html += ".navbar a:hover { background: #555; }";
        html += ".navbar a.activo { background: #5a5a5a; }";
        html += ".salir { margin-left: auto; background: #e33629; }";

        // Layout
        html += ".content { padding: 30px; }";
        html += ".contenedor-calculo { max-width: 1250px; margin: 0 auto; }";
        html += ".layout-calculo { display: grid; grid-template-columns: 480px 1fr; gap: 28px; align-items: start; }";

        // Cards — mismas que .form-card / .galeria-card
        html += ".config-card, .resultado-card { background: white; border-radius: 16px; padding: 28px; border: 1px solid #ddd; box-shadow: 0 2px 8px rgba(0,0,0,0.04); }";

        // Tipografía
        html += "h1 { font-size: 2.2rem; margin-bottom: 28px; }";
        html += "h2 { margin-top: 0; }";
        html += "hr { border: none; border-top: 1px solid #ddd; margin: 20px 0; }";
        html += "label { display: block; margin-top: 18px; margin-bottom: 6px; font-weight: bold; }";
        html += "input, select { width: 100%; padding: 12px; font-size: 1rem; box-sizing: border-box; border: 1px solid #ccc; border-radius: 10px; }";

        // Sección interna
        html += ".seccion-titulo { background: #f1f1f1; padding: 8px 12px; border-radius: 8px; margin: 22px 0 6px 0; font-weight: bold; font-size: 0.95rem; color: #333; }";

        // Info box bajo cada selector
        html += ".info-lista { background: #f9f9f9; border: 1px solid #e3e3e3; padding: 10px 14px; border-radius: 8px; margin-top: 8px; font-size: 0.85rem; min-height: 36px; color: #555; }";
        html += ".info-lista ul { margin: 0; padding-left: 18px; }";
        html += ".info-lista li { margin-bottom: 3px; }";

        // Botones
        html += ".btn-principal { margin-top: 25px; width: 100%; background: #333; color: white; border: none; font-weight: bold; padding: 14px; font-size: 1.05rem; border-radius: 8px; cursor: pointer; }";
        html += ".btn-principal:hover { background: #555; }";
        html += ".btn-calcular { margin-top: 25px; width: 100%; background: #1a73e8; color: white; border: none; font-weight: bold; padding: 14px; font-size: 1.05rem; border-radius: 8px; cursor: pointer; }";
        html += ".btn-calcular:hover { background: #1557b0; }";
        html += ".btn-agregar { margin-top: 14px; background: #f1f1f1; border: 1px solid #ccc; padding: 9px 14px; border-radius: 8px; cursor: pointer; font-size: 0.95rem; font-weight: bold; width: 100%; }";
        html += ".btn-agregar:hover { background: #e3e3e3; }";

        // Grid 2 columnas para campos numéricos
        html += ".grid-2 { display: grid; grid-template-columns: 1fr 1fr; gap: 14px; }";

        // Mensaje
        html += ".mensaje { background: #fff3cd; padding: 12px; border-radius: 8px; border: 1px solid #ffecb5; margin-bottom: 20px; }";

        // Resultado
        html += ".resultado-vacio { text-align: center; color: #888; padding: 80px 40px; border: 2px dashed #ddd; border-radius: 12px; margin-top: 10px; font-size: 1rem; }";
        html += ".resultado-ok { background: #f0f7ff; border: 1px solid #c8e0ff; border-radius: 12px; padding: 28px; text-align: center; margin-top: 10px; }";
        html += ".resultado-ok .numero { font-size: 4rem; font-weight: bold; color: #1a73e8; margin: 0; }";
        html += ".resultado-ok .etiqueta { font-size: 1.1rem; color: #444; margin-top: 6px; }";

        html += "</style>";

        // ── JAVASCRIPT ────────────────────────────────────────────────────────
        html += "<script>";

        // Datos de componentes para mostrar info sin recargar
        html += "var datosComp = {";
        for (Componente c : cables)
            html += "'c" + c.getId() + "': ['Aten. 470 MHz: " + c.getAt470() + " dB/100m', 'Aten. 862 MHz: " + c.getAt862() + " dB/100m'],";
        for (Componente c : tomas)
            html += "'t" + c.getId() + "': ['Aten. Derivaci\u00f3n: " + c.getAtDerivacion() + " dB'],";
        for (Componente c : derivadores)
            html += "'d" + c.getId() + "': ['Aten. Paso: " + c.getAtPaso() + " dB', 'Aten. Salida: " + c.getAtSalida() + " dB', 'Salidas: " + c.getNumSalidas() + "'],";
        for (Componente c : distribuidores)
            html += "'di" + c.getId() + "': ['Aten. Salida: " + c.getAtSalida() + " dB', 'Salidas: " + c.getNumSalidas() + "'],";
        html += "};";

        html += "function mostrarInfo(select, idContenedor, prefijo) {";
        html += "  var infoBox = document.getElementById(idContenedor);";
        html += "  var datos = datosComp[prefijo + select.value];";
        html += "  if (!datos) { infoBox.innerHTML = ''; return; }";
        html += "  var txt = '<ul>';";
        html += "  for (var i = 0; i < datos.length; i++) txt += '<li>' + datos[i] + '</li>';";
        html += "  txt += '</ul>';";
        html += "  infoBox.innerHTML = txt;";
        html += "}";

        html += "var numDeriv = 1;";
        html += "function agregarDerivador() {";
        html += "  numDeriv++;";
        html += "  var contenedor = document.getElementById('contenedor-derivadores');";
        html += "  var div = document.createElement('div');";
        html += "  var idInfo = 'info-deriv-' + numDeriv;";
        html += "  var lbl = document.createElement('label');";
        html += "  lbl.textContent = 'Derivador ' + numDeriv + ':';";
        html += "  div.appendChild(lbl);";
        html += "  var base = document.getElementById('base-derivador');";
        html += "  var sel = base.cloneNode(true);";
        html += "  sel.id = '';";
        html += "  sel.value = '';";
        html += "  (function(infoId){ sel.onchange = function(){ mostrarInfo(this, infoId, 'd'); }; })(idInfo);";
        html += "  div.appendChild(sel);";
        html += "  var infoDiv = document.createElement('div');";
        html += "  infoDiv.id = idInfo;";
        html += "  infoDiv.className = 'info-lista';";
        html += "  div.appendChild(infoDiv);";
        html += "  contenedor.appendChild(div);";
        html += "}";

        html += "</script>";

        // ── HTML ──────────────────────────────────────────────────────────────
        html += "</head><body>";

        // Navbar
        html += "<div class='navbar'>";
        html += "<span class='logo'>📡 Simulador TV</span>";
        html += "<a href='componentes'>Ingresar Componentes</a>";
        html += "<a href='calcular' class='activo'>Calcular Plantas</a>";
        html += "<a href='#'>Mi Edificio</a>";
        html += "<a href='#'>Historial</a>";
        html += "<a href='#' class='salir'>Salir</a>";
        html += "</div>";

        html += "<div class='content'><div class='contenedor-calculo'>";
        html += "<h1>Calcular Plantas</h1>";

        if (mensaje != null && !mensaje.equals("")) {
            html += "<div class='mensaje'>" + escapar(mensaje) + "</div>";
        }

        html += "<div class='layout-calculo'>";

        // ── Columna izquierda: formulario ──
        html += "<div class='config-card'>";
        html += "<h2>Configuraci&oacute;n</h2><hr>";
        html += "<form action='ejecutarCalculo' method='GET'>";

        // Equipamiento
        html += "<div class='seccion-titulo'>Equipamiento</div>";

        html += "<label>Cable Coaxial</label>";
        html += "<select name='cable' onchange=\"mostrarInfo(this,'info-cable','c')\" required>";
        html += "<option value=''>-- Seleccionar --</option>";
        for (Componente c : cables)
            html += "<option value='" + c.getId() + "'>" + escapar(c.getModelo()) + "</option>";
        html += "</select><div id='info-cable' class='info-lista'></div>";

        html += "<label>Distribuidor por Vivienda</label>";
        html += "<select name='distribuidor' onchange=\"mostrarInfo(this,'info-dist','di')\" required>";
        html += "<option value=''>-- Seleccionar --</option>";
        for (Componente c : distribuidores)
            html += "<option value='" + c.getId() + "'>" + escapar(c.getModelo()) + "</option>";
        html += "</select><div id='info-dist' class='info-lista'></div>";

        html += "<label>Base de Toma</label>";
        html += "<select name='toma' onchange=\"mostrarInfo(this,'info-toma','t')\" required>";
        html += "<option value=''>-- Seleccionar --</option>";
        for (Componente c : tomas)
            html += "<option value='" + c.getId() + "'>" + escapar(c.getModelo()) + "</option>";
        html += "</select><div id='info-toma' class='info-lista'></div>";

        html += "<div id='contenedor-derivadores'>";
        html += "<label>Derivador 1</label>";
        html += "<select name='derivadores' id='base-derivador' onchange=\"mostrarInfo(this,'info-deriv-1','d')\" required>";
        html += "<option value=''>-- Seleccionar --</option>";
        for (Componente c : derivadores)
            html += "<option value='" + c.getId() + "'>" + escapar(c.getModelo()) + "</option>";
        html += "</select><div id='info-deriv-1' class='info-lista'></div>";
        html += "</div>";

        html += "<button type='button' class='btn-agregar' onclick='agregarDerivador()'>+ A&ntilde;adir Derivador adicional</button>";

        // Dimensiones del edificio
        html += "<div class='seccion-titulo'>Dimensiones del Edificio</div>";

        html += "<div class='grid-2'>";
        html += "<div><label>Nivel Cabecera (dB&micro;V)</label><input type='number' name='cabecera' value='105' required></div>";
        html += "<div><label>Dist. entre Plantas (m)</label><input type='number' name='dist_plantas' value='3' required></div>";
        html += "</div>";

        html += "<div class='seccion-titulo'>Dentro de la Vivienda</div>";

        html += "<label>Distancia Derivador &rarr; Distribuidor (m)</label>";
        html += "<input type='number' name='dist_der_dist' value='15' required>";

        html += "<div class='grid-2'>";
        html += "<div><label>N&ordm; Tomas / Vivienda</label><input type='number' name='num_tomas' value='2' required></div>";
        html += "<div><label>Dist. Distribuidor &rarr; Toma (m)</label><input type='number' name='dist_dist_toma' value='10' required></div>";
        html += "</div>";

        html += "<button type='submit' class='btn-calcular'>Iniciar C&aacute;lculo</button>";
        html += "</form></div>";

        // ── Columna derecha: resultado ──
        html += "<div class='resultado-card'>";
        html += "<h2>Resultado</h2><hr>";

        if (maxPlantasResult != null) {
            html += "<div class='resultado-ok'>";
            html += "<p class='numero'>" + maxPlantasResult + "</p>";
            html += "<p class='etiqueta'>plantas cumplen los niveles m&iacute;nimos<br>a 470 MHz y 862 MHz</p>";
            html += "</div>";
        } else {
            html += "<div class='resultado-vacio'>Configure los par&aacute;metros a la izquierda<br>y pulse <b>Iniciar C&aacute;lculo</b> para ver el resultado.</div>";
        }

        html += "</div>";

        html += "</div></div></div></body></html>";

        return html;
    }

    private static String escapar(String texto) {
        if (texto == null) return "";
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;")
                    .replace("á", "&aacute;").replace("é", "&eacute;").replace("í", "&iacute;")
                    .replace("ó", "&oacute;").replace("ú", "&uacute;").replace("ñ", "&ntilde;");
    }
}
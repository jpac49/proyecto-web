import java.util.ArrayList;

public class ComponentesHTML {

    public static String generarPagina(ArrayList<Componente> componentes, String mensaje) {

        String html = "";

        html += "<!DOCTYPE html>";
        html += "<html lang='es'>";
        html += "<head>";
        html += "<meta charset='UTF-8'>";
        html += "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>";
        html += "<title>Componentes - Simulador ICT</title>";

        html += "<style>";
        html += "body { font-family: Arial, sans-serif; margin: 0; background-color: #f5f5f5; }";
        html += ".navbar { background: #333; color: white; padding: 1rem 1.5rem; display: flex; align-items: center; gap: 35px; }";
        html += ".navbar .logo { font-weight: bold; font-size: 1.4rem; margin-right: 20px; }";
        html += ".navbar a { color: white; text-decoration: none; padding: 10px 14px; border-radius: 8px; font-size: 1.1rem; }";
        html += ".navbar a:hover { background: #555; }";
        html += ".navbar a.activo { background: #5a5a5a; }";
        html += ".salir { margin-left: auto; background: #e33629; }";
        html += ".content { padding: 30px; }";
        html += ".contenedor-componentes { max-width: 1250px; margin: 0 auto; }";
        html += ".layout-componentes { display: grid; grid-template-columns: 420px 1fr; gap: 28px; align-items: start; }";
        html += ".form-card, .galeria-card { background: white; border-radius: 16px; padding: 28px; border: 1px solid #ddd; box-shadow: 0 2px 8px rgba(0,0,0,0.04); }";
        html += "h1 { font-size: 2.2rem; margin-bottom: 28px; }";
        html += "h2 { margin-top: 0; }";
        html += "hr { border: none; border-top: 1px solid #ddd; margin: 20px 0; }";
        html += "label { display: block; margin-top: 18px; margin-bottom: 6px; font-weight: bold; }";
        html += "input, select { width: 100%; padding: 12px; font-size: 1rem; box-sizing: border-box; border: 1px solid #ccc; border-radius: 10px; }";
        html += ".bloque-extra { margin-top: 25px; padding-top: 12px; border-top: 1px solid #e3e3e3; }";
        html += ".oculto { display: none; }";
        html += ".btn-principal { margin-top: 25px; width: 100%; background: #333; color: white; border: none; font-weight: bold; padding: 14px; font-size: 1.05rem; border-radius: 8px; cursor: pointer; }";
        html += ".btn-principal:hover { background: #555; }";
        html += ".galeria-grid { display: grid; grid-template-columns: repeat(2, minmax(230px, 1fr)); gap: 18px; margin-top: 22px; }";
        html += ".tarjeta-componente { background: #f1f1f1; border-radius: 14px; padding: 18px; border: 1px solid #ddd; }";
        html += ".etiqueta-tipo { display: inline-block; background: #333; color: white; font-size: 0.8rem; padding: 5px 10px; border-radius: 20px; margin-bottom: 12px; }";
        html += ".tarjeta-componente h3 { margin: 0 0 12px 0; font-size: 1.5rem; }";
        html += ".fila-dato { display: flex; justify-content: space-between; gap: 12px; border-bottom: 1px dashed #ccc; padding: 7px 0; font-size: 0.95rem; }";
        html += ".fila-dato span:first-child { font-weight: bold; }";
        html += ".mensaje { background: #fff3cd; padding: 12px; border-radius: 8px; border: 1px solid #ffecb5; margin-bottom: 20px; }";
        html += ".vacio { color: #666; font-style: italic; }";
        html += ".btn-eliminar { margin-top: 15px; width: 100%; background: #d93025; color: white; border: none; font-weight: bold; padding: 10px; border-radius: 8px; cursor: pointer; font-size: 0.95rem; }";
        html += ".btn-eliminar:hover { background: #b3261e; }";
        html += ".id-pequeno { font-size: 0.85rem; color: #777; margin-bottom: 10px; }";
        html += "</style>";

        html += "<script>";
        html += "function cerrarApp() {";
        html += "  if (confirm('¿Desea cerrar la aplicación?')) {";
        html += "    window.close();";
        html += "    window.location.href = 'about:blank';";
        html += "  }";
        html += "}";
        html += "  function cambiarTipoComponente() {";
        html += "    var tipo = document.getElementById('tipo').value;";
        html += "    var cCable = document.getElementById('camposCable');";
        html += "    var cToma = document.getElementById('camposToma');";
        html += "    var cDeriv = document.getElementById('camposDerivador');";
        html += "    var cDist = document.getElementById('camposDistribucion');";
        html += "    var labelPrecio = document.getElementById('labelPrecio');"; // Referencia a la etiqueta
        html += "    cCable.classList.add('oculto'); cToma.classList.add('oculto');";
        html += "    cDeriv.classList.add('oculto'); cDist.classList.add('oculto');";
        html += "    if (tipo === 'Cable Coaxial') {";
        html += "       cCable.classList.remove('oculto');";
        html += "       labelPrecio.innerHTML = 'Precio por metro (&euro;/m)';"; // Cambio a precio por metro
        html += "    } else {";
        html += "       labelPrecio.innerHTML = 'Precio (&euro;)';"; // Cambio a precio normal
        html += "       if (tipo === 'Toma') cToma.classList.remove('oculto');";
        html += "       else if (tipo === 'Derivador') cDeriv.classList.remove('oculto');";
        html += "       else if (tipo === 'Distribuidor') cDist.classList.remove('oculto');";
        html += "    }";
        html += "  }";
        html += "</script>";

        html += "</head>";
        html += "<body>";

        html += "<div class='navbar'>";
        html += "<span class='logo'>📡 Simulador TV</span>";
        html += "<a href='componentes' class='activo'>Ingresar Componentes</a>";
        html += "<a href='calcular'>Calcular Plantas</a>";
        html += "<a href='historial'>Historial</a>";
        html += "<button onclick='cerrarApp()' style='margin-left:auto; background:#e33629; color:white; border:none; padding:10px 14px; border-radius:8px; cursor:pointer; font-size:1.1rem;'>Salir</button>";
        html += "</div>";

        html += "<div class='content'>";
        html += "<div class='contenedor-componentes'>";
        html += "<h1>Ingresar Componentes</h1>";

        if (mensaje != null && !mensaje.equals("")) {
            html += "<div class='mensaje'>" + escapar(mensaje) + "</div>";
        }

        html += "<div class='layout-componentes'>";
        html += "<div class='form-card'>";
        html += "<h2>A&ntilde;adir Nuevo</h2>";
        html += "<hr>";
        html += "<form action='guardarComponente' method='POST'>";
        
        html += "<label for='tipo'>Tipo</label>";
        html += "<select name='tipo' id='tipo' onchange='cambiarTipoComponente()' required>";
        html += "<option value=''>-- Seleccionar --</option>";
        html += "<option value='Cable Coaxial'>Cable Coaxial</option>";
        html += "<option value='Toma'>Toma</option>";
        html += "<option value='Derivador'>Derivador</option>";
        html += "<option value='Distribuidor'>Distribuidor</option>";
        html += "</select>";

        html += "<label for='modelo'>Modelo</label>";
        html += "<input type='text' id='modelo' name='modelo' placeholder='Ej: CE-752' required>";

        // Etiqueta con ID para que JavaScript la modifique
        html += "<label id='labelPrecio' for='precio'>Precio (&euro;)</label>";
        html += "<input type='number' step='0.01' id='precio' name='precio' placeholder='Ej: 3' required>";

        // Bloques dinámicos actualizados
        html += "<div id='camposCable' class='bloque-extra oculto'>";
        html += "<label>Aten. @470 MHz (dB/100m)</label><input type='number' step='0.01' name='at470' placeholder='Ej: 12.3'>";
        html += "<label>Aten. @862 MHz (dB/100m)</label><input type='number' step='0.01' name='at862' placeholder='Ej: 18.7'>";
        html += "</div>";

        html += "<div id='camposToma' class='bloque-extra oculto'>";
        html += "<label>Aten. Derivaci&oacute;n (dB)</label><input type='number' step='0.01' name='atDerivacion' placeholder='Ej: 1.5'>";
        html += "</div>";

        html += "<div id='camposDerivador' class='bloque-extra oculto'>";
        html += "<label>N&ordm; Salidas</label><input type='number' name='numSalidas' placeholder='Ej: 2'>";
        html += "<label>Aten. Salida (dB)</label><input type='number' step='0.01' name='atSalida' placeholder='Ej: 15.0'>";
        html += "<label>Aten. Paso (dB)</label><input type='number' step='0.01' name='atPaso' placeholder='Ej: 1.2'>";
        html += "</div>";

        html += "<div id='camposDistribucion' class='bloque-extra oculto'>";
        html += "<label>N&ordm; Salidas</label><input type='number' name='numSalidasDist' placeholder='Ej: 4'>"; 
        html += "<label>Aten. Salida (dB)</label><input type='number' step='0.01' name='atSalidaDist' placeholder='Ej: 7.5'>"; 
        html += "</div>";

        html += "<button type='submit' class='btn-principal'>Guardar Componente</button>";
        html += "</form></div>";

        html += "<div class='galeria-card'><h2>Galer&iacute;a</h2><hr>";
        if (componentes.size() == 0) {
            html += "<p class='vacio'>No hay componentes.</p>";
        } else {
            html += "<div class='galeria-grid'>";
            for (Componente c : componentes) {
                html += "<div class='tarjeta-componente'>";
                html += "<div class='etiqueta-tipo'>" + escapar(c.getTipo()) + "</div>";
                html += "<h3>" + escapar(c.getModelo()) + "</h3>";
                
                // Muestra la unidad correcta en la galería según el tipo
                String unidadPrecio = "Cable Coaxial".equals(c.getTipo()) ? " &euro;/m" : " &euro;";
                html += "<div class='fila-dato'><span>Precio</span><span>" + mostrarDouble(c.getPrecio()) + unidadPrecio + "</span></div>";

                if ("Cable Coaxial".equals(c.getTipo())) {
                    html += "<div class='fila-dato'><span>Aten. 470MHz</span><span>" + mostrarDouble(c.getAt470()) + " dB/100m</span></div>";
                    html += "<div class='fila-dato'><span>Aten. 862MHz</span><span>" + mostrarDouble(c.getAt862()) + " dB/100m</span></div>";
                } else if ("Toma".equals(c.getTipo())) {
                    html += "<div class='fila-dato'><span>Aten. Deriv.</span><span>" + mostrarDouble(c.getAtDerivacion()) + " dB</span></div>";
                } else if ("Derivador".equals(c.getTipo())) {
                    html += "<div class='fila-dato'><span>Salidas</span><span>" + c.getNumSalidas() + "</span></div>";
                    html += "<div class='fila-dato'><span>Aten. Salida</span><span>" + mostrarDouble(c.getAtSalida()) + " dB</span></div>";
                    html += "<div class='fila-dato'><span>Aten. Paso</span><span>" + mostrarDouble(c.getAtPaso()) + " dB</span></div>";
                } else if ("Distribuidor".equals(c.getTipo())) {
                    html += "<div class='fila-dato'><span>Salidas</span><span>" + c.getNumSalidas() + "</span></div>";
                    html += "<div class='fila-dato'><span>Aten. Salida</span><span>" + mostrarDouble(c.getAtSalida()) + " dB</span></div>";
                }

                html += "<form action='eliminarComponente' method='POST' onsubmit=\"return confirm('¿Borrar?');\">";
                html += "<input type='hidden' name='id' value='" + c.getId() + "'>";
                html += "<button type='submit' class='btn-eliminar'>Eliminar</button></form>";
                html += "</div>";
            }
            html += "</div>";
        }
        html += "</div></div></div></div></body></html>";

        return html;
    }

    private static String mostrarDouble(Double valor) {
        return (valor == null) ? "-" : String.valueOf(valor);
    }

    private static String escapar(String texto) {
        if (texto == null) return "";
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;")
                    .replace("á", "&aacute;").replace("é", "&eacute;").replace("í", "&iacute;")
                    .replace("ó", "&oacute;").replace("ú", "&uacute;").replace("ñ", "&ntilde;");
    }
}
import java.util.ArrayList;

public class ComponentesHTML {

    public static String generarPagina(ArrayList<Componente> componentes, String mensaje) {

        String html = "";

        html += "<!DOCTYPE html>";
        html += "<html lang='es'>";
        html += "<head>";
        html += "<meta charset='UTF-8'>";
        html += "<title>Componentes - Simulador ICT</title>";

        html += "<style>";

        html += "body {";
        html += "font-family: Arial, sans-serif;";
        html += "margin: 0;";
        html += "background-color: #f5f5f5;";
        html += "}";

        html += ".navbar {";
        html += "background: #333;";
        html += "color: white;";
        html += "padding: 1rem 1.5rem;";
        html += "display: flex;";
        html += "align-items: center;";
        html += "gap: 35px;";
        html += "}";

        html += ".navbar .logo {";
        html += "font-weight: bold;";
        html += "font-size: 1.4rem;";
        html += "margin-right: 20px;";
        html += "}";

        html += ".navbar a {";
        html += "color: white;";
        html += "text-decoration: none;";
        html += "padding: 10px 14px;";
        html += "border-radius: 8px;";
        html += "font-size: 1.1rem;";
        html += "}";

        html += ".navbar a:hover {";
        html += "background: #555;";
        html += "}";

        html += ".navbar a.activo {";
        html += "background: #5a5a5a;";
        html += "}";

        html += ".salir {";
        html += "margin-left: auto;";
        html += "background: #e33629;";
        html += "}";

        html += ".content {";
        html += "padding: 30px;";
        html += "}";

        html += ".contenedor-componentes {";
        html += "max-width: 1250px;";
        html += "margin: 0 auto;";
        html += "}";

        html += ".layout-componentes {";
        html += "display: grid;";
        html += "grid-template-columns: 420px 1fr;";
        html += "gap: 28px;";
        html += "align-items: start;";
        html += "}";

        html += ".form-card, .galeria-card {";
        html += "background: white;";
        html += "border-radius: 16px;";
        html += "padding: 28px;";
        html += "border: 1px solid #ddd;";
        html += "box-shadow: 0 2px 8px rgba(0,0,0,0.04);";
        html += "}";

        html += "h1 {";
        html += "font-size: 2.2rem;";
        html += "margin-bottom: 28px;";
        html += "}";

        html += "h2 {";
        html += "margin-top: 0;";
        html += "}";

        html += "hr {";
        html += "border: none;";
        html += "border-top: 1px solid #ddd;";
        html += "margin: 20px 0;";
        html += "}";

        html += "label {";
        html += "display: block;";
        html += "margin-top: 18px;";
        html += "margin-bottom: 6px;";
        html += "font-weight: bold;";
        html += "}";

        html += "input, select {";
        html += "width: 100%;";
        html += "padding: 12px;";
        html += "font-size: 1rem;";
        html += "box-sizing: border-box;";
        html += "border: 1px solid #ccc;";
        html += "border-radius: 10px;";
        html += "}";

        html += ".bloque-extra {";
        html += "margin-top: 25px;";
        html += "padding-top: 12px;";
        html += "border-top: 1px solid #e3e3e3;";
        html += "}";

        html += ".oculto {";
        html += "display: none;";
        html += "}";

        html += ".btn-principal {";
        html += "margin-top: 25px;";
        html += "width: 100%;";
        html += "background: #333;";
        html += "color: white;";
        html += "border: none;";
        html += "font-weight: bold;";
        html += "padding: 14px;";
        html += "font-size: 1.05rem;";
        html += "border-radius: 8px;";
        html += "cursor: pointer;";
        html += "}";

        html += ".btn-principal:hover {";
        html += "background: #555;";
        html += "}";

        html += ".galeria-grid {";
        html += "display: grid;";
        html += "grid-template-columns: repeat(2, minmax(230px, 1fr));";
        html += "gap: 18px;";
        html += "margin-top: 22px;";
        html += "}";

        html += ".tarjeta-componente {";
        html += "background: #f1f1f1;";
        html += "border-radius: 14px;";
        html += "padding: 18px;";
        html += "border: 1px solid #ddd;";
        html += "}";

        html += ".etiqueta-tipo {";
        html += "display: inline-block;";
        html += "background: #333;";
        html += "color: white;";
        html += "font-size: 0.8rem;";
        html += "padding: 5px 10px;";
        html += "border-radius: 20px;";
        html += "margin-bottom: 12px;";
        html += "}";

        html += ".tarjeta-componente h3 {";
        html += "margin: 0 0 12px 0;";
        html += "font-size: 1.5rem;";
        html += "}";

        html += ".fila-dato {";
        html += "display: flex;";
        html += "justify-content: space-between;";
        html += "gap: 12px;";
        html += "border-bottom: 1px dashed #ccc;";
        html += "padding: 7px 0;";
        html += "font-size: 0.95rem;";
        html += "}";

        html += ".fila-dato span:first-child {";
        html += "font-weight: bold;";
        html += "}";

        html += ".mensaje {";
        html += "background: #fff3cd;";
        html += "padding: 12px;";
        html += "border-radius: 8px;";
        html += "border: 1px solid #ffecb5;";
        html += "margin-bottom: 20px;";
        html += "}";

        html += ".vacio {";
        html += "color: #666;";
        html += "font-style: italic;";
        html += "}";

        html += ".btn-eliminar {";
        html += "margin-top: 15px;";
        html += "width: 100%;";
        html += "background: #d93025;";
        html += "color: white;";
        html += "border: none;";
        html += "font-weight: bold;";
        html += "padding: 10px;";
        html += "border-radius: 8px;";
        html += "cursor: pointer;";
        html += "font-size: 0.95rem;";
        html += "}";

        html += ".btn-eliminar:hover {";
        html += "background: #b3261e;";
        html += "}";

        html += ".id-pequeno {";
        html += "font-size: 0.85rem;";
        html += "color: #777;";
        html += "margin-bottom: 10px;";
        html += "}";

        html += "</style>";

        html += "<script>";

        html += "function cambiarTipoComponente() {";
        html += "var tipo = document.getElementById('tipo').value;";

        html += "var camposCable = document.getElementById('camposCable');";
        html += "var camposToma = document.getElementById('camposToma');";

        html += "var at470 = document.getElementById('at470');";
        html += "var at862 = document.getElementById('at862');";
        html += "var atPaso = document.getElementById('atPaso');";

        html += "camposCable.classList.add('oculto');";
        html += "camposToma.classList.add('oculto');";

        html += "at470.required = false;";
        html += "at862.required = false;";
        html += "atPaso.required = false;";

        html += "if (tipo === 'Cable Coaxial') {";
        html += "camposCable.classList.remove('oculto');";
        html += "at470.required = true;";
        html += "at862.required = true;";
        html += "}";

        html += "if (tipo === 'Toma') {";
        html += "camposToma.classList.remove('oculto');";
        html += "atPaso.required = true;";
        html += "}";

        html += "}";

        html += "</script>";

        html += "</head>";

        html += "<body>";

        html += "<div class='navbar'>";
        html += "<span class='logo'>📡 Simulador TV</span>";
        html += "<a href='componentes' class='activo'>Ingresar Componentes</a>";
        html += "<a href='#'>Calcular Plantas</a>";
        html += "<a href='#'>Mi Edificio</a>";
        html += "<a href='#'>Historial</a>";
        html += "<a href='#' class='salir'>Salir</a>";
        html += "</div>";

        html += "<div class='content'>";
        html += "<div class='contenedor-componentes'>";

        html += "<h1>Ingresar Componentes</h1>";

        if (mensaje != null && !mensaje.equals("")) {
            html += "<div class='mensaje'>" + escapar(mensaje) + "</div>";
        }

        html += "<div class='layout-componentes'>";

        html += "<div class='form-card'>";
        html += "<h2>Añadir Nuevo Componente</h2>";
        html += "<hr>";

        html += "<form action='guardarComponente' method='POST'>";

        html += "<label for='tipo'>Tipo</label>";
        html += "<select name='tipo' id='tipo' onchange='cambiarTipoComponente()' required>";
        html += "<option value=''>-- Seleccionar tipo --</option>";
        html += "<option value='Cable Coaxial'>Cable Coaxial</option>";
        html += "<option value='Toma'>Toma</option>";
        html += "<option value='Derivador'>Derivador</option>";
        html += "<option value='Distribuidor'>Distribuidor</option>";
        html += "</select>";

        html += "<label for='modelo'>Modelo</label>";
        html += "<input type='text' id='modelo' name='modelo' placeholder='Ejemplo: CE-752 / Toma R-TV' required>";

        html += "<label for='precio'>Precio (€)</label>";
        html += "<input type='number' step='0.01' id='precio' name='precio' placeholder='Ejemplo: 4.50' required>";

        html += "<div id='camposCable' class='bloque-extra oculto'>";
        html += "<h3>Propiedades del Cable Coaxial</h3>";

        html += "<label for='at470'>Atenuación @470 MHz / 100m en dB</label>";
        html += "<input type='number' step='0.01' id='at470' name='at470' placeholder='Ejemplo: 12.30'>";

        html += "<label for='at862'>Atenuación @862 MHz / 100m en dB</label>";
        html += "<input type='number' step='0.01' id='at862' name='at862' placeholder='Ejemplo: 18.70'>";

        html += "</div>";

        html += "<div id='camposToma' class='bloque-extra oculto'>";
        html += "<h3>Propiedades de la Toma</h3>";

        html += "<label for='atPaso'>Atenuación de paso en dB</label>";
        html += "<input type='number' step='0.01' id='atPaso' name='atPaso' placeholder='Ejemplo: 1.20'>";

        html += "</div>";

        html += "<button type='submit' class='btn-principal'>Añadir Componente</button>";

        html += "</form>";
        html += "</div>";

        html += "<div class='galeria-card'>";
        html += "<h2>Galería de Componentes</h2>";
        html += "<hr>";

        if (componentes.size() == 0) {
            html += "<p class='vacio'>Todavía no hay componentes guardados.</p>";
        } else {
            html += "<div class='galeria-grid'>";

            for (int i = 0; i < componentes.size(); i++) {
                Componente c = componentes.get(i);

                html += "<div class='tarjeta-componente'>";

                html += "<div class='etiqueta-tipo'>" + escapar(c.getTipo()) + "</div>";
                html += "<div class='id-pequeno'>ID: " + c.getId() + "</div>";
                html += "<h3>" + escapar(c.getModelo()) + "</h3>";

                html += "<div class='fila-dato'>";
                html += "<span>Precio</span>";
                html += "<span>" + mostrarDouble(c.getPrecio()) + " &euro;</span>";
                html += "</div>";

                if ("Cable Coaxial".equals(c.getTipo())) {
                    html += "<div class='fila-dato'>";
                    html += "<span>Aten. 470 MHz</span>";
                    html += "<span>" + mostrarDouble(c.getAt470()) + " dB/100m</span>";
                    html += "</div>";

                    html += "<div class='fila-dato'>";
                    html += "<span>Aten. 862 MHz</span>";
                    html += "<span>" + mostrarDouble(c.getAt862()) + " dB/100m</span>";
                    html += "</div>";
                } else if ("Toma".equals(c.getTipo())) {
                    html += "<div class='fila-dato'>";
                    html += "<span>Aten. paso</span>";
                    html += "<span>" + mostrarDouble(c.getAtPaso()) + " dB</span>";
                    html += "</div>";
                } else {
                    html += "<div class='fila-dato'>";
                    html += "<span>Tipo</span>";
                    html += "<span>" + escapar(c.getTipo()) + "</span>";
                    html += "</div>";
                }

                html += "<form action='eliminarComponente' method='POST' ";
                html += "onsubmit=\"return confirm('¿Seguro que quieres eliminar este componente?');\">";

                html += "<input type='hidden' name='id' value='" + c.getId() + "'>";

                html += "<button type='submit' class='btn-eliminar'>";
                html += "Eliminar";
                html += "</button>";

                html += "</form>";

                html += "</div>";
            }

            html += "</div>";
        }

        html += "</div>";

        html += "</div>";
        html += "</div>";
        html += "</div>";

        html += "</body>";
        html += "</html>";

        return html;
    }

    private static String mostrarDouble(Double valor) {
        if (valor == null) {
            return "-";
        }

        return String.valueOf(valor);
    }

    private static String escapar(String texto) {
        if (texto == null) {
            return "";
        }

        texto = texto.replace("&", "&amp;");
        texto = texto.replace("<", "&lt;");
        texto = texto.replace(">", "&gt;");
        texto = texto.replace("\"", "&quot;");
        texto = texto.replace("'", "&#39;");

        return texto;
    }
}
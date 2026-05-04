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

        html += "<!DOCTYPE html>";
        html += "<html lang='es'>";
        html += "<head>";
        html += "<meta charset='UTF-8'>";
        html += "<title>Simulador TV - Calcular Plantas</title>";

        html += "<style>";
        html += "body { font-family: Arial, sans-serif; margin: 0; background-color: #f5f5f5; }";
        html += ".navbar { background: #333; color: white; padding: 1rem 1.5rem; display: flex; align-items: center; gap: 35px; }";
        html += ".navbar .logo { font-weight: bold; font-size: 1.4rem; margin-right: 20px; }";
        html += ".navbar a { color: white; text-decoration: none; padding: 10px 14px; border-radius: 8px; font-size: 1.1rem; }";
        html += ".navbar a:hover { background: #555; }";
        html += ".navbar a.activo { background: #5a5a5a; }";
        html += ".salir { margin-left: auto; background: #e33629; }";
        html += ".content { padding: 30px; }";
        html += ".contenedor-calculo { max-width: 1200px; margin: 0 auto; }";
        html += ".layout-calculo { display: grid; grid-template-columns: 450px 1fr; gap: 30px; align-items: start; }";
        html += ".config-card, .esquematico-card { background: white; border-radius: 16px; padding: 30px; border: 1px solid #ddd; box-shadow: 0 2px 8px rgba(0,0,0,0.04); }";
        html += "h1 { font-size: 2.2rem; margin-bottom: 28px; }";
        html += "h2 { margin-top: 0; color: #333; }";
        html += "hr { border: none; border-top: 1px solid #ddd; margin: 20px 0; }";
        html += "label { display: block; margin-top: 20px; margin-bottom: 8px; font-weight: bold; color: #555; }";
        html += "select, input { width: 100%; padding: 12px; font-size: 1rem; border: 1px solid #ccc; border-radius: 8px; box-sizing: border-box; }";
        html += ".btn-calcular { margin-top: 30px; width: 100%; background: #1a73e8; color: white; border: none; font-weight: bold; padding: 16px; font-size: 1.1rem; border-radius: 8px; cursor: pointer; }";
        html += ".btn-agregar { background: #eee; border: 1px solid #ccc; padding: 10px; border-radius: 8px; cursor: pointer; margin-top: 10px; width: 100%; }";
        html += ".esquematico-vacio { text-align: center; color: #888; padding: 50px; border: 2px dashed #ccc; border-radius: 10px; margin-top: 20px; }";
        html += ".resultado-box { background: #e8f0fe; color: #1967d2; padding: 20px; border-radius: 10px; border: 1px solid #d2e3fc; margin-bottom: 20px; text-align: center; }";
        html += "</style>";

        html += "<script>";
        html += "function agregarDerivador() {";
        html += "  var contenedor = document.getElementById('contenedor-derivadores');";
        html += "  var div = document.createElement('div');";
        html += "  div.innerHTML = '<label>Derivador adicional:</label>';";
        html += "  var select = document.getElementById('base-derivador').cloneNode(true);";
        html += "  select.id = '';"; // Evitar IDs duplicados
        html += "  div.appendChild(select);";
        html += "  contenedor.appendChild(div);";
        html += "}";
        html += "</script>";

        html += "</head>";
        html += "<body>";

        html += "<div class='navbar'>";
        html += "<span class='logo'>📡 Simulador TV</span>";
        html += "<a href='componentes'>Ingresar Componentes</a>"; // Ruta corregida
        html += "<a href='calcular' class='activo'>Calcular Plantas</a>"; // Ruta corregida
        html += "<a href='#'>Mi Edificio</a>";
        html += "<a href='login.html' class='salir'>Salir</a>";
        html += "</div>";

        html += "<div class='content'>";
        html += "<div class='contenedor-calculo'>";
        html += "<h1>Simulador de Red Coaxial</h1>";
        html += "<div class='layout-calculo'>";

        // COLUMNA IZQUIERDA: CONFIGURACION
        html += "<div class='config-card'>";
        html += "<h2>Configuración</h2><hr>";
        html += "<form action='ejecutarCalculo' method='GET'>";
        
        html += "<label>Nivel de señal en cabecera (dBµV):</label>";
        html += "<input type='number' name='cabecera' value='170' required>"; // Valor por defecto 170

        html += "<label>Cable Coaxial (Pérdida/100m):</label>";
        html += "<select name='cable' required>";
        for (Componente c : cables) {
            html += "<option value='" + c.getId() + "'>" + c.getModelo() + " (" + c.getAt862() + " dB)</option>";
        }
        html += "</select>";

        html += "<label>Base de Toma Final:</label>";
        html += "<select name='toma' required>";
        for (Componente c : tomas) {
            html += "<option value='" + c.getId() + "'>" + c.getModelo() + " (Paso: " + c.getAtPaso() + " dB)</option>";
        }
        html += "</select>";

        html += "<div id='contenedor-derivadores'>";
        html += "<label>Derivador Planta 1:</label>";
        html += "<select name='derivadores' id='base-derivador' required>";
        for (Componente c : derivadores) {
            html += "<option value='" + c.getId() + "'>" + c.getModelo() + "</option>";
        }
        html += "</select>";
        html += "</div>";
        
        html += "<button type='button' class='btn-agregar' onclick='agregarDerivador()'>+ Agregar Derivador</button>";
        html += "<button type='submit' class='btn-calcular'>Calcular Máximo de Plantas</button>";
        html += "</form></div>";

        // COLUMNA DERECHA: RESULTADOS
        html += "<div class='esquematico-card'>";
        html += "<h2>Visualización</h2><hr>";
        if (maxPlantasResult != null) {
            html += "<div class='resultado-box'><h3>Resultado: " + maxPlantasResult + " plantas</h3></div>";
        } else {
            html += "<div class='esquematico-vacio'>Selecciona los componentes para simular el edificio</div>";
        }
        html += "</div></div></div></div></body></html>";

        return html;
    }
}
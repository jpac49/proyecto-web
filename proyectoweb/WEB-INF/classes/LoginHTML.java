public class LoginHTML {

    public static boolean loginCorrecto(String nombre, String contrasena) {
        return nombre.equals("alumno1") && contrasena.equals("123");
    }

    public static String salidaHTML(boolean res) {
        if (!res) {
            return "<html><body><h1 style='color:red'>Acceso Denegado</h1><a href='login.html'>Reintentar</a></body></html>";
        }

        // Si el login es correcto, devolvemos el "Panel de Control" con pestañas
        return "<html><head><meta charset='UTF-8'><style>" +
               "body { font-family: sans-serif; margin: 0; }" +
               ".navbar { background: #333; color: white; padding: 1rem; display: flex; gap: 20px; }" +
               ".navbar a { color: white; text-decoration: none; padding: 10px; border-radius: 5px; }" +
               ".navbar a:hover { background: #555; }" +
               ".content { padding: 20px; }" +
               ".card { border: 1px solid #ddd; padding: 20px; border-radius: 8px; background: #fafafa; }" +
               "</style></head><body>" +
               "<div class='navbar'>" +
               "  <span style='font-weight:bold'>📡 Simulador TV</span>" +
               "  <a href='#'>Ingresar Componentes</a>" +
               "  <a href='#'>Calcular Plantas</a>" +
               "  <a href='#'>Mi Edificio</a>" +
               "  <a href='#'>Historial</a>" +
               "  <a href='login.html' style='margin-left:auto; background: #d93025'>Salir</a>" +
               "</div>" +
               "<div class='content'>" +
               "  <h2>Bienvenido al Simulador de ICT</h2>" +
               "  <div class='card'>" +
               "    <h3>Estado del Proyecto</h3>" +
               "    <p>Nivel de Cabecera configurado: <b>170 dBµV</b></p>" +
               "    <hr>" +
               "    <p>Selecciona una opción del menú superior para empezar a dimensionar la red coaxial.</p>" +
               "    <button>Crear Nueva Simulación</button>" +
               "  </div>" +
               "</div>" +
               "</body></html>";
    }
}
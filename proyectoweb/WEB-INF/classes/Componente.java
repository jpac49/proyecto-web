public class Componente {

    private int id;
    private String tipo;
    private String modelo;
    private Double precio;
    
    // Detalles técnicos (ahora cubrimos todas tus tablas nuevas)
    private Double at470;         // Para Cables
    private Double at862;         // Para Cables
    private Double atPaso;        // Para Derivadores (y tomas en cascada)
    private Double atDerivacion;  // Para Tomas y Derivadores
    private Integer numSalidas;   // Para Distribuidores y Derivadores
    private Double atSalida;      // Para Distribuidores y Derivadores

    // Constructor completo actualizado
    public Componente(int id, String tipo, String modelo, Double precio, 
                      Double at470, Double at862, Double atPaso, 
                      Double atDerivacion, Integer numSalidas, Double atSalida) {
        this.id = id;
        this.tipo = tipo;
        this.modelo = modelo;
        this.precio = precio;
        this.at470 = at470;
        this.at862 = at862;
        this.atPaso = atPaso;
        this.atDerivacion = atDerivacion;
        this.numSalidas = numSalidas;
        this.atSalida = atSalida;
    }

    // Getters
    public int getId() { return id; }
    public String getTipo() { return tipo; }
    public String getModelo() { return modelo; }
    public Double getPrecio() { return precio; }
    public Double getAt470() { return at470; }
    public Double getAt862() { return at862; }
    public Double getAtPaso() { return atPaso; }
    public Double getAtDerivacion() { return atDerivacion; }
    public Integer getNumSalidas() { return numSalidas; }
    public Double getAtSalida() { return atSalida; }
}
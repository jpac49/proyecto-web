public class Componente {

    private int id;
    private String tipo;
    private String modelo;
    private Double precio;
    private Double at470;
    private Double at862;
    private Double atPaso;

    public Componente(int id, String tipo, String modelo, Double precio, Double at470, Double at862, Double atPaso) {
        this.id = id;
        this.tipo = tipo;
        this.modelo = modelo;
        this.precio = precio;
        this.at470 = at470;
        this.at862 = at862;
        this.atPaso = atPaso;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getModelo() {
        return modelo;
    }

    public Double getPrecio() {
        return precio;
    }

    public Double getAt470() {
        return at470;
    }

    public Double getAt862() {
        return at862;
    }

    public Double getAtPaso() {
        return atPaso;
    }
}
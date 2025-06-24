package modelos;

public class DeporteDTO {
    private String nombre;
    private String tipo;
    private int cantJugadores;
    private String categoria;

    // Constructor
    public DeporteDTO(String nombre, String tipo, int cantJugadores, String categoria) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.cantJugadores = cantJugadores;
        this.categoria = categoria;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantJugadores() {
        return cantJugadores;
    }

    public void setCantJugadores(int cantJugadores) {
        this.cantJugadores = cantJugadores;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // Método obtenerCordenadas
    public String obtenerCordenadas() {
        // Implementación de ejemplo
        return "Coordenadas no disponibles";
    }
}

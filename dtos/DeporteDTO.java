package dtos;

import tipos.NivelDeJuego;

/**
 * DTO para transferir datos de Deportes.
 * Sus atributos coinciden con el modelo Deporte para una correcta correspondencia.
 */
public class DeporteDTO {

    private String nombre;
    private String descripcion;
    private int cantidadJugadores;
    private String reglas;

    /**
     * Constructor completo para crear un DTO con todos los datos.
     */
    public DeporteDTO(String nombre, String descripcion, int cantidadJugadores, String reglas) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidadJugadores = cantidadJugadores;
        this.reglas = reglas;
    }

    /**
     * Constructor vacío para mayor flexibilidad.
     */
    public DeporteDTO() {
    }

    // --- Getters y Setters ---

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidadJugadores() {
        return cantidadJugadores;
    }

    public void setCantidadJugadores(int cantidadJugadores) {
        this.cantidadJugadores = cantidadJugadores;
    }

    public String getReglas() {
        return reglas;
    }

    public void setReglas(String reglas) {
        this.reglas = reglas;
    }

}
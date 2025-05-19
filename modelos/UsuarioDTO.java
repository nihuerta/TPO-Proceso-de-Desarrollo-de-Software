package modelos;

public class UsuarioDTO {
    private String nombre;
    private String correo;
    private String contraseña;
    private String deporteFav;
    private NivelDeJuego nivelDeJuego;

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    public String getDeporteFav() { return deporteFav; }
    public void setDeporteFav(String deporteFav) { this.deporteFav = deporteFav; }
    public NivelDeJuego getNivelDeJuego() { return nivelDeJuego; }
    public void setNivelDeJuego(NivelDeJuego nivelDeJuego) { this.nivelDeJuego = nivelDeJuego; }
}
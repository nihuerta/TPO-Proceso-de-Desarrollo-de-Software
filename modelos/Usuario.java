package modelos;

import java.util.List;
import java.util.ArrayList;
import dtos.UsuarioDTO;
import tipos.NivelDeJuego;

public class Usuario {
    private String nombre;
    private String correo;
    private String contrasenia;
    private List<String> deportesFav;
    private NivelDeJuego nivelDeJuego;
    private List<Partido> partidosCreados;
    private Geolocalizacion zona; // Asociación directa (1 a 1)

    // Constructor vacío
    public Usuario() {
        this.deportesFav = new ArrayList<>();
        this.partidosCreados = new ArrayList<>();
    }

    // Método para crear usuario desde DTO
    public void crearUsuario(UsuarioDTO usuarioDTO) {
        this.nombre = usuarioDTO.getNombre();
        this.correo = usuarioDTO.getCorreo();
        this.contrasenia = usuarioDTO.getContrasenia();
        this.deportesFav.add(usuarioDTO.getDeporteFav());
        this.nivelDeJuego = usuarioDTO.getNivelDeJuego();
    }

    // Métodos para deportes favoritos
    public void agregarDeporteFav(Deporte deporte) {
        deportesFav.add(deporte.getNombre());
    }

    public void eliminarDeporteFav(Deporte deporte) {
        deportesFav.remove(deporte.getNombre());
    }

    // Métodos para gestión de partidos (composición)
    public void crearPartido(Partido partido) {
        partidosCreados.add(partido);
    }

    public void eliminarPartido(Partido partido) {
        partidosCreados.remove(partido);
    }

    public String getEmail() {
        return getCorreo();
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public List<String> getDeportesFav() {
        return deportesFav;
    }

    public void setDeportesFav(List<String> deportesFav) {
        this.deportesFav = deportesFav;
    }

    public NivelDeJuego getNivelDeJuego() {
        return nivelDeJuego;
    }

    public void setNivelDeJuego(NivelDeJuego nivelDeJuego) {
        this.nivelDeJuego = nivelDeJuego;
    }

    public List<Partido> getPartidosCreados() {
        return partidosCreados;
    }

    public void setPartidosCreados(List<Partido> partidosCreados) {
        this.partidosCreados = partidosCreados;
    }

    public Geolocalizacion getZona() {
        return zona;
    }

    public void setZona(Geolocalizacion zona) {
        this.zona = zona;
    }

    public boolean confirmacionPendiente() {
        return true;
    }
}
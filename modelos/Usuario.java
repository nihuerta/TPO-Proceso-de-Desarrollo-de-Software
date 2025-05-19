package modelos;

import java.util.List;
import java.util.ArrayList;

public class Usuario {
    private String nombre;
    private String correo;
    private String contraseña;
    private List<Deporte> deportesFav = new ArrayList<>(); // Agregación (1 a muchos)
    private Geolocalizacion zona; // Asociación directa (1 a 1)
    private List<Partido> partidosCreados = new ArrayList<>(); // Composición (1 a muchos)

    // Constructor vacío
    public Usuario() {}

    // Método para crear usuario desde DTO
    public void crearUsuario(UsuarioDTO usuarioDTO) {
        this.nombre = usuarioDTO.getNombre();
        this.correo = usuarioDTO.getCorreo();
        this.contraseña = usuarioDTO.getContraseña();
    }

    // Métodos para deportes favoritos
    public void agregarDeporteFav(Deporte deporte) {
        deportesFav.add(deporte);
    }

    public void eliminarDeporteFav(Deporte deporte) {
        deportesFav.remove(deporte);
    }

    // Métodos para gestión de partidos (composición)
    public void crearPartido(Partido partido) {
        partidosCreados.add(partido);
    }

    public void eliminarPartido(Partido partido) {
        partidosCreados.remove(partido);
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    public List<Deporte> getDeportesFav() { return deportesFav; }
    public Geolocalizacion getZona() { return zona; }
    public void setZona(Geolocalizacion zona) { this.zona = zona; }
    public List<Partido> getPartidosCreados() { return partidosCreados; }
}
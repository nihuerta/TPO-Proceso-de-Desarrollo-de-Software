package controladores;

import modelos.Usuario;
import modelos.BaseDatos;
import dtos.UsuarioDTO;
import modelos.Geolocalizacion;
import modelos.Deporte;
import tipos.NivelDeJuego;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController {

    private Usuario usuario;
    
    public UsuarioController() {
        // No necesita inicializar nada, usa BaseDatos
    }
    
    public Usuario crearUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioDTO == null) {
            throw new IllegalArgumentException("El DTO de usuario no puede ser nulo");
        }
        if (usuarioDTO.getNombre() == null || usuarioDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío");
        }
        if (usuarioDTO.getCorreo() == null || usuarioDTO.getCorreo().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico no puede estar vacío");
        }
        if (usuarioDTO.getContrasenia() == null || usuarioDTO.getContrasenia().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        Usuario nuevUsuario = new Usuario();
            nuevUsuario.setNombre(usuarioDTO.getNombre().trim());
            nuevUsuario.setCorreo(usuarioDTO.getCorreo().trim());
            nuevUsuario.setContrasenia(usuarioDTO.getContrasenia());
            System.out.println(usuarioDTO.getNivelDeJuego());
                    // Si no se especifica nivel, usar INTERMEDIO por defecto
        nuevUsuario.setNivelDeJuego(usuarioDTO.getNivelDeJuego() != null ? 
            usuarioDTO.getNivelDeJuego() : tipos.NivelDeJuego.INTERMEDIO);

        
        if (usuarioDTO.getDeporteFav() != null && !usuarioDTO.getDeporteFav().trim().isEmpty()) {
            List<String> deportes = new ArrayList<>();
            deportes.add(usuarioDTO.getDeporteFav().trim());
            nuevUsuario.setDeportesFav(deportes);
        } else {
            nuevUsuario.setDeportesFav(new ArrayList<>()); // Inicializar lista vacía
        }
                    System.out.println(usuarioDTO.getNivelDeJuego());


        
        
        


        BaseDatos.usuarios.add(nuevUsuario);
        return nuevUsuario;
    }

    public void actualizarUsuario(Usuario usuario, UsuarioDTO usuarioDTO) {
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setContrasenia(usuarioDTO.getContrasenia());
        usuario.setNivelDeJuego(usuarioDTO.getNivelDeJuego());
        if (usuarioDTO.getDeporteFav() != null) {
            List<String> deportes = new ArrayList<>();
            deportes.add(usuarioDTO.getDeporteFav());
            usuario.setDeportesFav(deportes);
        }
    }

    public void agregarDeporteFavorito(Usuario usuario, Deporte deporte) {
        usuario.agregarDeporteFav(deporte);
    }

    public void eliminarDeporteFavorito(Usuario usuario, Deporte deporte) {
        usuario.eliminarDeporteFav(deporte);
    }

    public void actualizarZona(Usuario usuario, Geolocalizacion zona) {
        usuario.setZona(zona);
    }

    public List<Usuario> getUsuarios() {
        return BaseDatos.usuarios;
    }

    public Usuario buscarPorCorreo(String correo) {
        return BaseDatos.usuarios.stream()
                .filter(u -> u.getCorreo().equals(correo))
                .findFirst()
                .orElse(null);
    }

    public List<Usuario> buscarPorDeporte(String nombreDeporte) {
        return BaseDatos.usuarios.stream()
                .filter(u -> u.getDeportesFav().contains(nombreDeporte))
                .toList();
    }
}
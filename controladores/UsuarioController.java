package controladores;

import modelos.Usuario;
import dtos.UsuarioDTO;
import modelos.Geolocalizacion;
import modelos.Deporte;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
    private List<Usuario> usuarios;
    private Usuario usuario;

    public UsuarioController() {
        this.usuarios = new ArrayList<>();
    }

    public Usuario crearUsuario(UsuarioDTO usuarioDTO) {
        Usuario nuevoUsuario = new Usuario();
        usuario.crearUsuario(nuevoUsuario);
        return usuario;
    }

    public void actualizarUsuario(Usuario usuario, UsuarioDTO usuarioDTO) {
        usuario.crearUsuario(usuarioDTO);
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
        return new ArrayList<>(usuarios);
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarios.stream()
                .filter(u -> u.getCorreo().equals(correo))
                .findFirst()
                .orElse(null);
    }

    public List<Usuario> buscarPorDeporte(Deporte deporte) {
        return usuarios.stream()
                .filter(u -> u.getDeportesFav().contains(deporte))
                .toList();
    }
}
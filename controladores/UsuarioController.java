package controladores;

import modelos.Usuario;
import modelos.UsuarioDTO;
import modelos.Deporte;

public class UsuarioController {
    // Dependencias: Usuario y UsuarioDTO
    public void crearUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.crearUsuario(usuarioDTO);
        // Lógica adicional (ej. guardar en base de datos)
    }

    public void actualizarUsuario(UsuarioDTO usuarioDTO) {
        // Lógica para actualizar usuario existente
    }

    public void agregarDeporte(Deporte deporte) {
        // Lógica para agregar deporte al sistema
    }

    public void marcarDeporteFav(Usuario usuario, Deporte deporte) {
        usuario.agregarDeporteFav(deporte);
    }
}
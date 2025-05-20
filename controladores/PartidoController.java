package controladores;

import modelos.*;
import java.util.List;

public class PartidoController {

    public Partido crearPartido(PartidoDTO dto) {
        return new Partido(
            dto.deporte,
            dto.cantJugadores,
            dto.duracion,
            dto.zona,
            dto.horario,
            dto.administrador
        );
    }

    public void setEstadoPartido(Partido partido, IEstadoPartido nuevoEstado) {
        partido.cambiarEstado(nuevoEstado);
    }

    public void setEstrategia(Partido partido, EstrategiaEmparejador estrategia) {
        partido.aplicarEstrategia(estrategia);
    }

    public void agregarJugador(Partido partido, Usuario usuario) {
        partido.agregarJugador(usuario);
    }

    public IEstadoPartido getEstadoPartido(Partido partido) {
        return partido.getEstado();
    }
}

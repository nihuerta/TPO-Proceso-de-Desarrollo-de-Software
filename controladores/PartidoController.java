package controladores;

import modelos.Partido;
import modelos.Usuario;
import notificaciones.Observador;
import dtos.PartidoDTO;
import estrategias.EstrategiaEmparejador;
import estados.IEstadoPartido;
import modelos.BaseDatos;
import java.util.List;

public class PartidoController {

    public PartidoController() {
        // No necesita inicializar nada
    }

    public void agregarObservador(Observador observador) {
        BaseDatos.observadores.add(observador);
    }

    public void quitarObservador(Observador observador) {
        BaseDatos.observadores.remove(observador);
    }

    public Partido crearPartido(PartidoDTO dto) {
        Partido partido = new Partido(
            dto.deporte,
            dto.cantJugadores,
            dto.duracion,
            dto.zona,
            dto.horario,
            dto.administrador
        );
        partido.setCualquierNivel(dto.cualquierNivel);
        partido.setNivelMinimo(dto.nivelMinimo);
        partido.setNivelMaximo(dto.nivelMaximo);
        
        // Agregar todos los observadores de la base de datos al partido
        BaseDatos.observadores.forEach(partido::agregarObservador);
        
        if (dto.estrategia != null) {
            partido.aplicarEstrategia(dto.estrategia);
        }
        
        BaseDatos.partidos.add(partido);
        return partido;
    }

    public void setEstrategia(Partido partido, EstrategiaEmparejador estrategia) {
        partido.aplicarEstrategia(estrategia);
    }

    public void setEstadoPartido(Partido partido, IEstadoPartido nuevoEstado) {
        partido.cambiarEstado(nuevoEstado);
    }

    public void agregarJugador(Partido partido, Usuario usuario) {
        partido.agregarJugador(usuario);
    }

    public void confirmarParticipacion(Partido partido, Usuario usuario) {
        partido.confirmarParticipacion(usuario);
    }

    public void iniciarPartido(Partido partido) {
        partido.iniciarPartido();
    }

    public void finalizarPartido(Partido partido) {
        partido.finalizarPartido();
    }

    public void cancelarPartido(Partido partido, Usuario solicitante) {
        partido.cancelarPartido(solicitante);
    }

    public IEstadoPartido getEstadoPartido(Partido partido) {
        return partido.getEstado();
    }

    public List<Partido> getPartidos() {
        return BaseDatos.partidos;
    }
}

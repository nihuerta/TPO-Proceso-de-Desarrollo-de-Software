package controladores;

import modelos.Partido;
import modelos.Usuario;
import notificaciones.Observador;
import dtos.PartidoDTO;
import estrategias.EstrategiaEmparejador;
import estados.IEstadoPartido;
import java.util.List;
import java.util.ArrayList;
import modelos.Deporte;
import modelos.Geolocalizacion;
import tipos.NivelDeJuego;

public class PartidoController {
    private List<Partido> partidos;
    private List<Observador> observadores;

    public PartidoController() {
        this.partidos = new ArrayList<>();
        this.observadores = new ArrayList<>();
    }

    public void agregarObservador(Observador observador) {
        observadores.add(observador);
    }

    public void quitarObservador(Observador observador) {
        observadores.remove(observador);
    }

    public Partido crearPartido(PartidoDTO dto) {
        // Convertir DeporteDTO a Deporte
        Deporte deporte = new Deporte(
            dto.deporte.getNombre(),
            dto.deporte.getDescripcion(),
            dto.deporte.getCantidadJugadores(),
            dto.deporte.getReglas(),
            NivelDeJuego.INTERMEDIO
        );
        // Convertir GeolocalizacionDTO a Geolocalizacion
        Geolocalizacion zona = new Geolocalizacion();
        zona.setLatitud(dto.zona.getLatitud());
        zona.setLongitud(dto.zona.getLongitud());
        Partido partido = new Partido(
            deporte,
            dto.cantJugadores,
            dto.duracion,
            zona,
            dto.horario,
            dto.administrador
        );
        // NUEVO: setear restricciones de nivel
        partido.setCualquierNivel(dto.cualquierNivel);
        partido.setNivelMinimo(dto.nivelMinimo);
        partido.setNivelMaximo(dto.nivelMaximo);
        
        // Agregar observadores al partido
        observadores.forEach(partido::agregarObservador);
        
        // Aplicar estrategia si se especificó
        if (dto.estrategia != null) {
            partido.aplicarEstrategia(dto.estrategia);
        }
        
        partidos.add(partido);
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
        return new ArrayList<>(partidos);
    }
}

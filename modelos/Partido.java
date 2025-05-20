import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Partido {
    // Atributos existentes
    private String deporte;
    private int cantJugadores;
    private int duracion;
    private Geolocalizacion zona;
    private LocalDateTime horario;
    private IEstadoPartido estado;
    private Usuario administrador;
    private List<Usuario> jugadores;
    private EstrategiaEmparejador estrategia;

    
    public Partido(String deporte, int cantJugadores, int duracion, Geolocalizacion zona, 
                  LocalDateTime horario, Usuario administrador) {
        this.deporte = deporte;
        this.cantJugadores = cantJugadores;
        this.duracion = duracion;
        this.zona = zona;
        this.horario = horario;
        this.administrador = administrador;
        this.jugadores = new ArrayList<>();
        this.jugadores.add(administrador);
        this.estado = new NecesitamosJugadores();
    }

    
    public void agregarJugador(Usuario jugador) {
        if (!puedeUnirse(jugador)) {
            throw new IllegalStateException("El jugador no cumple con los requisitos para unirse");
        }
        jugadores.add(jugador);
        
        if (estaCompleto()) {
            cambiarEstado(new PartidoArmado());
        }
    }

    public void confirmarParticipacion(Usuario jugador) {
        estado.confirmarParticipacion(this, jugador);
        if (todosConfirmaron()) {
            cambiarEstado(new Confirmado());
        }
    }

    public void iniciarPartido() {
        if (LocalDateTime.now().isBefore(horario)) {
            throw new IllegalStateException("Aún no es hora de iniciar el partido");
        }
        estado.iniciarPartido(this);
    }

    public void finalizarPartido() {
        estado.finalizarPartido(this);
    }

    public void cancelarPartido(Usuario solicitante) {
        if (!solicitante.equals(administrador)) {
            throw new IllegalStateException("Solo el administrador puede cancelar el partido");
        }
        estado.cancelar(this);
    }

    public void cambiarEstado(IEstadoPartido nuevoEstado) {
        this.estado = nuevoEstado;
        notificarCambioEstado();
    }

    private void notificarCambioEstado() {
        for (Usuario jugador : jugadores) {
            jugador.notificarCambioEstadoPartido(this);
        }
    }

    public void aplicarEstrategia(EstrategiaEmparejador estrategia) {
        this.estrategia = estrategia;
        if (!estrategia.validarPartido(this)) {
            throw new IllegalStateException("El partido no cumple con los requisitos de la estrategia");
        }
    }

    // Métodos de consulta
    public boolean estaCompleto() {
        return jugadores.size() >= cantJugadores;
    }

    public boolean contieneJugador(Usuario jugador) {
        return jugadores.contains(jugador);
    }

    public boolean puedeUnirse(Usuario jugador) {
        return !estaCompleto() && 
               !contieneJugador(jugador) && 
               estrategia != null && 
               estrategia.cumpleRequisitos(jugador) &&
               estado.puedeUnirseJugador();
    }

    private boolean todosConfirmaron() {
        return jugadores.stream().allMatch(Usuario::confirmacionPendiente);
    }

    // Getters
    public String getDeporte() {
        return deporte;
    }

    public int getCantJugadores() {
        return cantJugadores;
    }

    public int getDuracion() {
        return duracion;
    }

    public Geolocalizacion getZona() {
        return zona;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public IEstadoPartido getEstado() {
        return estado;
    }

    public Usuario getAdministrador() {
        return administrador;
    }

    public List<Usuario> getJugadores() {
        return new ArrayList<>(jugadores);
    }

    public EstrategiaEmparejador getEstrategia() {
        return estrategia;
    }
}
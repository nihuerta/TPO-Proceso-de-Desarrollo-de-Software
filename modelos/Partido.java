package modelos;

import estados.Confirmado;
import estados.IEstadoPartido;
import estados.NecesitamosJugadores;
import estados.PartidoArmado;
import estrategias.EstrategiaEmparejador;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import notificaciones.Observador;

public class Partido {
    // Atributos existentes
    private Deporte deporte;;
    private int cantJugadores;
    private int duracion;
    private Geolocalizacion zona;
    private LocalDateTime horario;
    private IEstadoPartido estado;
    private Usuario administrador;
    private List<Usuario> jugadores;
    private EstrategiaEmparejador estrategia;
    private List<Observador> observadores = new ArrayList<>();
    private tipos.NivelDeJuego nivelMinimo;
    private tipos.NivelDeJuego nivelMaximo;
    private boolean cualquierNivel = true;
    
    public Partido(Deporte deporte, int cantJugadores, int duracion, Geolocalizacion zona,
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
        this.cualquierNivel = true;
        this.nivelMinimo = null;
        this.nivelMaximo = null;
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
            throw new IllegalStateException("No es hora de iniciar el partido");
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

    public void agregarObservador(Observador observador) {
        observadores.add(observador);
    }

    public void quitarObservador(Observador observador) {
        observadores.remove(observador);
    }

    private void notificarCambioEstado() {
        String evento = "El partido cambió de estado a: " + estado.getClass().getSimpleName();
        for (Observador observador : observadores) {
            observador.actualizar(this, evento);
        }
    }

    public void aplicarEstrategia(EstrategiaEmparejador estrategia) {
        this.estrategia = estrategia;
        if (!estrategia.validarPartido(this)) {
            throw new IllegalStateException("El partido no cumple con los requisitos de la estrategia");
        }
    }

    // Setters para nivel
    public void setNivelMinimo(tipos.NivelDeJuego nivelMinimo) { this.nivelMinimo = nivelMinimo; }
    public void setNivelMaximo(tipos.NivelDeJuego nivelMaximo) { this.nivelMaximo = nivelMaximo; }
    public void setCualquierNivel(boolean cualquierNivel) { this.cualquierNivel = cualquierNivel; }

    // Métodos de consulta
    public boolean estaCompleto() {
        return jugadores.size() >= cantJugadores;
    }

    public boolean contieneJugador(Usuario jugador) {
        return jugadores.contains(jugador);
    }

    public boolean puedeUnirse(Usuario jugador) {
        boolean nivelOk = true;
        if (!cualquierNivel && jugador.getNivelDeJuego() != null) {
            int nivelJugador = jugador.getNivelDeJuego().ordinal();
            int min = nivelMinimo != null ? nivelMinimo.ordinal() : 0;
            int max = nivelMaximo != null ? nivelMaximo.ordinal() : 2;
            nivelOk = nivelJugador >= min && nivelJugador <= max;
        }
        return !estaCompleto() && 
               !contieneJugador(jugador) && 
               estrategia != null && 
               estrategia.cumpleRequisitos(jugador, this) &&
               estado.puedeUnirseJugador() &&
               nivelOk;
    }

    private boolean todosConfirmaron() {
        return jugadores.stream().allMatch(Usuario::confirmacionPendiente);
    }

    // Getters
    public Deporte getDeporte() { return deporte; }
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
    public tipos.NivelDeJuego getNivelMinimo() { return nivelMinimo; }
    public tipos.NivelDeJuego getNivelMaximo() { return nivelMaximo; }
    public boolean isCualquierNivel() { return cualquierNivel; }
}

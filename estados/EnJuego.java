package estados;
import modelos.Partido;
import modelos.Usuario;

public class EnJuego implements IEstadoPartido {

    @Override
    public void validarCondiciones(Partido partido) {}

    @Override
    public void aceptarPartido(Partido partido) {}

    @Override
    public void rechazarPartido(Partido partido) {}

    @Override
    public void iniciarPartido(Partido partido) {
        // Ya está iniciado
    }

    @Override
    public void jugarPartido(Partido partido) {
        // Lógica de juego (si aplica)
    }

    @Override
    public void confirmarParticipacion(Partido partido, Usuario jugador) {
        // Demasiado tarde
    }

    @Override
    public void cancelar(Partido partido) {
        partido.cambiarEstado(new Cancelado());
    }

    @Override
    public void finalizarPartido(Partido partido) {
        partido.cambiarEstado(new Finalizado());
    }

    @Override
    public boolean puedeUnirseJugador() {
        return false;
    }
}


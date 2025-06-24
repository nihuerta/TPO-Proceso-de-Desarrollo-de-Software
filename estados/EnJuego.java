package estados;
import modelos.Partido;
import modelos.Usuario;

public class EnJuego implements IEstadoPartido {

    @Override
    public void validarCondiciones(Partido partido) {
        throw new IllegalStateException("No se puede validar condiciones en un partido en juego");
    }

    @Override
    public void aceptarPartido(Partido partido) {
        throw new IllegalStateException("No se puede aceptar un partido en juego");
    }

    @Override
    public void rechazarPartido(Partido partido) {
        throw new IllegalStateException("No se puede rechazar un partido en juego");
    }

    @Override
    public void iniciarPartido(Partido partido) {
        throw new IllegalStateException("El partido ya está en juego");
    }

    @Override
    public void jugarPartido(Partido partido) {
        // Aquí podría ir la lógica de juego, si aplica
    }

    @Override
    public void confirmarParticipacion(Partido partido, Usuario jugador) {
        throw new IllegalStateException("No se puede confirmar participación en un partido en juego");
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


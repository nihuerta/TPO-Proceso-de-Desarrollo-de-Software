package estados;

import modelos.Partido;
import modelos.Usuario;

public class Cancelado implements IEstadoPartido {

    @Override
    public void validarCondiciones(Partido partido) {
        throw new IllegalStateException("No se puede validar condiciones en un partido cancelado");
    }

    @Override
    public void aceptarPartido(Partido partido) {
        throw new IllegalStateException("El partido ya fue cancelado");
    }

    @Override
    public void rechazarPartido(Partido partido) {
        throw new IllegalStateException("El partido ya fue cancelado");
    }

    @Override
    public void iniciarPartido(Partido partido) {
        throw new IllegalStateException("No se puede iniciar un partido cancelado");
    }

    @Override
    public void jugarPartido(Partido partido) {
        throw new IllegalStateException("No se puede jugar un partido cancelado");
    }

    @Override
    public void confirmarParticipacion(Partido partido, Usuario jugador) {
        throw new IllegalStateException("No se puede confirmar participación en un partido cancelado");
    }

    @Override
    public void cancelar(Partido partido) {
        throw new IllegalStateException("El partido ya fue cancelado");
    }

    @Override
    public void finalizarPartido(Partido partido) {
        throw new IllegalStateException("No se puede finalizar un partido cancelado");
    }

    @Override
    public boolean puedeUnirseJugador() {
        return false;
    }
}

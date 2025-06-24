package estados;

import modelos.Partido;
import modelos.Usuario;

public class Finalizado implements IEstadoPartido {

    @Override
    public void validarCondiciones(Partido partido) {
        throw new IllegalStateException("No se puede validar condiciones en un partido finalizado");
    }

    @Override
    public void aceptarPartido(Partido partido) {
        throw new IllegalStateException("No se puede aceptar un partido finalizado");
    }

    @Override
    public void rechazarPartido(Partido partido) {
        throw new IllegalStateException("No se puede rechazar un partido finalizado");
    }

    @Override
    public void iniciarPartido(Partido partido) {
        throw new IllegalStateException("No se puede iniciar un partido finalizado");
    }

    @Override
    public void jugarPartido(Partido partido) {
        throw new IllegalStateException("No se puede jugar un partido finalizado");
    }

    @Override
    public void confirmarParticipacion(Partido partido, Usuario jugador) {
        throw new IllegalStateException("No se puede confirmar participación en un partido finalizado");
    }

    @Override
    public void cancelar(Partido partido) {
        throw new IllegalStateException("No se puede cancelar un partido finalizado");
    }

    @Override
    public void finalizarPartido(Partido partido) {
        throw new IllegalStateException("El partido ya está finalizado");
    }

    @Override
    public boolean puedeUnirseJugador() {
        return false;
    }
}



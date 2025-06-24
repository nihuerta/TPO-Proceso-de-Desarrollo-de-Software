package estados;

import modelos.Partido;
import modelos.Usuario;

public class Confirmado implements IEstadoPartido {

    @Override
    public void validarCondiciones(Partido partido) {
        throw new IllegalStateException("No se puede validar condiciones en un partido confirmado");
    }

    @Override
    public void aceptarPartido(Partido partido) {
        throw new IllegalStateException("El partido ya está confirmado");
    }

    @Override
    public void rechazarPartido(Partido partido) {
        partido.cambiarEstado(new Cancelado());
    }

    @Override
    public void iniciarPartido(Partido partido) {
        partido.cambiarEstado(new EnJuego());
    }

    @Override
    public void jugarPartido(Partido partido) {
        throw new IllegalStateException("El partido debe estar en juego para poder jugarse");
    }

    @Override
    public void confirmarParticipacion(Partido partido, Usuario jugador) {
        throw new IllegalStateException("No se puede confirmar participación en un partido confirmado");
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

package estados;

import modelos.Partido;
import modelos.Usuario;

public class PartidoArmado implements IEstadoPartido {

    @Override
    public void validarCondiciones(Partido partido) {
        if (!partido.estaCompleto()) {
            partido.cambiarEstado(new NecesitamosJugadores());
            throw new IllegalStateException("El partido no tiene suficientes jugadores");
        }
    }

    @Override
    public void aceptarPartido(Partido partido) {
        partido.cambiarEstado(new Confirmado());
    }

    @Override
    public void rechazarPartido(Partido partido) {
        partido.cambiarEstado(new Cancelado());
    }

    @Override
    public void iniciarPartido(Partido partido) {
        throw new IllegalStateException("Debe confirmarse antes de iniciar");
    }

    @Override
    public void jugarPartido(Partido partido) {
        throw new IllegalStateException("Debe confirmarse antes de jugar");
    }

    @Override
    public void confirmarParticipacion(Partido partido, Usuario jugador) {}

    @Override
    public void cancelar(Partido partido) {
        partido.cambiarEstado(new Cancelado());
    }

    @Override
    public void finalizarPartido(Partido partido) {
        throw new IllegalStateException("El partido no comenzó");
    }

    @Override
    public boolean puedeUnirseJugador() {
        return false;
    }
}

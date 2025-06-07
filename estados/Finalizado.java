package estados;

import modelos.Partido;
import modelos.Usuario;

public class Finalizado implements IEstadoPartido {

    @Override
    public void validarCondiciones(Partido partido) {}

    @Override
    public void aceptarPartido(Partido partido) {}

    @Override
    public void rechazarPartido(Partido partido) {}

    @Override
    public void iniciarPartido(Partido partido) {}

    @Override
    public void jugarPartido(Partido partido) {}

    @Override
    public void confirmarParticipacion(Partido partido, Usuario jugador) {}

    @Override
    public void cancelar(Partido partido) {}

    @Override
    public void finalizarPartido(Partido partido) {
        // Ya está finalizado
    }

    @Override
    public boolean puedeUnirseJugador() {
        return false;
    }
}



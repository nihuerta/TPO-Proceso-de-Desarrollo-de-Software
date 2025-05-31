package modelos;

public class Confirmado implements IEstadoPartido {

    @Override
    public void validarCondiciones(Partido partido) {}

    @Override
    public void aceptarPartido(Partido partido) {}

    @Override
    public void rechazarPartido(Partido partido) {
        partido.cambiarEstado(new Cancelado());
    }

    @Override
    public void iniciarPartido(Partido partido) {
        partido.cambiarEstado(new EnJuego());
    }

    @Override
    public void jugarPartido(Partido partido) {}

    @Override
    public void confirmarParticipacion(Partido partido, Usuario jugador) {}

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

package modelos;

public class NecesitamosJugadores implements IEstadoPartido {

    @Override
    public void validarCondiciones(Partido partido) {
        if (partido.estaCompleto()) {
            partido.cambiarEstado(new PartidoArmado());
        }
    }

    @Override
    public void aceptarPartido(Partido partido) {
        throw new IllegalStateException("No se puede aceptar un partido que aún no está armado");
    }

    @Override
    public void rechazarPartido(Partido partido) {
        partido.cambiarEstado(new Cancelado());
    }

    @Override
    public void iniciarPartido(Partido partido) {
        throw new IllegalStateException("No se puede iniciar un partido incompleto");
    }

    @Override
    public void jugarPartido(Partido partido) {
        throw new IllegalStateException("No se puede jugar un partido incompleto");
    }

    @Override
    public void confirmarParticipacion(Partido partido, Usuario jugador) {
        throw new IllegalStateException("No se puede confirmar participación aún");
    }

    @Override
    public void cancelar(Partido partido) {
        partido.cambiarEstado(new Cancelado());
    }

    @Override
    public void finalizarPartido(Partido partido) {
        throw new IllegalStateException("El partido ni siquiera ha comenzado");
    }

    @Override
    public boolean puedeUnirseJugador() {
        return true;
    }
}

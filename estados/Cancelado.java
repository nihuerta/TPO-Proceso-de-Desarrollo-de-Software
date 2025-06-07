package estados;

import modelos.Partido;
import modelos.Usuario;

public class Cancelado implements IEstadoPartido {

    @Override
    public void validarCondiciones(Partido partido) {
        // Ya fue confirmado, no se necesita validación adicional
    }

    @Override
    public void aceptarPartido(Partido partido) {
        // El partido ya está confirmado, no se puede volver a aceptar
        throw new IllegalStateException("El partido ya fue confirmado");
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
        throw new IllegalStateException("El partido debe iniciarse primero");
    }

    @Override
    public void confirmarParticipacion(Partido partido, Usuario jugador) {
        // Ya está confirmado, no se requiere más confirmación
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
        return false; // Ya está confirmado, no pueden unirse más jugadores
    }
}

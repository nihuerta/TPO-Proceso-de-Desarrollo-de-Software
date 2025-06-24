package estados;

import modelos.Partido;
import modelos.Usuario;

public interface IEstadoPartido {
    void validarCondiciones(Partido partido);
    void aceptarPartido(Partido partido);
    void rechazarPartido(Partido partido);
    void iniciarPartido(Partido partido);
    void jugarPartido(Partido partido);
    void confirmarParticipacion(Partido partido, Usuario jugador);
    void cancelar(Partido partido);
    void finalizarPartido(Partido partido);
    boolean puedeUnirseJugador();

}


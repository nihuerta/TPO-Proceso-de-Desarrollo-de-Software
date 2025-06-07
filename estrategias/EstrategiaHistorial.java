package estrategias;

import modelos.Usuario;
import modelos.Partido;

public class EstrategiaHistorial implements EstrategiaEmparejador {
    public EstrategiaHistorial() {}

    @Override
    public boolean cumpleRequisitos(Usuario jugador, Partido partido) {
        return jugador.getPartidosCreados().stream()
                .anyMatch(p -> p.getAdministrador().equals(partido.getAdministrador()));
    }

    @Override
    public boolean validarPartido(Partido partido) {
        return partido.getJugadores().stream()
                .allMatch(j -> cumpleRequisitos(j, partido));
    }
}

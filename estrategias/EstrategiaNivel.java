package estrategias;

import modelos.Usuario;
import modelos.Partido;

public class EstrategiaNivel implements EstrategiaEmparejador {
    public EstrategiaNivel() {}

    @Override
    public boolean cumpleRequisitos(Usuario jugador, Partido partido) {
        // El organizador siempre cumple
        if (jugador.equals(partido.getAdministrador())) return true;
        return jugador.getDeportesFav().contains(partido.getDeporte().getNombre());
    }

    @Override
    public boolean validarPartido(Partido partido) {
        return partido.getJugadores().stream()
                .allMatch(j -> cumpleRequisitos(j, partido));
    }
}


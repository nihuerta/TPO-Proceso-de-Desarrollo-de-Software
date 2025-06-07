package estrategias;

import modelos.Usuario;
import modelos.Partido;

public class EstrategiaNivel implements EstrategiaEmparejador {
    public EstrategiaNivel() {}

    @Override
    public boolean cumpleRequisitos(Usuario jugador, Partido partido) {
        return jugador.getDeportesFav().contains(partido.getDeporte());
    }

    @Override
    public boolean validarPartido(Partido partido) {
        return partido.getJugadores().stream()
                .allMatch(j -> cumpleRequisitos(j, partido));
    }
}


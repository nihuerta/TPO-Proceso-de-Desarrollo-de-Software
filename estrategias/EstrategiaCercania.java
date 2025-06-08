package estrategias;

import modelos.Usuario;
import modelos.Partido;

public class EstrategiaCercania implements EstrategiaEmparejador {
    public EstrategiaCercania() {}

    @Override
    public boolean cumpleRequisitos(Usuario jugador, Partido partido) {
        // El organizador siempre cumple
        if (jugador.equals(partido.getAdministrador())) return true;
        return jugador.getZona() != null && partido.getZona() != null && jugador.getZona().distanciaA(partido.getZona()) < 10.0;
    }

    @Override
    public boolean validarPartido(Partido partido) {
        return partido.getJugadores().stream()
                .allMatch(j -> cumpleRequisitos(j, partido));
    }
}


package estrategias;

import modelos.Usuario;
import modelos.Partido;

public interface EstrategiaEmparejador {
    boolean cumpleRequisitos(Usuario jugador, Partido partido);
    boolean validarPartido(Partido partido);
}


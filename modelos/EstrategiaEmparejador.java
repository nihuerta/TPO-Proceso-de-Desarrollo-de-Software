package modelos;

public interface EstrategiaEmparejador {
    boolean cumpleRequisitos(Usuario jugador);
    boolean validarPartido(Partido partido);
}


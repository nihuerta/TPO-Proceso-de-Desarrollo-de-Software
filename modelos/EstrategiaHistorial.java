package modelos;

public class EstrategiaHistorial implements EstrategiaEmparejador {
    public boolean cumpleRequisitos(Usuario jugador, Partido partido) {
        return jugador.getPartidosCreados().stream().anyMatch(p -> p.getAdministrador().equals(partido.getAdministrador()));
    }
}

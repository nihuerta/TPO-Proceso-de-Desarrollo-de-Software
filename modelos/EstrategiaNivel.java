package modelos;

public class EstrategiaNivel implements EstrategiaEmparejador {
    public boolean cumpleRequisitos(Usuario jugador, Partido partido) {
        return jugador.getDeportesFav().contains(partido.getDeporte());
    }
}

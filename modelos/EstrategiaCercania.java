package modelos;

public class EstrategiaCercania implements EstrategiaEmparejador {
    public boolean cumpleRequisitos(Usuario jugador, Partido partido) {
        return jugador.getZona().distanciaA(partido.getZona()) < 10.0;
    }
}

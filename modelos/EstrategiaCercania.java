package modelos;

public class EstrategiaCercania implements EstrategiaEmparejador {
    private Partido partido;

    public EstrategiaCercania(Partido partido) {
        this.partido = partido;
    }

    @Override
    public boolean cumpleRequisitos(Usuario jugador) {
        return jugador.getZona().distanciaA(partido.getZona()) < 10.0;
    }

    @Override
    public boolean validarPartido(Partido partido) {
        return partido.getJugadores().stream()
                .allMatch(this::cumpleRequisitos);
    }
}


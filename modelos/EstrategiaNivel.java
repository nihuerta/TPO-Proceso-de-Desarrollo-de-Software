package modelos;

public class EstrategiaNivel implements EstrategiaEmparejador {

    private Partido partido;

    public EstrategiaNivel(Partido partido) {
        this.partido = partido;
    }

    @Override
    public boolean cumpleRequisitos(Usuario jugador) {
        return jugador.getDeportesFav().contains(partido.getDeporte());
    }

    @Override
    public boolean validarPartido(Partido partido) {
        return partido.getJugadores().stream()
                .allMatch(this::cumpleRequisitos);
    }
}


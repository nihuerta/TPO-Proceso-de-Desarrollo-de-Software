package modelos;

public class EstrategiaHistorial implements EstrategiaEmparejador {
    private Partido partido;

    public EstrategiaHistorial(Partido partido) {
        this.partido = partido;
    }

    @Override
    public boolean cumpleRequisitos(Usuario jugador) {
        return jugador.getPartidosCreados().stream()
                .anyMatch(p -> p.getAdministrador().equals(partido.getAdministrador()));
    }

    @Override
    public boolean validarPartido(Partido partido) {
        return partido.getJugadores().stream()
                .allMatch(this::cumpleRequisitos);
    }
}

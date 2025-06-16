package estrategias;

import modelos.Partido;
import modelos.Usuario;
import tipos.NivelDeJuego;

public class EstrategiaNivel implements EstrategiaEmparejador {
    public EstrategiaNivel() {}

    @Override
    public boolean cumpleRequisitos(Usuario jugador, Partido partido) {
        // El organizador siempre cumple
        if (jugador.equals(partido.getAdministrador())) {
            return true;
        }

        // Si el partido acepta cualquier nivel, solo verificar que el deporte esté en favoritos
        if (partido.isCualquierNivel()) {
            return jugador.getDeportesFav().contains(partido.getDeporte().getNombre());
        }

        // Si hay restricción de nivel, verificar nivel y deportes favoritos
        if (!jugador.getDeportesFav().contains(partido.getDeporte().getNombre())) {
            return false;
        }

        // Verificar que el nivel del jugador esté dentro del rango permitido
        NivelDeJuego nivelJugador = jugador.getNivelDeJuego();
        if (nivelJugador == null) {
            return false;
        }

        int nivelJugadorOrd = nivelJugador.ordinal();
        int nivelMinOrd = partido.getNivelMinimo() != null ? partido.getNivelMinimo().ordinal() : 0;
        int nivelMaxOrd = partido.getNivelMaximo() != null ? partido.getNivelMaximo().ordinal() : 2;

        return nivelJugadorOrd >= nivelMinOrd && nivelJugadorOrd <= nivelMaxOrd;
    }

    @Override
    public boolean validarPartido(Partido partido) {
        return partido.getJugadores().stream()
                .allMatch(j -> cumpleRequisitos(j, partido));
    }
}


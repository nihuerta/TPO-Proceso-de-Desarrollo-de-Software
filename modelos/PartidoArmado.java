package modelos;


public class PartidoArmado implements IEstadoPartido {
    @Override
    public void validarCondicions(Partido partido) {
        if (partido.cantJugadores < partido.deporte.cantidadJugadores) {
            partido.setEstado(new NecesitamosJugadores());
            throw new IllegalStateException("El partido no tiene la cantidad de jugadores necesaria");
        }
    }   

    @Override
    public void aceptarPartido(Partido partido) {
        return partido.setEstado(new Confirmado());
    }

    @Override
    public void rechazarPartido(Partido partido) {
        return partido.setEstado(new Cancelado());
    }

    @Override
    public void jugarPartido(Partido partido) {
        // TODO: Implementar la lógica para jugar el partido
    }
}

package modelos;


public class NecesitamosJugadores implements IEstadoPartido {
    @Override
    public void validarCondicions(Partido partido) {
        // TODO: Implementar la lógica para validar las condiciones del partido
    }

    @Override
    public void aceptarPartido(Partido partido) {
        // TODO: Implementar la lógica para aceptar el partido
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

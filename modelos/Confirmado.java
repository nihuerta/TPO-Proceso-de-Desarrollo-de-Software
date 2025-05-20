package modelos;


public class Confirmado implements IEstadoPartido {
    @Override
    public void validarCondicions(Partido partido) {
        // TODO: Implementar la lógica para validar las condiciones del partido confirmado
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
        return partido.setEstado(new EnJuego());
    }
}

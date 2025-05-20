package modelos;

public interface IEstadoPartido {
    void validarCondicions(Partido partido);
    void aceptarPartido(Partido partido);
    void rechazarPartido(Partido partido);
    void jugarPartido(Partido partido);
}


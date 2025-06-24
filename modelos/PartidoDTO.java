package modelos;

import java.time.LocalDateTime;
import java.util.List;

public class PartidoDTO {
    public String deporte;
    public int cantJugadores;
    public int duracion;
    public Geolocalizacion zona;
    public LocalDateTime horario;
    public EstrategiaEmparejador estrategia;
    public IEstadoPartido estado;
    public Usuario administrador;
    public List<Usuario> jugadores;
}

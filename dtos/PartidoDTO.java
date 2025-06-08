package dtos;

import java.time.LocalDateTime;
import java.util.List;

import estados.IEstadoPartido;
import estrategias.EstrategiaEmparejador;
import modelos.Usuario;
import modelos.Deporte;
import modelos.Geolocalizacion;

public class PartidoDTO {
    public Deporte deporte;
    public int cantJugadores;
    public int duracion;
    public Geolocalizacion zona;
    public LocalDateTime horario;
    public EstrategiaEmparejador estrategia;
    public IEstadoPartido estado;
    public Usuario administrador;
    public List<Usuario> jugadores;
    // NUEVOS CAMPOS PARA RESTRICCION DE NIVEL
    public tipos.NivelDeJuego nivelMinimo;
    public tipos.NivelDeJuego nivelMaximo;
    public boolean cualquierNivel;

    public PartidoDTO() {}

    public PartidoDTO(Deporte deporte, int cantJugadores, int duracion, 
                     Geolocalizacion zona, LocalDateTime horario, Usuario administrador) {
        this.deporte = deporte;
        this.cantJugadores = cantJugadores;
        this.duracion = duracion;
        this.zona = zona;
        this.horario = horario;
        this.administrador = administrador;
    }
}

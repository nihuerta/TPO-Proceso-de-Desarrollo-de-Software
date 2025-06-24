package modelos;

import dtos.PartidoDTO;
import estrategias.EstrategiaNivel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import notificaciones.Observador;
import tipos.NivelDeJuego;

public class BaseDatos {    
    public static List<Usuario> usuarios = new ArrayList<>();
    public static List<Deporte> deportes = new ArrayList<>();
    public static List<Partido> partidos = new ArrayList<>();
    public static List<Observador> observadores = new ArrayList<>();
    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        initialized = true;

        try {
            // Inicializar deportes por defecto
            Deporte futbol = new Deporte("Fútbol", "Equipo", 22, "Fútbol 11", NivelDeJuego.INTERMEDIO);
            Deporte basket = new Deporte("Basketball", "Equipo", 10, "Basketball 5v5", NivelDeJuego.INTERMEDIO);
            Deporte tenis = new Deporte("Tenis", "Individual", 4, "Singles/Dobles", NivelDeJuego.INTERMEDIO);
            Deporte padel = new Deporte("Padel", "Equipo", 4, "Padel 2v2", NivelDeJuego.INTERMEDIO);
            deportes.add(futbol);
            deportes.add(basket);
            deportes.add(tenis);
            deportes.add(padel);

            // Crear algunos usuarios por defecto
            Usuario admin = new Usuario();
            admin.setNombre("Admin");
            admin.setCorreo("admin@example.com");
            admin.setContrasenia("admin123");

            Usuario juan = new Usuario();
            juan.setNombre("Juan");
            juan.setCorreo("juan@example.com");
            juan.setContrasenia("juan123");

            Usuario maria = new Usuario();
            maria.setNombre("Maria");
            maria.setCorreo("maria@example.com");
            maria.setContrasenia("maria123");

            Usuario carlos = new Usuario();
            carlos.setNombre("Carlos");
            carlos.setCorreo("carlos@example.com");
            carlos.setContrasenia("carlos123");

            // Configurar zonas para los usuarios
            Geolocalizacion zonaCapital = new Geolocalizacion();
            zonaCapital.setLatitud(-34.6037);
            zonaCapital.setLongitud(-58.3816);
            
            Geolocalizacion zonaNorte = new Geolocalizacion();
            zonaNorte.setLatitud(-34.5);
            zonaNorte.setLongitud(-58.5);
            
            admin.setZona(zonaCapital);
            juan.setZona(zonaCapital);
            maria.setZona(zonaNorte);
            carlos.setZona(zonaNorte);

            // Configurar niveles y deportes favoritos
            admin.setNivelDeJuego(NivelDeJuego.AVANZADO);
            juan.setNivelDeJuego(NivelDeJuego.INTERMEDIO);
            maria.setNivelDeJuego(NivelDeJuego.PRINCIPIANTE);
            carlos.setNivelDeJuego(NivelDeJuego.INTERMEDIO);

            admin.agregarDeporteFav(futbol);
            juan.agregarDeporteFav(futbol);
            maria.agregarDeporteFav(padel);
            carlos.agregarDeporteFav(basket);

            usuarios.add(admin);
            usuarios.add(juan);
            usuarios.add(maria);
            usuarios.add(carlos);

            // Crear algunos partidos por defecto usando DTO
            // Partido de fútbol para mañana
            LocalDateTime maniana = LocalDateTime.now().plusDays(1).withHour(18).withMinute(0);
            PartidoDTO partidoFutbolDTO = new PartidoDTO(
                futbol,
                10,  // jugadores necesarios
                90,  // duración en minutos
                zonaCapital,
                maniana,
                admin
            );
            // Configurar estrategia y niveles permitidos
            partidoFutbolDTO.estrategia = new EstrategiaNivel();
            partidoFutbolDTO.cualquierNivel = true; // permitir cualquier nivel
            Partido partidoFutbol = new Partido(
                partidoFutbolDTO.deporte,
                partidoFutbolDTO.cantJugadores,
                partidoFutbolDTO.duracion,
                partidoFutbolDTO.zona,
                partidoFutbolDTO.horario,
                partidoFutbolDTO.administrador
            );
            partidoFutbol.agregarJugador(juan);

            // Partido de padel para pasado mañana
            LocalDateTime pasadoManiana = LocalDateTime.now().plusDays(2).withHour(20).withMinute(0);
            PartidoDTO partidoPadelDTO = new PartidoDTO(
                padel,
                4,   // jugadores necesarios
                60,  // duración en minutos
                zonaNorte,
                pasadoManiana,
                maria
            );
            // Configurar estrategia y niveles permitidos
            partidoPadelDTO.estrategia = new EstrategiaNivel();
            partidoPadelDTO.cualquierNivel = true; // permitir cualquier nivel
            Partido partidoPadel = new Partido(
                partidoPadelDTO.deporte,
                partidoPadelDTO.cantJugadores,
                partidoPadelDTO.duracion,
                partidoPadelDTO.zona,
                partidoPadelDTO.horario,
                partidoPadelDTO.administrador
            );
            partidoPadel.setCualquierNivel(true);
            partidoPadel.agregarJugador(carlos);

            partidos.add(partidoFutbol);
            partidos.add(partidoPadel);

        } catch (Exception e) {
            System.err.println("Error al inicializar datos por defecto: " + e.getMessage());
        }
    }
}

package app;

import controladores.UsuarioController;
import controladores.DeporteController;
import controladores.PartidoController;

import modelos.Usuario;
import modelos.Deporte;
import modelos.Geolocalizacion;
import tipos.NivelDeJuego;
import modelos.Partido;

import adaptadores.AdapterJavaMail;

import notificaciones.NotificacionEmail;
import notificaciones.NotificacionFireBase;

import dtos.UsuarioDTO;
import dtos.PartidoDTO;
import dtos.GeolocalizacionDTO;
import dtos.DeporteDTO;

import estrategias.EstrategiaNivel;
import estrategias.EstrategiaCercania;
import estrategias.EstrategiaHistorial;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Controladores
        UsuarioController usuarioCtrl = new UsuarioController();
        DeporteController deporteCtrl = new DeporteController();
        PartidoController partidoCtrl = new PartidoController();

        // Crear deportes
        Deporte futbol = new Deporte("Futbol", "Equipo", 10, "Futbol 11", NivelDeJuego.INTERMEDIO);
        Deporte tenis = new Deporte("Tenis", "Individual", 2, "Singles", NivelDeJuego.INTERMEDIO);

        // Crear zona
        Geolocalizacion zona = new Geolocalizacion();
        zona.setLatitud(-34.6037);
        zona.setLongitud(-58.3816);

        // Crear usuarios
        Usuario admin = new Usuario();
        admin.setNombre("Admin");
        admin.setCorreo("admin@example.com");
        admin.setContrasenia("admin123");
        admin.agregarDeporteFav(futbol);
        admin.setZona(zona);

        Usuario jugador1 = new Usuario();
        jugador1.setNombre("Jugador1");
        jugador1.setCorreo("jugador1@example.com");
        jugador1.setContrasenia("pass123");
        jugador1.agregarDeporteFav(futbol);
        jugador1.setZona(zona);

        Usuario jugador2 = new Usuario();
        jugador2.setNombre("Jugador2");
        jugador2.setCorreo("jugador2@example.com");
        jugador2.setContrasenia("pass456");
        jugador2.agregarDeporteFav(futbol);
        jugador2.setZona(zona);

        // Configurar notificaciones
        AdapterJavaMail adapter = new AdapterJavaMail();
        NotificacionEmail notificacionEmail = new NotificacionEmail(adapter);
        NotificacionFireBase notificacionFireBase = new NotificacionFireBase();
        partidoCtrl.agregarObservador(notificacionEmail);
        partidoCtrl.agregarObservador(notificacionFireBase);

        // Crear deporte
        deporteCtrl.agregarDeporte(futbol);
        deporteCtrl.agregarDeporte(tenis);

        // =====================
        // 1. EstrategiaNivel
        // =====================
        System.out.println("\n--- Probando EstrategiaNivel ---");
        Partido partidoNivel = crearPartidoSinEstrategia(partidoCtrl, admin, futbol, zona);
        partidoCtrl.agregarJugador(partidoNivel, jugador1);
        partidoCtrl.agregarJugador(partidoNivel, jugador2);
        EstrategiaNivel estrategiaNivel = new EstrategiaNivel();
        partidoCtrl.setEstrategia(partidoNivel, estrategiaNivel);
        partidoCtrl.confirmarParticipacion(partidoNivel, admin);
        partidoCtrl.confirmarParticipacion(partidoNivel, jugador1);
        partidoCtrl.confirmarParticipacion(partidoNivel, jugador2);

        // =====================
        // 2. EstrategiaCercania
        // =====================
        System.out.println("\n--- Probando EstrategiaCercania ---");
        Partido partidoCercania = crearPartidoSinEstrategia(partidoCtrl, admin, futbol, zona);
        partidoCtrl.agregarJugador(partidoCercania, jugador1);
        partidoCtrl.agregarJugador(partidoCercania, jugador2);
        EstrategiaCercania estrategiaCercania = new EstrategiaCercania();
        partidoCtrl.setEstrategia(partidoCercania, estrategiaCercania);
        partidoCtrl.confirmarParticipacion(partidoCercania, admin);
        partidoCtrl.confirmarParticipacion(partidoCercania, jugador1);
        partidoCtrl.confirmarParticipacion(partidoCercania, jugador2);

        // =====================
        // 3. EstrategiaHistorial
        // =====================
        Partido partidoHist = new Partido(futbol, 3, 60, zona, LocalDateTime.now(), admin);
        jugador1.crearPartido(partidoHist);
        jugador2.crearPartido(partidoHist);
        System.out.println("\n--- Probando EstrategiaHistorial ---");
        Partido partidoHistorial = crearPartidoSinEstrategia(partidoCtrl, admin, futbol, zona);
        partidoCtrl.agregarJugador(partidoHistorial, jugador1);
        partidoCtrl.agregarJugador(partidoHistorial, jugador2);
        EstrategiaHistorial estrategiaHistorial = new EstrategiaHistorial();
        partidoCtrl.setEstrategia(partidoHistorial, estrategiaHistorial);
        partidoCtrl.confirmarParticipacion(partidoHistorial, admin);
        partidoCtrl.confirmarParticipacion(partidoHistorial, jugador1);
        partidoCtrl.confirmarParticipacion(partidoHistorial, jugador2);
    }

    private static Partido crearPartidoSinEstrategia(PartidoController partidoCtrl, Usuario admin, Deporte deporte, Geolocalizacion zona) {
        return partidoCtrl.crearPartido(new PartidoDTO(
            deporte,
            3,
            60,
            zona,
            LocalDateTime.now().plusHours(1),
            admin
        ));
    }
}

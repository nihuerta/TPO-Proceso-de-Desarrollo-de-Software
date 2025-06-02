package app;

import controladores.*;
import modelos.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        
        // === CONTROLADORES ===
        UsuarioController usuarioCtrl = new UsuarioController();
        DeporteController deporteCtrl = new DeporteController();
        PartidoController partidoCtrl = new PartidoController();

        // === USUARIOS ===
        Usuario admin = new Usuario();
        admin.setNombre("Julián");
        admin.setCorreo("julian@futbolero.com");

        Usuario jugador1 = new Usuario();
        jugador1.setNombre("Valen");
        jugador1.setCorreo("valen@example.com");

        Usuario jugador2 = new Usuario();
        jugador2.setNombre("Martina");
        jugador2.setCorreo("martina@example.com");

        List<Usuario> jugadores = new ArrayList<>(List.of(jugador1, jugador2));
        List<Usuario> usuarios = new ArrayList<>(List.of(admin, jugador1, jugador2));

        // === ZONA ===
        Geolocalizacion zona = new Geolocalizacion();
        zona.setLatitud(10.0);
        zona.setLongitud(20.0);

        // Asignar zona a todos los usuarios
        usuarios.forEach(u -> u.setZona(zona));

        // === DEPORTE ===
        Deporte futbol = new Deporte("Fútbol", "5 vs 5", 3, "Reglas simples", NivelDeJuego.INTERMEDIO);
        deporteCtrl.agregarDeporte(futbol);

        // === CREAR PARTIDO ===
        PartidoDTO dto = new PartidoDTO();
        dto.administrador = admin;
        dto.cantJugadores = futbol.getCantidadJugadores();
        dto.duracion = 60;
        dto.zona = zona;
        dto.horario = LocalDateTime.now().plusSeconds(2);
        dto.deporte = futbol;

        Partido partido = partidoCtrl.crearPartido(dto);

        // === OBSERVADORES ===
        AdapterJavaMail adapter = new AdapterJavaMail();
        NotificacionEmail emailObserver = new NotificacionEmail(adapter);
        partido.agregarObservador(emailObserver);
        partido.agregarObservador(new NotificacionFireBase());

        // === ESTRATEGIA DE EMPAREJAMIENTO ===
        partidoCtrl.setEstrategia(partido, new EstrategiaCercania(partido));

        // === AGREGAR JUGADORES ===
        for (Usuario jugador : jugadores) {
            partidoCtrl.agregarJugador(partido, jugador); // cambia estado a PartidoArmado
        }

        // === CONFIRMAR PARTICIPACIÓN ===
        usuarios.forEach(partido::confirmarParticipacion); // todos confirman

        // === MOSTRAR USUARIOS ===
        System.out.println("\n👤 Usuarios registrados:");
        usuarios.forEach(u -> System.out.println(" - " + u.getNombre() + " (" + u.getCorreo() + ")"));

        // === MOSTRAR PARTIDO DISPONIBLE ===
        System.out.println("\n📋 Partido disponible:");
        System.out.println(" - Deporte: " + partido.getDeporte().getNombre());
        System.out.println(" - Estado: " + partido.getEstado().getClass().getSimpleName());
        System.out.println(" - Zona: (" + zona.getLatitud() + ", " + zona.getLongitud() + ")");
        System.out.println(" - Nivel de juego: " + futbol.getNivelDeJuego());
        System.out.println(" - Jugadores actuales:");
        partido.getJugadores().forEach(j -> System.out.println("   • " + j.getNombre()));

        // === ESPERAR HORARIO ===
        System.out.println("\n⏳ Esperando a que sea la hora del partido...");
        while (LocalDateTime.now().isBefore(partido.getHorario())) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // === INICIAR Y FINALIZAR PARTIDO ===
        partido.iniciarPartido();
        partido.finalizarPartido();

        System.out.println("\n✅ Simulación finalizada correctamente.");
    }
}

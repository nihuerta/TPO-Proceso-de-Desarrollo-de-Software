package app;

import controladores.DeporteController;
import controladores.NotificacionController;
import controladores.PartidoController;
import controladores.UsuarioController;
import dtos.PartidoDTO;
import dtos.UsuarioDTO;
import estrategias.EstrategiaCercania;
import estrategias.EstrategiaHistorial;
import estrategias.EstrategiaNivel;
import java.time.LocalDateTime;
import java.util.*;
import modelos.BaseDatos;
import modelos.Deporte;
import modelos.Geolocalizacion;
import modelos.Partido;
import modelos.Usuario;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Inicializar datos por defecto
        BaseDatos.init();

        // Controladores
        UsuarioController usuarioCtrl = new UsuarioController();
        DeporteController deporteCtrl = new DeporteController();
        PartidoController partidoCtrl = new PartidoController();

        // Configurar notificaciones
        NotificacionController notificacionCtrl = NotificacionController.getInstance();
        notificacionCtrl.configurarObservadores(partidoCtrl);

        // --- HILO DE TRANSICIÓN AUTOMÁTICA DE PARTIDOS ---
        Thread autoTransitionThread = new Thread(() -> {
            while (true) {
                try {
                    List<Partido> partidos = partidoCtrl.getPartidos();
                    for (Partido partido : partidos) {
                        String estado = partido.getEstado().getClass().getSimpleName();
                        java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
                        // Si está confirmado y es hora de iniciar
                        if (estado.equals("Confirmado") && !ahora.isBefore(partido.getHorario())) {
                            try {
                                partido.iniciarPartido();
                                System.out.println("[AUTO] Partido iniciado automáticamente: " + partido.getDeporte().getNombre());
                            } catch (Exception ignored) {}
                        }
                        // Si está en juego y ya terminó
                        if (estado.equals("EnJuego")) {
                            java.time.LocalDateTime fin = partido.getHorario().plusMinutes(partido.getDuracion());
                            if (!ahora.isBefore(fin)) {
                                try {
                                    partido.finalizarPartido();
                                    System.out.println("[AUTO] Partido finalizado automáticamente: " + partido.getDeporte().getNombre());
                                } catch (Exception ignored) {}
                            }
                        }
                    }
                    Thread.sleep(30000); // 30 segundos
                } catch (Exception e) {
                    // Silenciar errores del hilo
                }
            }
        });
        autoTransitionThread.setDaemon(true);
        autoTransitionThread.start();

        // Menú principal
        boolean salir = false;
        while (!salir) {
            try {
                System.out.println("\n--- Menú Principal ---");
                System.out.println("1. Registrar usuario");
                System.out.println("2. Crear deporte");
                System.out.println("3. Crear partido");
                System.out.println("4. Buscar partidos en mi zona");
                System.out.println("5. Unirse a un partido");
                System.out.println("6. Listar todos los partidos");
                System.out.println("7. Cancelar partido");
                System.out.println("8. Salir");
                System.out.print("Seleccione una opción: ");
                int opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        registrarUsuario(usuarioCtrl, deporteCtrl);
                        break;
                    case 2:
                        crearDeporteInteractivo(deporteCtrl);
                        break;
                    case 3:
                        crearPartidoInteractivo(partidoCtrl, deporteCtrl, usuarioCtrl);
                        break;
                    case 4:
                        buscarPartidosEnZona(partidoCtrl, usuarioCtrl);
                        break;
                    case 5:
                        unirseAPartido(partidoCtrl, usuarioCtrl);
                        break;
                    case 6:
                        listarPartidos(partidoCtrl, usuarioCtrl);
                        break;
                    case 7:
                        cancelarPartido(partidoCtrl, usuarioCtrl);
                        break;
                    case 8:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (Exception e) {
                System.out.println("Error en la entrada de datos. Por favor, intente nuevamente. Detalle: " + e.getMessage());
            }
        }
        System.out.println("¡Hasta luego!");
    }

    private static void registrarUsuario(UsuarioController usuarioCtrl, DeporteController deporteCtrl) {
        while (true) {
            try {
                System.out.println("\n--- Registro de Usuario ---");
                System.out.println("Para cancelar el registro en cualquier momento, escriba 'salir'");
                
                System.out.print("Nombre de usuario (solo letras y espacios): ");
                String nombre = scanner.nextLine();
                if (nombre.equalsIgnoreCase("salir")) {
                    System.out.println("Registro cancelado.");
                    return;
                }
                if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{2,50}")) {
                    System.out.println("Nombre inválido. Solo letras y espacios, mínimo 2 caracteres.");
                    continue;
                }

                UsuarioDTO usuarioDTO = new UsuarioDTO();
                usuarioDTO.setNombre(nombre);

                // Validar email
                while (true) {
                    System.out.print("Correo electrónico: ");
                    String correo = scanner.nextLine();
                    if (correo.equalsIgnoreCase("salir")) {
                        System.out.println("Registro cancelado.");
                        return;
                    }
                    if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                        System.out.println("Correo inválido. Debe tener formato usuario@dominio.com");
                    } else {
                        usuarioDTO.setCorreo(correo);
                        break;
                    }
                }

                // Validar contraseña
                while (true) {
                    System.out.print("Contraseña (mínimo 6 caracteres, al menos una letra y un número): ");
                    String contrasenia = scanner.nextLine();
                    if (contrasenia.equalsIgnoreCase("salir")) {
                        System.out.println("Registro cancelado.");
                        return;
                    }
                    if (!contrasenia.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")) {
                        System.out.println("Contraseña inválida. Debe tener al menos 6 caracteres, una letra y un número.");
                    } else {
                        usuarioDTO.setContrasenia(contrasenia);
                        break;
                    }
                }

                System.out.print("¿Desea ingresar un deporte favorito? (s/n): ");
                if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
                    List<Deporte> deportes = deporteCtrl.obtenerDeportes();
                    if (deportes.isEmpty()) {
                        System.out.println("No hay deportes cargados. Puede ingresar manualmente su deporte favorito.");
                        System.out.print("Ingrese el nombre del deporte: ");
                        String nombreDeporte = scanner.nextLine().trim();
                        usuarioDTO.setDeporteFav(nombreDeporte);
                    } else {
                        System.out.println("Deportes disponibles:");
                        for (int i = 0; i < deportes.size(); i++) {
                            System.out.println((i + 1) + ". " + deportes.get(i).getNombre());
                        }
                        System.out.println("0. Ingresar otro deporte");
                        System.out.print("Seleccione una opción: ");
                        int idx = Integer.parseInt(scanner.nextLine());
                        if (idx == 0) {
                            System.out.print("Ingrese el nombre del deporte: ");
                            String nombreDeporte = scanner.nextLine().trim();
                            usuarioDTO.setDeporteFav(nombreDeporte);
                        } else {
                            usuarioDTO.setDeporteFav(deportes.get(idx - 1).getNombre());
                        }
                    }
                }                System.out.print("¿Desea ingresar su nivel de juego? (s/n): ");
                String respNivel = scanner.nextLine().trim();
                if (respNivel.equalsIgnoreCase("s")) {
                    while (true) {
                        try {
                            System.out.println("1. PRINCIPIANTE\n2. INTERMEDIO\n3. AVANZADO");
                            System.out.print("Seleccione el nivel: ");
                            int nivel = Integer.parseInt(scanner.nextLine());
                            switch (nivel) {
                                case 1:
                                    usuarioDTO.setNivelDeJuego(tipos.NivelDeJuego.PRINCIPIANTE);
                                    break;
                                case 2:
                                    usuarioDTO.setNivelDeJuego(tipos.NivelDeJuego.INTERMEDIO);
                                    break;
                                case 3:
                                    usuarioDTO.setNivelDeJuego(tipos.NivelDeJuego.AVANZADO);
                                    break;
                                default:
                                    System.out.println("Opción inválida. Por favor seleccione 1, 2 o 3.");
                                    continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Por favor ingrese un número válido (1, 2 o 3).");
                        }
                    }
                } else {
                    // Si no se elige un nivel, establecer INTERMEDIO por defecto
                    usuarioDTO.setNivelDeJuego(tipos.NivelDeJuego.INTERMEDIO);
                }
                Usuario usuario = usuarioCtrl.crearUsuario(usuarioDTO);
                System.out.print("¿Desea ingresar su zona? (s/n): ");
                if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
                    Geolocalizacion zona = new Geolocalizacion();
                    while (true) {
                        try {
                            System.out.print("Latitud (ejemplo: -34.6037): ");
                            zona.setLatitud(Double.parseDouble(scanner.nextLine()));
                            System.out.print("Longitud (ejemplo: -58.3816): ");
                            zona.setLongitud(Double.parseDouble(scanner.nextLine()));
                            break;
                        } catch (Exception e) {
                            System.out.println("Latitud o longitud inválida. Intente nuevamente.");
                        }
                    }
                    usuarioCtrl.actualizarZona(usuario, zona);
                }
                System.out.println("Usuario registrado exitosamente!");
                break;
            } catch (Exception e) {
                System.out.println("Error en la entrada de datos. Por favor, intente nuevamente. Detalle: " + e.getMessage());
            }
        }
    }

    private static void crearDeporteInteractivo(DeporteController deporteCtrl) {
        while (true) {
            try {
                System.out.println("\n--- Crear Deporte ---");
                System.out.println("Para cancelar la creación en cualquier momento, escriba 'salir'");
                
                System.out.print("Nombre del deporte: ");
                String nombre = scanner.nextLine();
                if (nombre.equalsIgnoreCase("salir")) {
                    System.out.println("Creación de deporte cancelada.");
                    return;
                }

                System.out.print("Tipo (Equipo/Individual): ");
                String tipo = scanner.nextLine();
                if (tipo.equalsIgnoreCase("salir")) {
                    System.out.println("Creación de deporte cancelada.");
                    return;
                }

                System.out.print("Cantidad máxima de jugadores: ");
                String cantJugadoresStr = scanner.nextLine();
                if (cantJugadoresStr.equalsIgnoreCase("salir")) {
                    System.out.println("Creación de deporte cancelada.");
                    return;
                }
                int cantJugadores = Integer.parseInt(cantJugadoresStr);

                System.out.print("Categoría o reglas: ");
                String categoria = scanner.nextLine();
                if (categoria.equalsIgnoreCase("salir")) {
                    System.out.println("Creación de deporte cancelada.");
                    return;
                }

                if (nombre.equalsIgnoreCase("futbol") && cantJugadores > 22) {
                    System.out.println("Error: En fútbol no puede haber más de 22 jugadores en cancha.");
                    continue;
                }
                
                Deporte nuevo = new Deporte(nombre, tipo, cantJugadores, categoria, tipos.NivelDeJuego.INTERMEDIO);
                deporteCtrl.agregarDeporte(nuevo);
                System.out.println("Deporte creado exitosamente!");
                break;
            } catch (Exception e) {
                System.out.println("Error en la entrada de datos. Por favor, intente nuevamente. Detalle: " + e.getMessage());
            }
        }
    }

    private static void crearPartidoInteractivo(PartidoController partidoCtrl, DeporteController deporteCtrl, UsuarioController usuarioCtrl) {
        while (true) {
            try {
                System.out.println("\n--- Crear Partido ---");
                System.out.println("Para cancelar la creación en cualquier momento, escriba 'salir'");
                
                List<Deporte> deportes = deporteCtrl.obtenerDeportes();
                if (deportes.isEmpty()) {
                    System.out.println("No hay deportes disponibles. Cree uno primero.");
                    return;
                }
                for (int i = 0; i < deportes.size(); i++) {
                    System.out.println((i + 1) + ". " + deportes.get(i).getNombre());
                }
                System.out.print("Seleccione el deporte (o escriba 'salir' para cancelar): ");
                String seleccion = scanner.nextLine();
                if (seleccion.equalsIgnoreCase("salir")) {
                    System.out.println("Creación de partido cancelada.");
                    return;
                }
                int deporteIdx = Integer.parseInt(seleccion) - 1;
                Deporte deporte = deportes.get(deporteIdx);

                System.out.print("Cantidad de jugadores requeridos: ");
                String cantStr = scanner.nextLine();
                if (cantStr.equalsIgnoreCase("salir")) {
                    System.out.println("Creación de partido cancelada.");
                    return;
                }
                int cantJugadores = Integer.parseInt(cantStr);

                if (deporte.getNombre().equalsIgnoreCase("futbol") && cantJugadores > 22) {
                    System.out.println("Error: En fútbol no puede haber más de 22 jugadores en cancha.");
                    continue;
                }
                if (cantJugadores > deporte.getCantidadJugadores()) {
                    System.out.println("Error: La cantidad máxima de jugadores para " + deporte.getNombre() + " es " + deporte.getCantidadJugadores());
                    continue;
                }

                System.out.print("Duración del encuentro (minutos): ");
                String duracionStr = scanner.nextLine();
                if (duracionStr.equalsIgnoreCase("salir")) {
                    System.out.println("Creación de partido cancelada.");
                    return;
                }
                int duracion = Integer.parseInt(duracionStr);

                // Sugerir rango de latitud y longitud
                System.out.println("Ingrese la ubicación del partido.");
                System.out.println("Latitud recomendada entre -35.0 y -34.0 (CABA y alrededores)");
                System.out.println("Longitud recomendada entre -59.0 y -57.0 (CABA y alrededores)");
                double lat, lon;
                while (true) {
                    try {
                        System.out.print("Ubicación - Ingrese la latitud (ejemplo: -34.6037): ");
                        lat = Double.parseDouble(scanner.nextLine());
                        if (lat < -90 || lat > 90) {
                            System.out.println("Latitud inválida. Debe estar entre -90 y 90.");
                            continue;
                        }
                        if (lat < -35.0 || lat > -34.0) {
                            System.out.println("Advertencia: la latitud está fuera del rango recomendado para CABA y alrededores.");
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("Latitud inválida. Intente nuevamente.");
                    }
                }
                while (true) {
                    try {
                        System.out.print("Ubicación - Ingrese la longitud (ejemplo: -58.3816): ");
                        lon = Double.parseDouble(scanner.nextLine());
                        if (lon < -180 || lon > 180) {
                            System.out.println("Longitud inválida. Debe estar entre -180 y 180.");
                            continue;
                        }
                        if (lon < -59.0 || lon > -57.0) {
                            System.out.println("Advertencia: la longitud está fuera del rango recomendado para CABA y alrededores.");
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("Longitud inválida. Intente nuevamente.");
                    }
                }
                Geolocalizacion zona = new Geolocalizacion();
                zona.setLatitud(lat);
                zona.setLongitud(lon);
                // Pedir fecha y hora del partido
                LocalDateTime horario = pedirFechaHora();
                if (horario == null) {
                    System.out.println("Creación de partido cancelada.");
                    return;
                }

                List<Usuario> usuarios = usuarioCtrl.getUsuarios();
                if (usuarios.isEmpty()) {
                    System.out.println("No hay usuarios registrados. Registre uno primero.");
                    return;
                }
                for (int i = 0; i < usuarios.size(); i++) {
                    System.out.println((i + 1) + ". " + usuarios.get(i).getNombre());
                }
                System.out.print("Seleccione el usuario administrador: ");
                int adminIdx = Integer.parseInt(scanner.nextLine()) - 1;
                Usuario admin = usuarios.get(adminIdx);

                // --- NUEVO: Selección de estrategia de emparejamiento ---
                System.out.println("Seleccione la estrategia de emparejamiento:");
                System.out.println("1. Por nivel de juego");
                System.out.println("2. Por cercanía geográfica");
                System.out.println("3. Por historial con el organizador");
                System.out.print("Opción: ");
                int estrategiaOpt = Integer.parseInt(scanner.nextLine());
                estrategias.EstrategiaEmparejador estrategia = null;
                switch (estrategiaOpt) {
                    case 1: estrategia = new EstrategiaNivel(); break;
                    case 2: estrategia = new EstrategiaCercania(); break;
                    case 3: estrategia = new EstrategiaHistorial(); break;
                    default: estrategia = new EstrategiaNivel();
                }

                // --- NUEVO: Restricción de nivel ---
                boolean cualquierNivel = true;
                tipos.NivelDeJuego nivelMin = null;
                tipos.NivelDeJuego nivelMax = null;
                System.out.print("¿Permitir cualquier nivel de jugador? (s/n): ");
                String respNivel = scanner.nextLine().trim();
                if (respNivel.equalsIgnoreCase("n")) {
                    cualquierNivel = false;
                    System.out.println("Seleccione nivel mínimo:");
                    System.out.println("1. PRINCIPIANTE\n2. INTERMEDIO\n3. AVANZADO");
                    int minOpt = Integer.parseInt(scanner.nextLine());
                    switch (minOpt) {
                        case 1: nivelMin = tipos.NivelDeJuego.PRINCIPIANTE; break;
                        case 2: nivelMin = tipos.NivelDeJuego.INTERMEDIO; break;
                        case 3: nivelMin = tipos.NivelDeJuego.AVANZADO; break;
                        default: nivelMin = tipos.NivelDeJuego.PRINCIPIANTE;
                    }
                    System.out.println("Seleccione nivel máximo:");
                    System.out.println("1. PRINCIPIANTE\n2. INTERMEDIO\n3. AVANZADO");
                    int maxOpt = Integer.parseInt(scanner.nextLine());
                    switch (maxOpt) {
                        case 1: nivelMax = tipos.NivelDeJuego.PRINCIPIANTE; break;
                        case 2: nivelMax = tipos.NivelDeJuego.INTERMEDIO; break;
                        case 3: nivelMax = tipos.NivelDeJuego.AVANZADO; break;
                        default: nivelMax = tipos.NivelDeJuego.AVANZADO;
                    }
                }

                PartidoDTO partidoDTO = new PartidoDTO(
                    deporte,
                    cantJugadores,
                    duracion,
                    zona,
                    horario,
                    admin
                );
                partidoDTO.estrategia = estrategia;
                partidoDTO.cualquierNivel = cualquierNivel;
                partidoDTO.nivelMinimo = nivelMin;
                partidoDTO.nivelMaximo = nivelMax;
                partidoCtrl.crearPartido(partidoDTO);
                System.out.println("Partido creado exitosamente! Estado inicial: 'Necesitamos jugadores'");
                // --- NUEVO: Notificar a usuarios con deporte favorito ---
                List<Usuario> usuariosNotificar = usuarioCtrl.getUsuarios();
                for (Usuario u : usuariosNotificar) {
                    List<String> favs = u.getDeportesFav();
                    if (favs != null && favs.stream().filter(Objects::nonNull).anyMatch(f -> f.equals(deporte.getNombre()))) {                        // Simular notificación push y email
                        System.out.println("[NOTIFICACIÓN] Hola " + u.getNombre() + ", se ha creado un nuevo partido de tu deporte favorito: " + deporte.getNombre());
                        NotificacionController.getInstance().enviarEmail(u.getCorreo(), "Nuevo partido de tu deporte favorito", "Se ha creado un nuevo partido de " + deporte.getNombre() + ". ¡Únete desde la app!");
                    }
                }
                break;
            } catch (Exception e) {
                System.out.println("Error en la entrada de datos. Por favor, intente nuevamente. Detalle: " + e.getMessage());
            }
        }
    }

    private static void buscarPartidosEnZona(PartidoController partidoCtrl, UsuarioController usuarioCtrl) {
        while (true) {
            try {
                System.out.println("\n--- Buscar Partidos en mi Zona ---");
                System.out.println("Para cancelar la búsqueda en cualquier momento, escriba 'salir'");
                
                List<Usuario> usuarios = usuarioCtrl.getUsuarios();
                if (usuarios.isEmpty()) {
                    System.out.println("No hay usuarios registrados. Registre uno primero.");
                    return;
                }
                
                for (int i = 0; i < usuarios.size(); i++) {
                    System.out.println((i + 1) + ". " + usuarios.get(i).getNombre());
                }
                
                System.out.print("Seleccione su usuario (o escriba 'salir' para cancelar): ");
                String seleccion = scanner.nextLine();
                if (seleccion.equalsIgnoreCase("salir")) {
                    System.out.println("Búsqueda cancelada.");
                    return;
                }
                
                int usuarioIdx = Integer.parseInt(seleccion) - 1;
                if (usuarioIdx < 0 || usuarioIdx >= usuarios.size()) {
                    System.out.println("Selección inválida de usuario.");
                    continue;
                }
                
                Usuario usuario = usuarios.get(usuarioIdx);
                Geolocalizacion miZona = usuario.getZona();
                if (miZona == null) {
                    System.out.println("El usuario no tiene zona configurada.");
                    return;
                }
                List<Partido> partidos = partidoCtrl.getPartidos();
                boolean hay = false;
                for (Partido p : partidos) {
                    if (p.getZona().getLatitud().equals(miZona.getLatitud()) &&
                        p.getZona().getLongitud().equals(miZona.getLongitud()) &&
                        p.getJugadores().size() < p.getCantJugadores()) {
                        System.out.println("Partido: " + p.getDeporte().getNombre() + " | Jugadores: " + p.getJugadores().size() + "/" + p.getCantJugadores() + " | Estado: Necesitamos jugadores");
                        hay = true;
                    }
                }
                if (!hay) {
                    System.out.println("No hay partidos en su zona que necesiten jugadores.");
                }
                break;
            } catch (Exception e) {
                System.out.println("Error en la entrada de datos. Por favor, intente nuevamente. Detalle: " + e.getMessage());
            }
        }
    }

    private static void listarPartidos(PartidoController partidoCtrl, UsuarioController usuarioCtrl) {
        try {
            System.out.println("\n--- Todos los Partidos ---");
            List<Partido> partidos = partidoCtrl.getPartidos();
            if (partidos.isEmpty()) {
                System.out.println("No hay partidos disponibles.");
                return;
            }

            boolean hayPartidosArmados = false;
            for (int i = 0; i < partidos.size(); i++) {
                Partido p = partidos.get(i);
                String estado = p.getEstado().getClass().getSimpleName();
                System.out.println((i + 1) + ". " + p.getDeporte().getNombre() + " | Jugadores: " + 
                    p.getJugadores().size() + "/" + p.getCantJugadores() + 
                    " | Estado: " + estado + " | Administrador: " + p.getAdministrador().getNombre());
                
                if (estado.equals("PartidoArmado")) {
                    hayPartidosArmados = true;
                }
            }

            if (hayPartidosArmados) {
                System.out.print("\n¿Desea confirmar algún partido armado? (s/n): ");
                String respuesta = scanner.nextLine().trim().toLowerCase();
                
                if (respuesta.equals("s")) {
                    System.out.print("Seleccione el número del partido a confirmar: ");
                    int partidoIdx = Integer.parseInt(scanner.nextLine()) - 1;
                    
                    if (partidoIdx >= 0 && partidoIdx < partidos.size()) {
                        Partido partido = partidos.get(partidoIdx);
                        
                        if (!partido.getEstado().getClass().getSimpleName().equals("PartidoArmado")) {
                            System.out.println("Error: El partido seleccionado no está en estado 'Partido Armado'");
                            return;
                        }

                        System.out.println("\nSeleccione el usuario administrador:");
                        List<Usuario> usuarios = usuarioCtrl.getUsuarios();
                        for (int i = 0; i < usuarios.size(); i++) {
                            System.out.println((i + 1) + ". " + usuarios.get(i).getNombre());
                        }

                        System.out.print("Seleccione el número de usuario: ");
                        int usuarioIdx = Integer.parseInt(scanner.nextLine()) - 1;
                        
                        if (usuarioIdx >= 0 && usuarioIdx < usuarios.size()) {
                            Usuario usuario = usuarios.get(usuarioIdx);
                            
                            if (!usuario.equals(partido.getAdministrador())) {
                                System.out.println("Error: Solo el administrador puede confirmar el partido");
                                return;
                            }

                            try {
                                partido.cambiarEstado(new estados.Confirmado());
                                System.out.println("¡Partido confirmado exitosamente!");
                            } catch (Exception e) {
                                System.out.println("Error al confirmar el partido: " + e.getMessage());
                            }
                        } else {
                            System.out.println("Selección de usuario inválida.");
                        }
                    } else {
                        System.out.println("Selección de partido inválida.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar partidos: " + e.getMessage());
        }
    }

    private static void listarPartidosSinConfirmacion(PartidoController partidoCtrl) {
        try {
            System.out.println("\n--- Todos los Partidos ---");
            List<Partido> partidos = partidoCtrl.getPartidos();
            if (partidos.isEmpty()) {
                System.out.println("No hay partidos disponibles.");
                return;
            }

            for (int i = 0; i < partidos.size(); i++) {
                Partido p = partidos.get(i);
                String estado = p.getEstado().getClass().getSimpleName();
                System.out.println((i + 1) + ". " + p.getDeporte().getNombre() + " | Jugadores: " + 
                    p.getJugadores().size() + "/" + p.getCantJugadores() + 
                    " | Estado: " + estado + " | Administrador: " + p.getAdministrador().getNombre());
            }
        } catch (Exception e) {
            System.out.println("Error al listar partidos: " + e.getMessage());
        }
    }

    private static void unirseAPartido(PartidoController partidoCtrl, UsuarioController usuarioCtrl) {
        while (true) {
            try {
                System.out.println("\n--- Unirse a Partido ---");
                System.out.println("Para cancelar en cualquier momento, escriba 'salir'");
                
                List<Partido> partidos = partidoCtrl.getPartidos();
                if (partidos.isEmpty()) {
                    System.out.println("No hay partidos disponibles.");
                    return;
                }
                
                listarPartidosSinConfirmacion(partidoCtrl);
                System.out.print("Seleccione el número de partido al que desea unirse (o escriba 'salir' para cancelar): ");
                String seleccionPartido = scanner.nextLine();
                if (seleccionPartido.equalsIgnoreCase("salir")) {
                    System.out.println("Operación cancelada.");
                    return;
                }
                
                int partidoIdx = Integer.parseInt(seleccionPartido) - 1;
                if (partidoIdx < 0 || partidoIdx >= partidos.size()) {
                    System.out.println("Selección inválida de partido.");
                    continue;
                }
                
                Partido partido = partidos.get(partidoIdx);
                List<Usuario> usuarios = usuarioCtrl.getUsuarios();
                for (int i = 0; i < usuarios.size(); i++) {
                    System.out.println((i + 1) + ". " + usuarios.get(i).getNombre());
                }
                
                System.out.print("Seleccione el usuario (o escriba 'salir' para cancelar): ");
                String seleccionUsuario = scanner.nextLine();
                if (seleccionUsuario.equalsIgnoreCase("salir")) {
                    System.out.println("Operación cancelada.");
                    return;
                }
                
                int usuarioIdx = Integer.parseInt(seleccionUsuario) - 1;
                if (usuarioIdx < 0 || usuarioIdx >= usuarios.size()) {
                    System.out.println("Selección inválida de usuario.");
                    continue;
                }
                
                Usuario usuario = usuarios.get(usuarioIdx);
                // Validar requisitos antes de agregar
                boolean puedeUnirse = true;
                String motivo = "";
                if (partido.getJugadores().contains(usuario)) {
                    puedeUnirse = false;
                    motivo = "Ya está inscripto en el partido.";
                } else if (partido.estaCompleto()) {
                    puedeUnirse = false;
                    motivo = "El partido ya está completo.";
                } else if (partido.getEstrategia() != null && !partido.getEstrategia().cumpleRequisitos(usuario, partido)) {
                    puedeUnirse = false;
                    motivo = "No cumple con la estrategia de emparejamiento del partido.";
                } else if (!partido.isCualquierNivel() && usuario.getNivelDeJuego() != null) {
                    int nivelJugador = usuario.getNivelDeJuego().ordinal();
                    int min = partido.getNivelMinimo() != null ? partido.getNivelMinimo().ordinal() : 0;
                    int max = partido.getNivelMaximo() != null ? partido.getNivelMaximo().ordinal() : 2;
                    if (nivelJugador < min || nivelJugador > max) {
                        puedeUnirse = false;
                        motivo = "Su nivel de juego no está dentro del rango permitido para este partido.";
                    }
                }
                if (!puedeUnirse) {
                    System.out.println("No puede unirse al partido: " + motivo);
                    break;
                }
                partidoCtrl.agregarJugador(partido, usuario);
                System.out.println(usuario.getNombre() + " se ha unido al partido!");
                break;
            } catch (Exception e) {
                System.out.println("Error en la entrada de datos. Por favor, intente nuevamente. Detalle: " + e.getMessage());
            }
        }
    }

    private static void cancelarPartido(PartidoController partidoCtrl, UsuarioController usuarioCtrl) {
        while (true) {
            try {
                System.out.println("\n--- Cancelar Partido ---");
                System.out.println("Para cancelar esta operación en cualquier momento, escriba 'salir'");
                
                List<Partido> partidos = partidoCtrl.getPartidos();
                if (partidos.isEmpty()) {
                    System.out.println("No hay partidos disponibles.");
                    return;
                }
                
                listarPartidos(partidoCtrl, usuarioCtrl);
                System.out.print("Seleccione el número de partido a cancelar (o escriba 'salir' para cancelar): ");
                String seleccionPartido = scanner.nextLine();
                if (seleccionPartido.equalsIgnoreCase("salir")) {
                    System.out.println("Operación cancelada.");
                    return;
                }
                
                int partidoIdx = Integer.parseInt(seleccionPartido) - 1;
                if (partidoIdx < 0 || partidoIdx >= partidos.size()) {
                    System.out.println("Selección inválida de partido.");
                    continue;
                }
                
                Partido partido = partidos.get(partidoIdx);
                List<Usuario> usuarios = usuarioCtrl.getUsuarios();
                for (int i = 0; i < usuarios.size(); i++) {
                    System.out.println((i + 1) + ". " + usuarios.get(i).getNombre());
                }
                
                System.out.print("Seleccione el organizador (solo el organizador puede cancelar, o escriba 'salir' para cancelar): ");
                String seleccionUsuario = scanner.nextLine();
                if (seleccionUsuario.equalsIgnoreCase("salir")) {
                    System.out.println("Operación cancelada.");
                    return;
                }
                
                int usuarioIdx = Integer.parseInt(seleccionUsuario) - 1;
                if (usuarioIdx < 0 || usuarioIdx >= usuarios.size()) {
                    System.out.println("Selección inválida de usuario.");
                    continue;
                }
                
                Usuario usuario = usuarios.get(usuarioIdx);
                if (!usuario.equals(partido.getAdministrador())) {
                    System.out.println("Solo el organizador puede cancelar el partido.");
                    continue;
                }
                
                partidoCtrl.cancelarPartido(partido, usuario);
                System.out.println("Partido cancelado correctamente.");
                break;
            } catch (Exception e) {
                System.out.println("Error al cancelar partido: " + e.getMessage());
            }
        }
    }

    private static LocalDateTime pedirFechaHora() {
        while (true) {
            try {
                System.out.println("\nIngrese la fecha y hora del partido:");
                
                // Obtener la fecha
                System.out.print("Día (1-31): ");
                String diaStr = scanner.nextLine();
                if (diaStr.equalsIgnoreCase("salir")) return null;
                int dia = Integer.parseInt(diaStr);
                
                System.out.print("Mes (1-12): ");
                String mesStr = scanner.nextLine();
                if (mesStr.equalsIgnoreCase("salir")) return null;
                int mes = Integer.parseInt(mesStr);
                
                System.out.print("Año (ej: 2025): ");
                String anioStr = scanner.nextLine();
                if (anioStr.equalsIgnoreCase("salir")) return null;
                int anio = Integer.parseInt(anioStr);
                
                // Obtener la hora
                System.out.print("Hora (0-23): ");
                String horaStr = scanner.nextLine();
                if (horaStr.equalsIgnoreCase("salir")) return null;
                int hora = Integer.parseInt(horaStr);
                
                System.out.print("Minutos (0-59): ");
                String minutosStr = scanner.nextLine();
                if (minutosStr.equalsIgnoreCase("salir")) return null;
                int minutos = Integer.parseInt(minutosStr);
                
                // Validar que la fecha no sea pasada
                LocalDateTime fechaHora = LocalDateTime.of(anio, mes, dia, hora, minutos);
                LocalDateTime ahora = LocalDateTime.now();
                
                if (fechaHora.isBefore(ahora)) {
                    System.out.println("Error: La fecha y hora no puede ser anterior a la actual.");
                    continue;
                }
                
                return fechaHora;
                
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingrese números válidos.");
            } catch (Exception e) {
                System.out.println("Error: Fecha u hora inválida. Asegúrese de ingresar valores correctos.");
            }
        }
    }
}

package controladores;

import dtos.DeporteDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import modelos.BaseDatos;
import modelos.Deporte;
/**
 * Controlador para la gestión de Deportes.
 * Sigue el patrón de acceso directo a BaseDatos y uso de DTOs para entrada de datos.
 */
public class DeporteController {

    public DeporteController() {
        // No se necesita inicialización, usa BaseDatos estática.
    }

    /**
     * Crea un nuevo deporte a partir de un DTO y lo agrega a la base de datos.
     * @param deporteDTO El DTO con la información del deporte a crear.
     * @return El objeto Deporte creado y guardado.
     * @throws IllegalArgumentException si los datos del DTO son inválidos o el deporte ya existe.
     */
    public Deporte crearDeporte(DeporteDTO deporteDTO) {
        // 1. Validar el DTO de entrada
        if (deporteDTO == null) {
            throw new IllegalArgumentException("El DTO de deporte no puede ser nulo.");
        }
        if (deporteDTO.getNombre() == null || deporteDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del deporte no puede estar vacío.");
        }
        if (deporteDTO.getCantidadJugadores() <= 0) {
            throw new IllegalArgumentException("La cantidad de jugadores debe ser un número positivo.");
        }

        // 2. Verificar si ya existe un deporte con el mismo nombre (para evitar duplicados)
        boolean yaExiste = BaseDatos.deportes.stream()
                .anyMatch(d -> d.getNombre().equalsIgnoreCase(deporteDTO.getNombre().trim()));
        
        if (yaExiste) {
            throw new IllegalArgumentException("Ya existe un deporte con el nombre: " + deporteDTO.getNombre());
        }

        // 3. Convertir DTO a Modelo
        Deporte nuevoDeporte = new Deporte(
            deporteDTO.getNombre().trim(),
            deporteDTO.getDescripcion() != null ? deporteDTO.getDescripcion().trim() : "Sin descripción.",
            deporteDTO.getCantidadJugadores(),
            deporteDTO.getReglas() != null ? deporteDTO.getReglas().trim() : "Sin reglas especificadas."
        );

        // 4. Guardar directamente en la Base de Datos
        BaseDatos.deportes.add(nuevoDeporte);
        return nuevoDeporte;
    }

    /**
     * Obtiene la lista completa de deportes directamente de la base de datos.
     * @return Una lista de todos los deportes.
     */
    public List<Deporte> obtenerDeportes() {
        return BaseDatos.deportes;
    }

}
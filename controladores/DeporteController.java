package controladores;

import modelos.Deporte;
import modelos.BaseDatos;
import java.util.List;

public class DeporteController {
    public DeporteController() {
        // No inicializa lista
    }

    public void agregarDeporte(Deporte deporte) {
        BaseDatos.deportes.add(deporte);
    }

    public void actualizarDeporte(int index, Deporte deporte) {
        if (index >= 0 && index < BaseDatos.deportes.size()) {
            BaseDatos.deportes.set(index, deporte);
        }
    }

    public List<Deporte> obtenerDeportes() {
        return BaseDatos.deportes;
    }

    public void eliminarDeporte(int index) {
        if (index >= 0 && index < BaseDatos.deportes.size()) {
            BaseDatos.deportes.remove(index);
        }
    }
}

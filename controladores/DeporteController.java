package controladores;

import modelos.Deporte;
import java.util.ArrayList;
import java.util.List;

public class DeporteController {
    private List<Deporte> deportes;

    public DeporteController() {
        this.deportes = new ArrayList<>();
    }

    public void agregarDeporte(Deporte deporte) {
        deportes.add(deporte);
    }

    public void actualizarDeporte(int index, Deporte deporte) {
        if (index >= 0 && index < deportes.size()) {
            deportes.set(index, deporte);
        }
    }

    public List<Deporte> obtenerDeportes() {
        return deportes;
    }

    public void eliminarDeporte(int index) {
        if (index >= 0 && index < deportes.size()) {
            deportes.remove(index);
        }
    }
}

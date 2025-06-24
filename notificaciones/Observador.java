package notificaciones;

import modelos.Partido;

public interface Observador {
    void actualizar(Partido partido, String evento);
}

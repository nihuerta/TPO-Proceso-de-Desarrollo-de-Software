package notificaciones;

import modelos.Partido;
import modelos.Usuario;
import notificaciones.Notificacion;
import java.util.List;

public class NotificacionFireBase implements Observador {

    @Override
    public void actualizar(Partido partido, String evento) {
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(evento);
        List<Usuario> destinatarios = partido.getJugadores();
        for (Usuario usuario : destinatarios) {
            // Simulación de notificación push
            System.out.println(" Firebase Notification to " + usuario.getNombre() + ": " + notificacion.getMensaje());
        }
    }
}


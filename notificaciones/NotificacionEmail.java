package notificaciones;

import adaptadores.AdapterJavaMail;
import modelos.Partido;
import modelos.Usuario;
import notificaciones.Notificacion;
import java.util.List;

public class NotificacionEmail implements Observador {

    private AdapterJavaMail adapter;

    public NotificacionEmail(AdapterJavaMail adapter) {
        this.adapter = adapter;
    }

    @Override
    public void actualizar(Partido partido, String evento) {
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(evento);
        List<Usuario> destinatarios = partido.getJugadores();
        for (Usuario usuario : destinatarios) {
            notificacion.setEmailDestinatario(usuario.getEmail());
            adapter.enviarEmail(
                notificacion.getEmailDestinatario(),
                "Notificación de partido",
                notificacion.getMensaje()
            );
        }
    }
}

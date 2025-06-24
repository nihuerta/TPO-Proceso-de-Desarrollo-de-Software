import moduloNotificaciones.Notificacion;
import moduloNotificaciones.estrategias.adapters.NotificacionEmail.AdapterJavaMail;

public class NotificacionEmail implements Observador {

    private AdapterJavaMail adapter;

    public NotificacionEmail(AdapterJavaMail adapter) {
        this.adapter = adapter;
    }

    @Override
    public void actualizar(Partido partido, String evento) {
        Notificacion notificacion = new Notificacion();
        notificacion.mensaje = evento;
        notificacion.emailDestinatario = partido.getAdministrador().getEmail(); // o notificar a todos
        adapter.enviarEmail(notificacion);
    }
}

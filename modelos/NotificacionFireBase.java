import moduloNotificaciones.Notificacion;

public class NotificacionFireBase implements Observador {

    @Override
    public void actualizar(Partido partido, String evento) {
        Notificacion notificacion = new Notificacion();
        notificacion.mensaje = evento;
        System.out.println(" Firebase Notification: " + notificacion.mensaje);
        // Aquí se podría integrar con SDK Firebase
    }
}


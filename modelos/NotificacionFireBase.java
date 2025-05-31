package modelos;

public class NotificacionFireBase implements Observador {

    @Override
    public void actualizar(Partido partido, String evento) {
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(evento);
        System.out.println(" Firebase Notification: " + notificacion.getMensaje());
    }

}


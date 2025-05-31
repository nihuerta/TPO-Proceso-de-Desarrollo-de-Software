package modelos;

public class NotificacionEmail implements Observador {

    private AdapterJavaMail adapter;

    public NotificacionEmail(AdapterJavaMail adapter) {
        this.adapter = adapter;
    }

    @Override
    public void actualizar(Partido partido, String evento) {
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(evento);
        notificacion.setEmailDestinatario(partido.getAdministrador().getEmail()); // o notificar a todos
        adapter.enviarEmail(notificacion);
    }

}

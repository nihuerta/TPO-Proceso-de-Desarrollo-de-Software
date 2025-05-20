import moduloNotificaciones.Notificacion;
import moduloNotificaciones.estrategias.adapters.NotificacionEmail.AdapterNotificadorEmail;

public class NotificacionEmail implements Notificador {

	private AdapterJavaMail adapter;
	
	public void setAdapter(AdapterJavaMail adapter) {
		this.adapter = adapter;
	}

	public Email(AdapterJavaMail adapter) {
		super();
		this.adapter = adapter;
	}

	public void enviar(Notificacion notificacion) {
		this.adapter.enviarEmail(notificacion);
	}

}

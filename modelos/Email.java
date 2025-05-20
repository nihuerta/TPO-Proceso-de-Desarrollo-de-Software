package moduloNotificaciones.estrategias;

import moduloNotificaciones.Notificacion;
import moduloNotificaciones.estrategias.adapters.email.AdapterNotificadorEmail;

public class Email implements Notificador {

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

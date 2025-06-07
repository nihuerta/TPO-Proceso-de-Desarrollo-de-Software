package adaptadores;

import notificaciones.Notificacion;

public interface IAdapterEmail {
    void enviarEmail(Notificacion notificacion);
}

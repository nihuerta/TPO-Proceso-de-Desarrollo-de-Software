package controladores;

import adaptadores.IAdapterEmail;
import adaptadores.AdapterJavaMail;
import adaptadores.AdapterSendGrid;
import notificaciones.NotificacionEmail;
import notificaciones.NotificacionFireBase;

public class NotificacionController {
    private static NotificacionController instance;
    private final IAdapterEmail adapter;
    private final NotificacionEmail notificacionEmail;
    private final NotificacionFireBase notificacionFireBase;

    private NotificacionController() {
        this.adapter = new AdapterSendGrid();
        this.notificacionEmail = new NotificacionEmail(adapter);
        this.notificacionFireBase = new NotificacionFireBase();
    }

    public static NotificacionController getInstance() {
        if (instance == null) {
            instance = new NotificacionController();
        }
        return instance;
    }

    public void configurarObservadores(PartidoController partidoCtrl) {
        partidoCtrl.agregarObservador(notificacionEmail);
        partidoCtrl.agregarObservador(notificacionFireBase);
    }

    public void enviarEmail(String destinatario, String asunto, String mensaje) {
        adapter.enviarEmail(destinatario, asunto, mensaje);
    }
}
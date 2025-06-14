package controladores;

import adaptadores.AdapterJavaMail;
import notificaciones.NotificacionEmail;
import notificaciones.NotificacionFireBase;
import controladores.PartidoController;

public class NotificacionController {
    private static NotificacionController instance;
    private final AdapterJavaMail adapter;
    private final NotificacionEmail notificacionEmail;
    private final NotificacionFireBase notificacionFireBase;

    private NotificacionController() {
        this.adapter = new AdapterJavaMail();
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
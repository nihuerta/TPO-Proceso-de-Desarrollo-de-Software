package modelos;

public class AdapterJavaMail implements IAdapterEmail {

    @Override
    public void enviarEmail(Notificacion notificacion) {
        System.out.println("Email enviado a " + notificacion.getEmailDestinatario()
                + ": " + notificacion.getMensaje());
    }

}

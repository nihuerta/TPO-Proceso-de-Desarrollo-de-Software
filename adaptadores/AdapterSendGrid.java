package adaptadores;

public class AdapterSendGrid implements IAdapterEmail {
    @Override
    public void enviarEmail(String destinatario, String asunto, String mensaje) {
        // Simulación de envío de email a través de SendGrid
        System.out.println("[SENDGRID] Email enviado a " + destinatario);
        System.out.println("[SENDGRID] Asunto: " + asunto);
        System.out.println("[SENDGRID] Mensaje: " + mensaje);
        System.out.println("[SENDGRID] Estado: Enviado exitosamente");
    }
}

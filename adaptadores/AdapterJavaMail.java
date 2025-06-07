package adaptadores;

public class AdapterJavaMail {
    public void enviarEmail(String destinatario, String asunto, String mensaje) {
        // Simulación de envío de email
        System.out.println(" Email enviado a " + destinatario);
        System.out.println("Asunto: " + asunto);
        System.out.println("Mensaje: " + mensaje);
    }
} 
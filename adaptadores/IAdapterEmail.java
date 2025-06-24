package adaptadores;

public interface IAdapterEmail {
    void enviarEmail(String destinatario, String asunto, String mensaje);
}

package modelos;

public class GeolocalizacionDTO {
    private String latitud;
    private String longitud;

    public GeolocalizacionDTO(String latitud, String longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Getters y Setters
    public String getLatitud() { return latitud; }
    public void setLatitud(String latitud) { this.latitud = latitud; }
    public String getLongitud() { return longitud; }
    public void setLongitud(String longitud) { this.longitud = longitud; }
}
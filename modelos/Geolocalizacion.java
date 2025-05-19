package modelos;

public class Geolocalizacion {
    private Double latitud;
    private Double longitud;

    // Método para obtener DTO
    public GeolocalizacionDTO obtenerCoordenadas() {
        return new GeolocalizacionDTO(
            String.valueOf(latitud), 
            String.valueOf(longitud)
        );
    }

    // Getters y Setters
    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }
    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }
}
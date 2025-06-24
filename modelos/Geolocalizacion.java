package modelos;

import dtos.GeolocalizacionDTO;

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

    //  Método para usar en EstrategiaCercania
    public double distanciaA(Geolocalizacion otra) {
        double dx = this.latitud - otra.latitud;
        double dy = this.longitud - otra.longitud;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Getters y Setters
    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }
    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }
}

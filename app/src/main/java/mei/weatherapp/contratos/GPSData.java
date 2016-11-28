package mei.weatherapp.contratos;

public class GPSData {

    public float lon = -1;
    public float lat = -1;
    public String cidade = "";

    //construtor com float
    public GPSData(float lat, float lon, String city) {
        this.lat = lat;
        this.lon = lon;
        this.cidade = city;
    }

    //construtor com double
    public GPSData(double lat, double lon, String city) {
        this.lat = (float) lat;
        this.lon = (float) lon;
        this.cidade = city;
    }
}

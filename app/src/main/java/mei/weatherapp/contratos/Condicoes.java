package mei.weatherapp.contratos;


import java.io.Serializable;

//teste
public class Condicoes implements Serializable{
    private long id;
    private int WeatherIcon;
    private String WeatherText;
    private String Rating;
    private String Temperature;
    private String TemperatureMax;
    private String TemperatureMin;
    private String RelativeHumidity;
    private String WindSpeed;
    private String Data;

    public Condicoes() {
    }

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public String getData() {
        return Data == null ? "-" : Data;
    }

    public void setData(String data) {Data = data;}

    public String getRelativeHumidity() {
        return RelativeHumidity == null ? "-" : RelativeHumidity;
    }

    public void setRelativeHumidity(String relativeHumidity) {
        RelativeHumidity = relativeHumidity;
    }

    public String getTemperatureMax() {
        return TemperatureMax == null ? "-" : TemperatureMax;
    }

    public void setTemperatureMax(String temperature) {
        TemperatureMax = temperature;
    }

    public String getTemperatureMin() {
        return TemperatureMin == null ? "-" : TemperatureMin;
    }

    public void setTemperatureMin(String temperatureMin) {
        TemperatureMin = temperatureMin;
    }

    public String getTemperature() {
        return Temperature == null ? "-" : Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public int getWeatherIcon() {
        return WeatherIcon;
    }

    public void setWeatherIcon(int weatherIcon) {
        WeatherIcon = weatherIcon;
    }

    public String getWeatherText() {
        return WeatherText == null ? "-" : WeatherText;
    }

    public void setWeatherText(String weatherText) {
        WeatherText = weatherText;
    }

    public String getWindSpeed() {
        return WindSpeed == null ? "-" : WindSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        WindSpeed = windSpeed;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

}

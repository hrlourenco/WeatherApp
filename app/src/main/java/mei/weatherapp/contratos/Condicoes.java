package mei.weatherapp.contratos;


import java.io.Serializable;

//teste
public class Condicoes implements Serializable{
    private long id;
    private int WeatherIcon;
    private String WeatherText;
    private String Temperature;
    private String TemperatureMax;
    private String TemperatureMin;
    private String RelativeHumidity;
    private String WindDirection;
    private String WindSpeed;
    private String WindGust;
    private String UVIndex;
    private String UVIndexText;
    private String CloudCover;
    private String Pressure;
    private String PressureTendency;
    private String PrecipitationSummary;
    private String Data;

    public Condicoes() {
    }

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public String getCloudCover() {
        return CloudCover;
    }

    public void setCloudCover(String cloudCover) {
        CloudCover = cloudCover;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {Data = data;}

    public String getPrecipitationSummary() {
        return PrecipitationSummary;
    }

    public void setPrecipitationSummary(String precipitationSummary) {
        PrecipitationSummary = precipitationSummary;
    }

    public String getPressure() {
        return Pressure;
    }

    public void setPressure(String pressure) {
        Pressure = pressure;
    }

    public String getPressureTendency() {
        return PressureTendency;
    }

    public void setPressureTendency(String pressureTendency) {
        PressureTendency = pressureTendency;
    }

    public String getRelativeHumidity() {
        return RelativeHumidity;
    }

    public void setRelativeHumidity(String relativeHumidity) {
        RelativeHumidity = relativeHumidity;
    }

    public String getTemperatureMax() {
        return TemperatureMax;
    }

    public void setTemperatureMax(String temperature) {
        TemperatureMax = temperature;
    }

    public String getTemperatureMin() {
        return TemperatureMin;
    }

    public void setTemperatureMin(String temperatureMin) {
        TemperatureMin = temperatureMin;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getUVIndex() {
        return UVIndex;
    }

    public void setUVIndex(String UVIndex) {
        this.UVIndex = UVIndex;
    }

    public String getUVIndexText() {
        return UVIndexText;
    }

    public void setUVIndexText(String UVIndexText) {
        this.UVIndexText = UVIndexText;
    }

    public int getWeatherIcon() {
        return WeatherIcon;
    }

    public void setWeatherIcon(int weatherIcon) {
        WeatherIcon = weatherIcon;
    }

    public String getWeatherText() {
        return WeatherText;
    }

    public void setWeatherText(String weatherText) {
        WeatherText = weatherText;
    }

    public String getWindDirection() {
        return WindDirection;
    }

    public void setWindDirection(String windDirection) {
        WindDirection = windDirection;
    }

    public String getWindGust() {
        return WindGust;
    }

    public void setWindGust(String windGust) {
        WindGust = windGust;
    }

    public String getWindSpeed() {
        return WindSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        WindSpeed = windSpeed;
    }
}

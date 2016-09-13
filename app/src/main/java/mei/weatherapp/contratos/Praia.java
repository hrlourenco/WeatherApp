package mei.weatherapp.contratos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Praia implements Serializable{
    private int id;
    private String nome;
    private String longitude;
    private String latitude;
    private String morada;
    private int favorita;
    private String locationKey;

    private Condicoes condicoesActuais;
    private List<Condicoes> forecast;

    //CONSTRUTORES
    public Praia() {
    }

    public Praia(int favorita, int id, String latitude, String longitude, String nome, String morada) {
        this.favorita = favorita;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nome = nome;
        this.morada = morada;
        this.condicoesActuais = new Condicoes();
        this.forecast = new ArrayList<Condicoes>();
    }

    //GET & SET
    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public int getFavorita() {
        return favorita;
    }

    public void setFavorita(int favorita) {
        this.favorita = favorita;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocationKey() {
        return locationKey;
    }

    public void setLocationKey(String locationKey) {
        this.locationKey = locationKey;
    }

    public Condicoes getCondicoesActuais() {
        return condicoesActuais;
    }

    public void setCondicoesActuais(Condicoes condicoesActuais) {
        this.condicoesActuais = condicoesActuais;
    }

    public List<Condicoes> getForecast() {
        return forecast;
    }

    public void setForecast(List<Condicoes> forecast) {
        this.forecast = forecast;
    }

    @Override
    public String toString() {
        return "" + nome +
                ", " + longitude +
                ", " + latitude;
    }

}
package mei.weatherapp.contratos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Praia implements Serializable{
    private String praiaId;
    private String nome;
    private Float latitude;
    private Float longitude;
    private int rate;
    private Float temperatura;
    private Boolean favorito;

    //CONSTRUTORES
    public Praia() {
    }

    public Praia(String praiaId, String nome, Float latitude, Float longitude, int rate, Boolean favorito, float temperatura) {
        this.praiaId = praiaId;
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rate = rate;
        this.favorito = favorito;
        this.temperatura = temperatura;
    }

    //GET & SET
    public String getPraiaId() {
        return praiaId;
    }

    public void setPraiaId(String praiaId) {
        this.praiaId = praiaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public Float getTemperatura() { return temperatura; }

    public void setTemperatura(Float temperatura) { this.temperatura = temperatura; }
}

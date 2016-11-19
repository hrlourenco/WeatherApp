package mei.weatherapp.contratos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Praia implements Serializable{
  private String praiaId;
  private String nome;
  private Double latitude;
  private Double longitude;
  private int rate;
  private Float temperatura;
  private Boolean favorito;
  private String imagem;
  private int rating;
  private int numRating;

  //CONSTRUTORES
  public Praia() {
  }

  public Praia(String praiaId, String nome, Double latitude, Double longitude, int rate, Float temperatura, Boolean favorito, String imagem, int rating, int numRating) {
    this.praiaId = praiaId;
    this.nome = nome;
    this.latitude = latitude;
    this.longitude = longitude;
    this.rate = rate;
    this.temperatura = temperatura;
    this.favorito = favorito;
    this.imagem = imagem;
    this.rating = rating;
    this.numRating = numRating;
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

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public int getRate() {
    return rate;
  }

  public void setRate(int rate) {
    this.rate = rate;
  }

  public Float getTemperatura() {
    return temperatura;
  }

  public void setTemperatura(Float temperatura) {
    this.temperatura = temperatura;
  }

  public Boolean getFavorito() {
    return favorito;
  }

  public void setFavorito(Boolean favorito) {
    this.favorito = favorito;
  }

  public String getImagem() {
    return imagem;
  }

  public void setImagem(String imagem) {
    this.imagem = imagem;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public int getNumRating() {
    return numRating;
  }

  public void setNumRating(int numRating) {
    this.numRating = numRating;
  }
}

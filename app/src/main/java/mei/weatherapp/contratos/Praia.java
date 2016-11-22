package mei.weatherapp.contratos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Praia implements Serializable{
  private String praiaId;
  private String nome;
  private Double latitude;
  private Double longitude;
  private Boolean favorito;
  private String imagem;
  private Double rate;
  private int rating;
  private int numRating;
  private ArrayList<Condicoes> forecast;
  private String dataTempo;
  //dados do tempo actual
  private String icon;
  private Float temperatura;

  //CONSTRUTORES
  public Praia() {
  }

  public Praia(String dataTempo, Boolean favorito, ArrayList<Condicoes> forecast, String imagem, Double latitude
          , Double longitude, String nome, int numRating, String praiaId, Double rate, int rating, Float temperatura) {
    this.dataTempo = dataTempo;
    this.favorito = favorito;
    this.forecast = forecast;
    this.imagem = imagem;
    this.latitude = latitude;
    this.longitude = longitude;
    this.nome = nome;
    this.numRating = numRating;
    this.praiaId = praiaId;
    this.rate = rate;
    this.rating = rating;
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

  public Double getRate() {
    return rate;
  }

  public void setRate(Double rate) {
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

  public ArrayList<Condicoes> getForecast() {
    return forecast;
  }

  public void setForecast(ArrayList<Condicoes> forecast) {
    this.forecast = forecast;
  }

  public String getDataTempo() {
    return dataTempo;
  }

  public void setDataTempo(String dataTempo) {
    this.dataTempo = dataTempo;
  }

  public Praia doParsingAPIJsonToPraia(String apiPraia) {
    Praia resPraia = new Praia();
    try {
      JSONObject geral = new JSONObject(apiPraia);
      JSONObject praia = geral.getJSONObject("praia");
      resPraia.setPraiaId(praia.getString("_id"));
      resPraia.setNome(praia.getString("praia"));
      resPraia.setImagem(praia.getString("imagem"));
      resPraia.setDataTempo(praia.getString("dataTempo"));
      //pegar o array tempo
      String array = praia.getString("tempo");
      JSONArray tempo = new JSONArray(array);
      //pegar o primeiro elemento que contem a temperatura do dia actual
      JSONObject tempoActual = tempo.getJSONObject(0);
      resPraia.setTemperatura(Float.parseFloat(tempoActual.getString("tempMax")));
      //objecto rating
      try {
        JSONObject ratingObj = praia.getJSONObject("rating");
        resPraia.setRating(ratingObj.getInt("ratingGeral"));
        resPraia.setNumRating(ratingObj.getInt("ratingGeralNum"));
        Double rateAux = 0.0;
        if (resPraia.getNumRating() > 0) {
          int a = resPraia.getRating();
          int b = resPraia.getNumRating();
          rateAux = ((double) a / (double) b);
        } else
          rateAux = -1.0;
        resPraia.setRate(rateAux);
      } catch (JSONException e) {
        resPraia.setRating(0);
        resPraia.setNumRating(0);
        resPraia.setRate(0.0);
      }

    } catch (JSONException e) {
      e.printStackTrace();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return resPraia;
  }

}

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
  private Date dataTempo;
  //dados do tempo actual
  private String icon;
  private Double temperatura;

  //CONSTRUTORES
  public Praia() {
  }

  public Praia(Date dataTempo, Boolean favorito, ArrayList<Condicoes> forecast, String imagem, Double latitude
          , Double longitude, String nome, int numRating, String praiaId, Double rate, int rating, Double temperatura) {
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

  public Double getTemperatura() {
    return temperatura;
  }

  public void setTemperatura(Double temperatura) {
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

  public Date getDataTempo() {
    return dataTempo;
  }

  public void setDataTempo(Date dataTempo) {
    this.dataTempo = dataTempo;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public Praia doParsingAPIJsonToPraia(String apiPraia) {
    Praia resPraia = new Praia();
    try {
      JSONObject geral = new JSONObject(apiPraia);
      JSONObject praia = geral.getJSONObject("praia");
      resPraia.setPraiaId(praia.getString("_id"));
      resPraia.setNome(praia.getString("praia"));
      resPraia.setImagem(praia.getString("imagem"));
      resPraia.setDataTempo(new Date(praia.getString("dataTempo")));
      //pegar o array tempo
      String array = praia.getString("tempo");
      JSONArray tempo = new JSONArray(array);
      //pegar o primeiro elemento que contem a temperatura do dia actual
      JSONObject tempoActual = tempo.getJSONObject(0);
      if(tempoActual.has("tempMax"))
        resPraia.setTemperatura(Double.parseDouble(tempoActual.getString("tempMax")));
      if(tempoActual.has("icon"))
        resPraia.setIcon(tempoActual.getString("icon"));
      ArrayList<Condicoes> auxArrayCondicoes = new ArrayList<Condicoes>();
      for( int i = 0; i < tempo.length(); i++) {
        JSONObject auxTempo = tempo.getJSONObject(i);
        Condicoes auxCondicoes = new Condicoes();
        if(auxTempo.has("tempMin"))
          auxCondicoes.setTempMin(auxTempo.getDouble("tempMin"));
        if(auxTempo.has("tempMax"))
          auxCondicoes.setTempMax(auxTempo.getDouble("tempMax"));
        if(auxTempo.has("vento"))
          auxCondicoes.setVento(auxTempo.getDouble("vento"));
        if(auxTempo.has("humidade"))
          auxCondicoes.setHumidade(auxTempo.getDouble("humidade"));
        if(auxTempo.has("pressao"))
          auxCondicoes.setPressao(auxTempo.getDouble("pressao"));
        if(auxTempo.has("mensagem"))
          auxCondicoes.setMensagem(auxTempo.getString("mensagem"));
        if(auxTempo.has("icon"))
          auxCondicoes.setIcon(auxTempo.getString("icon"));
        auxArrayCondicoes.add(auxCondicoes);
      }
      resPraia.setForecast(auxArrayCondicoes);
      //objecto rating
      try {
        Double rateAux = 0.0;
        if(praia.has("rating")) {
          JSONObject ratingObj = praia.getJSONObject("rating");
          resPraia.setRating(ratingObj.getInt("ratingGeral"));
          resPraia.setNumRating(ratingObj.getInt("ratingGeralNum"));
          if (resPraia.getNumRating() > 0) {
            int a = resPraia.getRating();
            int b = resPraia.getNumRating();
            rateAux = ((double) a / (double) b);
          } else
            rateAux = -0.0;
        }
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

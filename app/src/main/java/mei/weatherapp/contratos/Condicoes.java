package mei.weatherapp.contratos;
import java.io.Serializable;

public class Condicoes implements Serializable{
  private String icon;
  private String mensagem;
  private Double pressao;
  private Double vento;
  private Double humidade;
  private Double tempMax;
  private Double tempMin;

  public Condicoes() {
  }

  public Condicoes(Double humidade, String icon, String mensagem, Double pressao, Double tempMax, Double tempMin, Double vento) {
    this.humidade = humidade;
    this.icon = icon;
    this.mensagem = mensagem;
    this.pressao = pressao;
    this.tempMax = tempMax;
    this.tempMin = tempMin;
    this.vento = vento;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getMensagem() {
    return mensagem;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }

  public Double getPressao() {
    return pressao;
  }

  public void setPressao(Double pressao) {
    this.pressao = pressao;
  }

  public Double getHumidade() {
    return humidade;
  }

  public void setHumidade(Double humidade) {
    this.humidade = humidade;
  }

  public Double getTempMax() {
    return tempMax;
  }

  public void setTempMax(Double tempMax) {
    this.tempMax = tempMax;
  }

  public Double getTempMin() {
    return tempMin;
  }

  public void setTempMin(Double tempMin) {
    this.tempMin = tempMin;
  }

  public Double getVento() {
    return vento;
  }

  public void setVento(Double vento) {
    this.vento = vento;
  }
}

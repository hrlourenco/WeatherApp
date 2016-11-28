package mei.weatherapp;

/**
 * Classe para funcoes genéricas auxiliares
 */
public class Utils {
  //dado um inteiro devolve uma string com o código da imagem em drawable
  public static String MakeAWImageString(int imageId)
  {
    String im = imageId < 10 ? "0" + Integer.toString(imageId) : Integer.toString(imageId);
    return "aw" + im + "s";
  }

  //dado um icon devolve o seu drawable
  //esta a fazer replace de "-" por "" e prefixo "icon_"
  public static String IconWeatherString(String name) {
    return ("icon_" + name.replace("-",""));
  }
}
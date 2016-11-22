package mei.weatherapp.webservice;

import org.json.JSONException;
import org.json.JSONObject;
import mei.weatherapp.contratos.APIData;

public class WeatherIPCAWebService extends WebserviceConnector {

    public WeatherIPCAWebService() {
    }

    //get praia from API without login
    public String doGetPraiasNoLogin(String nomePraia, Double latitude, Double longitude) {
        JSONObject dataPost = new JSONObject();
        JSONObject coordenadas = new JSONObject();
        String result = null;

        try {
            dataPost.put("praia",nomePraia);
                coordenadas.put("lat", latitude);
                coordenadas.put("long", longitude);
            dataPost.put("coordenadas", coordenadas);
            buildWebserviceCall(APIData.WeatherIPCA.ENDPOINT_GETPRAIA, null);
            result = postToWebserviceJson(dataPost);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    //get praia from API with login
    public String doGetPraiasLogin(String nomePraia, Double latitude, Double longitude, String userId) {
        JSONObject dataPost = new JSONObject();
        JSONObject coordenadas = new JSONObject();
        String result = null;

        try {
            dataPost.put("praia",nomePraia);
                coordenadas.put("lat", Double.toString(latitude));
                coordenadas.put("long", Double.toString(longitude));
            dataPost.put("coordenadas", coordenadas);
            dataPost.put("userId",userId);
            buildWebserviceCall(APIData.WeatherIPCA.ENDPOINT_GETPRAIA, null);
            result = postToWebserviceJson(dataPost);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}

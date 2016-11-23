package mei.weatherapp.webservice;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import mei.weatherapp.contratos.APIData;
import mei.weatherapp.contratos.User;

public class WeatherIPCAWebService extends WebserviceConnector {

    private final String ENDPOINT = "http://weatheripca.herokuapp.com/api/v1/";

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

    public String doLogin(String passwordHash){
        buildWebserviceCall(APIData.WeatherIPCA.ENDPOINT_USERS + passwordHash + "/", null);
        String res = getWebserviceResponse();

        return res;
    }

    public String doAddUser(String username, String passwordHash){
        JSONObject data = new JSONObject();
        String result = null;
        try {
            data.put("username", username);
            data.put("passwordHash", passwordHash);
            buildWebserviceCall(APIData.WeatherIPCA.ENDPOINT, null);
            result = postToWebserviceJson(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}

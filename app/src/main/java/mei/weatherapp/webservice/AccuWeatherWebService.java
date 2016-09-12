package mei.weatherapp.webservice;

import org.json.JSONException;
import org.json.JSONObject;

import mei.weatherapp.contratos.APIData;
import mei.weatherapp.contratos.Praia;

//http://developer.accuweather.com/apis
public class AccuWeatherWebService extends WebserviceConnector {
    private final String APIKEY = "vplLxDyBIiRtS44dUIX0NiGLNCZFpzW9";
    private final String ENDPOINT = "http://dataservice.accuweather.com/";

    public AccuWeatherWebService() {
    }

    public Praia doSearchLocationGeoPosition(Praia praia)
    {
        if(praia!=null)
        {
            if(praia.getLongitude()!=null && praia.getLatitude()!=null)
            {
                String urlGeoLocation = APIData.Accuweather.getGeoPositionURL(praia.getLatitude(), praia.getLongitude(),true);
                buildWebserviceCallURLString(urlGeoLocation);
                String dataJson = getWebserviceResponse();
                if(dataJson!=null)
                {
                    try
                    {
                        JSONObject jo = new JSONObject(dataJson);
                        praia.setLocationKey(jo.getString("Key"));
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                        praia = null;
                    }
                }
                else
                {
                    praia = null;
                }
            }
        }
        return praia;
    }

    public String getCurrentConditions(Praia praia, Boolean details)
    {
        String urlCurrentCondition = APIData.Accuweather.getCurrentConditionURL(praia.getLocationKey(), details);
        buildWebserviceCallURLString(urlCurrentCondition);
        return getWebserviceResponse();
    }
}

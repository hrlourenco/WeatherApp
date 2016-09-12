package mei.weatherapp.webservice;

import java.util.HashMap;

/**
 * Created by joaofaria on 09/09/16.
 */
public class GeonamesWebservice  extends WebserviceConnector{
  private final String ENDPOINT = "http://api.geonames.org/searchJSON";

  private final String Q_PARAM = "q";

  private final String MAXROWS_PARAM = "maxRows";
  private final String MAXROWS_VALUE = "1";

  private final String USERNAME_PARAM = "username";
  private final String USERNAME_VALUE = "a8836";

  private final String COUNTRY_PARAM = "country";
  private final String COUNTRY_VALUE = "PT";

  public GeonamesWebservice() {

  }

  public String doGetCoordinates(String localName) {
    String call_uri_base = ENDPOINT;

    HashMap<String, String> params = new HashMap<>();
    params.put(Q_PARAM, localName);
    params.put(MAXROWS_PARAM, MAXROWS_VALUE);
    params.put(USERNAME_PARAM, USERNAME_VALUE);
    params.put(COUNTRY_PARAM, COUNTRY_VALUE);

    buildWebserviceCall(call_uri_base, params);
    String res = getWebserviceResponse();

    return res;
  }
}

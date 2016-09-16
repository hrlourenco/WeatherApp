package mei.weatherapp.webservice;

import java.util.HashMap;


public class PanoramioWebservice extends WebserviceConnector{
  private final String ENDPOINT = "http://www.panoramio.com/map/get_panoramas.php";

  private final String SET_PARAM = "set";
  private final String SET_VALUE = "full";

  private final String FROM_PARAM = "from";
  private final String FROM_VALUE = "0";

  private final String TO_PARAM = "to";
  private final String TO_VALUE = "1";

  private final String MINX_PARAM = "minx";

  private final String MINY_PARAM = "miny";

  private final String MAXX_PARAM = "maxx";

  private final String MAXY_PARAM = "maxy";

  private final String SIZE_PARAM = "size";
  private final String SIZE_VALUE = "medium";

  private final String MAP_FILTER_PARAM = "mapfilter";
  private final String MAP_FILTER_VALUE = "true";

  public PanoramioWebservice() {

  }

  public String doGetImage(String minx, String miny, String maxx, String maxy){
    String call_uri_base = ENDPOINT;

    HashMap<String, String> params = new HashMap<>();
    params.put(SET_PARAM, SET_VALUE);
    params.put(FROM_PARAM, FROM_VALUE);
    params.put(TO_PARAM, TO_VALUE);
    params.put(MINX_PARAM, minx);
    params.put(MINY_PARAM, miny);
    params.put(MAXX_PARAM, maxx);
    params.put(MAXY_PARAM, maxy);
    params.put(SIZE_PARAM, SIZE_VALUE);
    params.put(MAP_FILTER_PARAM, MAP_FILTER_VALUE);

    buildWebserviceCall(call_uri_base, params);
    String res = getWebserviceResponse();

    return res;
  }
}

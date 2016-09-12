package mei.weatherapp.contratos;

//Dados para utilização da API
public class APIData {
    public static class Accuweather
    {
        public static final String APIKEY = "vplLxDyBIiRtS44dUIX0NiGLNCZFpzW9";
        public static final String ENDPOINT = "http://dataservice.accuweather.com";

        private static String METHOD_GeoPosition  = ENDPOINT + "/locations/v1/cities/geoposition/search?apikey={APIKEY}&q={LATITUDE}%2C{LONGITUDE}&language=pt-pt&toplevel={MAISPROXIMA}";
        private static String METHOD_CurrentConditions = ENDPOINT + "/currentconditions/v1/{LOCATIONKEY}?apikey={APIKEY}&details={DETAILS}&language=pt-pt";

        public static String getGeoPositionURL(String latitude, String longitude, Boolean localidadeMaisProxima)
        {
            return METHOD_GeoPosition.replace("{APIKEY}", APIKEY).replace("{LATITUDE}",latitude).replace("{LONGITUDE}",longitude).replace("{MAISPROXIMA}",localidadeMaisProxima.toString());
        }

        public static String getCurrentConditionURL(String locationKey, Boolean details)
        {
            return METHOD_CurrentConditions.replace("{APIKEY}", APIKEY).replace("{LOCATIONKEY}",locationKey).replace("{DETAILS}",details.toString());
        }
    }
}

package mei.weatherapp.contratos;

//Dados para utilização da API
public class APIData {
    public static class Accuweather
    {
        public static final String APIKEY = "O97qaitWJ4JSFahihuN7BYeWJ7yl0tYb";
        public static final String ENDPOINT = "http://dataservice.accuweather.com";

        private static String METHOD_GeoPosition  = ENDPOINT + "/locations/v1/cities/geoposition/search?apikey={APIKEY}&q={LATITUDE}%2C{LONGITUDE}&language=pt-pt&toplevel={MAISPROXIMA}";
        private static String METHOD_CurrentConditions = ENDPOINT + "/currentconditions/v1/{LOCATIONKEY}?apikey={APIKEY}&details={DETAILS}&language=pt-pt";
        private static String METHOD_forecasts = ENDPOINT + "/forecasts/v1/daily/5day/{LOCATIONKEY}?apikey={APIKEY}&details={DETAILS}&language=pt-pt&metric=true";

        public static String getGeoPositionURL(String latitude, String longitude, Boolean localidadeMaisProxima)
        {
            return METHOD_GeoPosition.replace("{APIKEY}", APIKEY).replace("{LATITUDE}",latitude).replace("{LONGITUDE}",longitude).replace("{MAISPROXIMA}",localidadeMaisProxima.toString());
        }

        public static String getCurrentConditionURL(String locationKey, Boolean details)
        {
            return METHOD_CurrentConditions.replace("{APIKEY}", APIKEY).replace("{LOCATIONKEY}",locationKey).replace("{DETAILS}",details.toString());
        }
        public static String getForecastURL(String locationKey, Boolean details){
            return METHOD_forecasts.replace("{APIKEY}", APIKEY).replace("{LOCATIONKEY}",locationKey).replace("{DETAILS}",details.toString());
        }
    }
}

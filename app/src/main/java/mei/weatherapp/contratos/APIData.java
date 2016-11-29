package mei.weatherapp.contratos;

import java.util.HashMap;

//Dados para utilização da API
public class APIData {
    public static class WeatherIPCA
    {
        public static final String ENDPOINT = "https://weatheripca.herokuapp.com/api/v1";

        public static final String ENDPOINT_GETPRAIA  = ENDPOINT + "/praias/";

        public static final String ENDPOINT_RATE  = ENDPOINT + "/rate/";

        public static final String ENDPOINT_USERS  = ENDPOINT + "/users/";

        public static final String ENDPOINT_UPLOAD_FOTOS  = ENDPOINT + "/file_upload/";

    }
}

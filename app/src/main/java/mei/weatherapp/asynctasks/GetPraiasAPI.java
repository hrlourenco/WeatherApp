package mei.weatherapp.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mei.weatherapp.contratos.Condicoes;
import mei.weatherapp.contratos.Praia;
import mei.weatherapp.webservice.AccuWeatherWebService;
import mei.weatherapp.webservice.WeatherIPCAWebService;
import mei.weatherapp.webservice.WebserviceConnector;


public class GetPraiasAPI extends AsyncTask<Praia, Integer, Praia> {
    RelativeLayout load;
    String txtUserId;
    TextView txtPercentagem;
    ImageView imgTemp;
    TextView txtTemp;
    TextView txtMsg;

    Context ctx;
    Integer totalLoads = 2;

    public GetPraiasAPI(Context ctx, ImageView imgTemp, RelativeLayout load
            , TextView txtMsg, TextView txtPercentagem, TextView txtTemp, String userId) {
        this.ctx = ctx;
        this.imgTemp = imgTemp;
        this.load = load;
        this.totalLoads = totalLoads;
        this.txtMsg = txtMsg;
        this.txtPercentagem = txtPercentagem;
        this.txtTemp = txtTemp;
        this.txtUserId = txtUserId;
    }

    @Override
    protected Praia doInBackground(Praia... praias) {
        Praia praiaLocal = new Praia();
        praiaLocal = praias[0];

        publishProgress(-1); //aceder à api

        WeatherIPCAWebService ws = new WeatherIPCAWebService();
        publishProgress(-2); //a obter json
        String teste = ws.doGetPraiasNoLogin(praiaLocal.getNome(),praiaLocal.getLatitude(),praiaLocal.getLongitude());

        Praia resPraia = new Praia();
        resPraia = resPraia.doParsingAPIJsonToPraia(teste);

        return resPraia;
    }

    @Override
    protected void onPostExecute(Praia praia) {
        super.onPostExecute(praia);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if(values[0]==-1)
            this.txtPercentagem.setText("A comunicar com API");
        else if(values[0]==-2)
            this.txtPercentagem.setText("A obter dados Json");
        else
            this.txtPercentagem.setText("Loading " + values[0]/totalLoads + " %");
    }
}

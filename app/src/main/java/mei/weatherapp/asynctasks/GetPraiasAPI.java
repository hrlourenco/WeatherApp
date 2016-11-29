package mei.weatherapp.asynctasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.renderscript.Double2;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mei.weatherapp.MainActivity;
import mei.weatherapp.Utils;
import mei.weatherapp.contratos.Condicoes;
import mei.weatherapp.contratos.Praia;
import mei.weatherapp.contratos.Proximas;
import mei.weatherapp.contratos.User;
import mei.weatherapp.interfaces.AsyncResponse;
import mei.weatherapp.webservice.AccuWeatherWebService;
import mei.weatherapp.webservice.WeatherIPCAWebService;
import mei.weatherapp.webservice.WebserviceConnector;


public class GetPraiasAPI extends AsyncTask<Praia, Integer, Praia> {

    public AsyncResponse delegate = null;

    RelativeLayout load;
    String userId;
    TextView txtPercentagem;
    ImageView imgTemp;
    TextView txtTemp;
    TextView txtRate;
    TextView txtMsg;
    TextView txtUser;
    boolean pesquisaManual;
    User user;

    Context ctx;
    Integer totalLoads = 2;

    public GetPraiasAPI(Context ctx, ImageView imgTemp, RelativeLayout load
            , TextView txtMsg, TextView txtPercentagem, TextView txtTemp, TextView txtRate, TextView txtUser, String userId, boolean pesquisaManual, AsyncResponse delegate) {
        this.ctx = ctx;
        this.imgTemp = imgTemp;
        this.load = load;
        this.txtMsg = txtMsg;
        this.txtPercentagem = txtPercentagem;
        this.txtTemp = txtTemp;
        this.txtRate = txtRate;
        this.delegate = delegate;
        this.pesquisaManual = pesquisaManual;
        this.txtUser = txtUser;
        this.userId = userId;
    }

    @Override
    protected Praia doInBackground(Praia... praias) {
        Praia praiaLocal = new Praia();
        praiaLocal = praias[0];

        publishProgress(-1); //aceder à api

        WeatherIPCAWebService ws = new WeatherIPCAWebService();
        publishProgress(-2); //a obter json

        String teste = null;
        if(userId == null) {
            teste = ws.doGetPraiasNoLogin(praiaLocal.getNome(), praiaLocal.getLatitude(), praiaLocal.getLongitude());
        } else {
            teste = ws.doGetPraiasLogin(praiaLocal.getNome(), praiaLocal.getLatitude(), praiaLocal.getLongitude(), userId);
            JSONObject res = null;
            try {
                JSONObject geral = new JSONObject(teste);
                if(geral.has("user")) {
                    JSONObject userJson = geral.getJSONObject("user");
                    user = new User(userJson.getString("_id"), userJson.getString("username"), userJson.getInt("credito"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Praia resPraia = new Praia();
        if(teste != null) {
            resPraia = resPraia.doParsingAPIJsonToPraia(teste);
            ArrayList<Proximas> proximas = resPraia.doParsingProximas(teste);
            resPraia.setProximas(proximas);
        }

        return resPraia;
    }

    @Override
    protected void onPostExecute(Praia praia) {
        super.onPostExecute(praia);
        Integer val = (int)Math.round(praia.getRate());
        switch (val) {
            case 0: this.txtMsg.setText("Muito Mau"); break;
            case 1: this.txtMsg.setText("Bom"); break;
            case 2: this.txtMsg.setText("Muito Bom"); break;
            default: this.txtMsg.setText("Melhor.... Impossível");
        }

        String iconTempo = praia.getIcon();
        iconTempo = "icon_" + iconTempo.replace("-","");
        int id = ctx.getResources().getIdentifier(Utils.IconWeatherString(praia.getIcon()),"drawable", ctx.getPackageName());
        this.imgTemp.setImageResource(id);
        this.txtTemp.setText(String.format("%.1f",praia.getTemperatura()) + "º C");
        this.txtRate.setText(String.format("%.1f",praia.getRate()) + "");
        if(user != null) {
            this.txtUser.setText(user.getUsername() + " :: " + user.getCreditos() + " créditos");
        }

        this.load.setVisibility(View.GONE);

        Intent intentMain = new Intent(ctx, MainActivity.class);
        intentMain.putExtra("praia", praia);

        delegate.processFinish(praia);

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

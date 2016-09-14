package mei.weatherapp.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mei.weatherapp.Utils;
import mei.weatherapp.contratos.Condicoes;
import mei.weatherapp.contratos.Praia;
import mei.weatherapp.webservice.AccuWeatherWebService;

public class AccuweatherCurrentConditions extends AsyncTask<Praia, Integer, Praia> {
    Context ctx;

    TextView txtAdress;
    ImageView imgTemp;
    TextView txtTemp;
    TextView txtMsg;
    RelativeLayout load;


    public AccuweatherCurrentConditions(Context ctx, ImageView imgTemp, TextView txtAdress, TextView txtTemp, TextView txtMsg, RelativeLayout load) {
        this.ctx = ctx;
        this.imgTemp = imgTemp;
        this.txtAdress = txtAdress;
        this.txtTemp = txtTemp;
        this.txtMsg = txtMsg;
        this.load = load;
    }

    @Override
    protected Praia doInBackground(Praia... praias) {
        Praia p = new Praia();

        AccuWeatherWebService aw = new AccuWeatherWebService();
        p = aw.doSearchLocationGeoPosition(praias[0]);

        String dataJson = aw.getCurrentConditions(praias[0], false);
        if(dataJson!=null)
        {
            Condicoes condicoes = new Condicoes();
            try
            {
                JSONArray ja = new JSONArray(dataJson);
                JSONObject jo = ja.getJSONObject(0);
                condicoes.setWeatherText(jo.getString("WeatherText"));
                condicoes.setWeatherIcon(jo.getInt("WeatherIcon"));

                JSONObject joTemp = jo.getJSONObject("Temperature");
                JSONObject joTempMetric = joTemp.getJSONObject("Metric");
                condicoes.setTemperature(Integer.toString(joTempMetric.getInt("Value")));

                p.setCondicoesActuais(condicoes);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            p = null;
        }
        return p;
    }

    @Override
    protected void onPostExecute(Praia praia) {
        super.onPostExecute(praia);

        Condicoes c = praia.getCondicoesActuais();

        this.txtAdress.setText(praia.getMorada());
        this.txtMsg.setText(c.getWeatherText());
        int id = ctx.getResources().getIdentifier(Utils.MakeAWImageString(c.getWeatherIcon()),"drawable", ctx.getPackageName());
        this.imgTemp.setImageResource(id);
        this.txtTemp.setText(c.getTemperature() + "º C");
        this.load.setVisibility(View.GONE);
    }
}

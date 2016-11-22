package mei.weatherapp.asynctasks;

import android.content.Context;
import android.content.Intent;
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

    int totalLoads = 1;

    RelativeLayout load;
    TextView txtPercentagem;
    ImageView imgTemp;
    TextView txtTemp;
    TextView txtMsg;



    public AccuweatherCurrentConditions(Context ctx, RelativeLayout load, TextView txtPercentagem,
                                        ImageView imgTemp, TextView txtTemp, TextView txtMsg) {
        this.ctx = ctx;
        this.txtPercentagem = txtPercentagem;
        this.imgTemp = imgTemp;
        this.load = load;
        this.txtMsg = txtMsg;
        this.txtTemp = txtTemp;
    }

    @Override
    protected Praia doInBackground(Praia... praias) {
        Praia p = new Praia();
        /*

        publishProgress(-1); //aceder à api

        AccuWeatherWebService aw = new AccuWeatherWebService();
        p = aw.doSearchLocationGeoPosition(praias[0]);

        publishProgress(-2); //a obter json
        String dataJson = aw.getCurrentConditions(p, true);
        if(dataJson!=null)
        {
            Condicoes condicoes = new Condicoes();
            try
            {
                JSONObject jo1;
                JSONObject jo2;
                JSONObject jo3;

                JSONArray ja = new JSONArray(dataJson);
                JSONObject jo = ja.getJSONObject(0);//geral

                condicoes.setWeatherText(jo.getString("WeatherText"));
                condicoes.setWeatherIcon(jo.getInt("WeatherIcon"));
                condicoes.setRelativeHumidity(Integer.toString(jo.getInt("RelativeHumidity")));

                //Temperatura
                jo1 = jo.getJSONObject("Temperature");
                jo2 = jo1.getJSONObject("Metric");
                condicoes.setTemperature(Integer.toString(jo2.getInt("Value")));
                publishProgress(1);


                //p.setCondicoesActuais(condicoes);
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
        */
        return p;
    }

    @Override
    protected void onPostExecute(Praia praia) {
        super.onPostExecute(praia);
/*
        Condicoes c = null; // = praia.getCondicoesActuais();

        this.txtMsg.setText(c.getWeatherText());
        int id = ctx.getResources().getIdentifier(Utils.MakeAWImageString(c.getWeatherIcon()),"drawable", ctx.getPackageName());
        this.imgTemp.setImageResource(id);
        this.txtTemp.setText(c.getTemperature() + "º C");

        this.load.setVisibility(View.GONE);*/
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

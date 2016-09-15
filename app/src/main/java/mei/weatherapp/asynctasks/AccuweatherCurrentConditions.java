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

    int totalLoads = 7;

    RelativeLayout load;
    TextView txtPercentagem;
    TextView txtAdress;
    ImageView imgTemp;
    TextView txtTemp;
    TextView txtMsg;
    TextView txtHumidade;
    TextView txtVento;
    TextView txtRajadas;
    TextView txtRaiosUV;
    TextView txtNuvens;
    TextView txtPressao;
    TextView txtPrecipitacao;


    public AccuweatherCurrentConditions(Context ctx, RelativeLayout load, TextView txtPercentagem, TextView txtAdress, ImageView imgTemp, TextView txtTemp, TextView txtMsg, TextView txtHumidade, TextView txtVento, TextView txtRajadas, TextView txtRaiosUV, TextView txtNuvens, TextView txtPressao, TextView txtPrecipitacao) {
        this.ctx = ctx;
        this.txtPercentagem = txtPercentagem;
        this.imgTemp = imgTemp;
        this.load = load;
        this.txtAdress = txtAdress;
        this.txtHumidade = txtHumidade;
        this.txtMsg = txtMsg;
        this.txtNuvens = txtNuvens;
        this.txtPrecipitacao = txtPrecipitacao;
        this.txtPressao = txtPressao;
        this.txtRaiosUV = txtRaiosUV;
        this.txtRajadas = txtRajadas;
        this.txtTemp = txtTemp;
        this.txtVento = txtVento;
    }

    @Override
    protected Praia doInBackground(Praia... praias) {
        Praia p = new Praia();

        publishProgress(-1); //aceder à api

        AccuWeatherWebService aw = new AccuWeatherWebService();
        p = aw.doSearchLocationGeoPosition(praias[0]);

        publishProgress(-2); //a obter json
        String dataJson = aw.getCurrentConditions(praias[0], true);
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

                //Vento
                jo1 = jo.getJSONObject("Wind");
                jo2 = jo1.getJSONObject("Direction");
                condicoes.setWindDirection(Integer.toString(jo2.getInt("Degrees")) + " " + jo2.getString("Localized"));
                jo2 = jo1.getJSONObject("Speed");
                jo3 = jo2.getJSONObject("Metric");
                condicoes.setWindSpeed(Double.toString(jo3.getDouble("Value")) + " " + jo3.getString("Unit"));
                publishProgress(2);


                //Rajadas
                jo1 = jo.getJSONObject("WindGust");
                jo2 = jo1.getJSONObject("Speed");
                jo3 = jo2.getJSONObject("Metric");
                condicoes.setWindGust(Integer.toString(jo3.getInt("Value")) + " " + jo3.getString("Unit"));
                publishProgress(3);

                //Rais UV
                condicoes.setUVIndex(Integer.toString(jo.getInt("UVIndex")));
                condicoes.setUVIndexText(jo.getString("UVIndexText"));
                publishProgress(4);

                //Nuvens
                condicoes.setCloudCover(Integer.toString(jo.getInt("CloudCover")));
                publishProgress(5);

                //Pressão
                jo1 = jo.getJSONObject("Pressure");
                jo2 = jo1.getJSONObject("Metric");
                condicoes.setPressure(Integer.toString(jo2.getInt("Value")) + " " + jo2.getString("Unit"));
                jo1 = jo.getJSONObject("PressureTendency");
                condicoes.setPressureTendency(jo1.getString("LocalizedText"));
                publishProgress(6);

                //Resumo Precipitação
                jo1 = jo.getJSONObject("PrecipitationSummary");
                jo2 = jo1.getJSONObject("Precipitation");
                jo3 = jo2.getJSONObject("Metric");
                condicoes.setPrecipitationSummary(Integer.toString(jo3.getInt("Value")) + " " + jo3.getString("Unit"));
                publishProgress(7);

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

        this.txtHumidade.setText(c.getRelativeHumidity());
        this.txtNuvens.setText(c.getCloudCover());
        this.txtPrecipitacao.setText(c.getPrecipitationSummary());
        this.txtPressao.setText(c.getPressure() + ", " + c.getPressureTendency());
        this.txtRaiosUV.setText(c.getUVIndex() + ", " + c.getUVIndexText());
        this.txtRajadas.setText(c.getWindGust());
        this.txtVento.setText(c.getWindSpeed() + "(" + c.getWindDirection() + ")");

        this.load.setVisibility(View.GONE);
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

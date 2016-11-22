package mei.weatherapp.asynctasks;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import mei.weatherapp.BeachDetails;
import mei.weatherapp.R;
import mei.weatherapp.adapter.forecastAdapter;
import mei.weatherapp.contratos.Condicoes;
import mei.weatherapp.contratos.Praia;
import mei.weatherapp.webservice.AccuWeatherWebService;


public class AccuweatherForecast extends AsyncTask<Praia, Integer, Praia> {

  private ListView lv_forecast;
  private Context ctx;


  public AccuweatherForecast(ListView lv_forecast, Context ctx) {
    this.lv_forecast = lv_forecast;
    this.ctx = ctx;
  }

  @Override
  protected Praia doInBackground(Praia... praias) {
    List<Condicoes> lista = new ArrayList<Condicoes>();
    Condicoes cond;
    Praia p = praias[0];
/*
    AccuWeatherWebService ws = new AccuWeatherWebService();

    try {
      JSONObject jsonObjForecast = new JSONObject(ws.getForecast(praias[0], true));
      JSONArray jsonArrDailyForecasts = jsonObjForecast.getJSONArray("DailyForecasts");
      for (int i = 0; i < 5; i++) {
        cond = new Condicoes();
        JSONObject jsonObjDay = jsonArrDailyForecasts.getJSONObject(i);
        cond.setTemperatureMin(Double.toString(jsonObjDay.getJSONObject("Temperature").getJSONObject("Minimum").getDouble("Value")));
        cond.setTemperatureMax(Double.toString(jsonObjDay.getJSONObject("Temperature").getJSONObject("Maximum").getDouble("Value")));
        cond.setWeatherIcon(jsonObjDay.getJSONObject("Day").getInt("Icon"));
        String date = jsonObjDay.getString("Date");
        cond.setData(date.substring(0, 10));
        cond.setWindSpeed(Double.toString(jsonObjDay.getJSONObject("Day").getJSONObject("Wind").getJSONObject("Speed").getDouble("Value")));
        cond.setWeatherText(jsonObjDay.getJSONObject("Day").getString("LongPhrase"));
        lista.add(cond);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    p.setForecast(lista);
*/
    return p;
  }

  @Override
  protected void onPostExecute(Praia praia) {
    super.onPostExecute(praia);

    lv_forecast.setAdapter(new forecastAdapter(ctx, praia.getForecast()));


  }
}


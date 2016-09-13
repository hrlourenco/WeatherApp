package mei.weatherapp.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mei.weatherapp.Utils;
import mei.weatherapp.contratos.Condicoes;
import mei.weatherapp.contratos.Praia;
import mei.weatherapp.webservice.AccuWeatherWebService;

public class AccuweatherCurrentConditions extends AsyncTask<Praia, Void, Praia> {
    Context ctx;

    TextView txtLat;
    TextView txtLong;
    TextView txtAdress;
    ImageView imgTemp;
    TextView txtTemp;

    public AccuweatherCurrentConditions(Context ctx, ImageView imgTemp, TextView txtAdress, TextView txtLat, TextView txtLong, TextView txtTemp) {
        this.ctx = ctx;
        this.imgTemp = imgTemp;
        this.txtAdress = txtAdress;
        this.txtLat = txtLat;
        this.txtLong = txtLong;
        this.txtTemp = txtTemp;
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

        this.txtLat.setText(praia.getLatitude());
        this.txtLong.setText(praia.getLongitude());
        this.txtAdress.setText(praia.getMorada());
        this.txtTemp.setText(c.getWeatherText());
        int id = ctx.getResources().getIdentifier(Utils.MakeAWImageString(c.getWeatherIcon()),"drawable", ctx.getPackageName());
        this.imgTemp.setImageResource(id);
    }
}

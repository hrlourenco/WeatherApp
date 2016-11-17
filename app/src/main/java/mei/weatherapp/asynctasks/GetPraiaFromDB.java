package mei.weatherapp.asynctasks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mei.weatherapp.MainActivity;
import mei.weatherapp.Utils;
import mei.weatherapp.basedados.MyOpenHelper;
import mei.weatherapp.contratos.Condicoes;
import mei.weatherapp.contratos.Praia;


public class GetPraiaFromDB extends AsyncTask<Void, Void, Praia> {
    private Context ctx;
    private MyOpenHelper moh;
    private TextView txtAdress;
    private TextView txtMsg;
    private TextView txtTemp;
    private ImageView imgTemp;
    private RelativeLayout load;
    private TextView txtLocationKey;
    private TextView txtLongitude;
    private TextView txtLatitude;
    private TextView txtNome;

    public GetPraiaFromDB(Context ctx, TextView txtAdress, TextView txtMsg, TextView txtTemp, ImageView imgTemp
            , RelativeLayout load,TextView txtLocationKey, TextView txtLatitude, TextView txtLongitude, TextView nome) {
        this.ctx = ctx;
        this.txtAdress = txtAdress;
        this.txtMsg = txtMsg;
        this.txtTemp = txtTemp;
        this.imgTemp = imgTemp;
        this.load = load;
        this.txtLocationKey = txtLocationKey;
        this.txtLatitude = txtLatitude;
        this.txtLongitude = txtLongitude;
        this.txtNome=nome;


        moh = new MyOpenHelper(ctx);
    }

    @Override
    protected Praia doInBackground(Void... voids) {
        SQLiteDatabase db = moh.getWritableDatabase();

        Praia praia = moh.getFromPraias(db);

        return praia;
    }

    @Override
    protected void onPostExecute(Praia praia) {
        super.onPostExecute(praia);

        if(praia.getLocationKey()!=null){
            Condicoes c = praia.getCondicoesActuais();

            txtAdress.setText(praia.getMorada());
            txtMsg.setText(c.getWeatherText());
            int id = ctx.getResources().getIdentifier(Utils.MakeAWImageString(c.getWeatherIcon()),"drawable", ctx.getPackageName());
            imgTemp.setImageResource(id);
            txtTemp.setText(c.getTemperature() + "º C");
            txtLocationKey.setText(praia.getLocationKey());
            txtLongitude.setText(praia.getLongitude());
            txtLatitude.setText(praia.getLatitude());
            txtNome.setText(praia.getNome());

            load.setVisibility(View.GONE);
        }

    }
}

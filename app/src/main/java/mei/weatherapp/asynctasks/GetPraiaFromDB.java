package mei.weatherapp.asynctasks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mei.weatherapp.Utils;
import mei.weatherapp.basedados.MyOpenHelper;
import mei.weatherapp.contratos.Condicoes;
import mei.weatherapp.contratos.Praia;


public class GetPraiaFromDB extends AsyncTask<Void, Void, Praia> {

    private Context ctx;
    private MyOpenHelper moh;
    private TextView txtMsg;
    private TextView txtTemp;
    private TextView txtRate;
    private ImageView imgTemp;
    private RelativeLayout load;
    private TextView txtLongitude;
    private TextView txtLatitude;
    private TextView txtNome;

    public GetPraiaFromDB(Context ctx, TextView txtMsg, TextView txtTemp, TextView txtRate, ImageView imgTemp
            , RelativeLayout load, TextView txtLatitude, TextView txtLongitude, TextView nome) {
        this.ctx = ctx;
        this.txtMsg = txtMsg;
        this.txtTemp = txtTemp;
        this.txtRate = txtRate;
        this.imgTemp = imgTemp;
        this.load = load;
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
/*
//        if(praia.getLocationKey()!=null){
            Condicoes c = praia.getForecast().get(0);

            txtMsg.setText(c.getMensagem());
            int id = ctx.getResources().getIdentifier(c.getIcon(),"drawable", ctx.getPackageName());
            imgTemp.setImageResource(id);
            txtTemp.setText(c.getTempMax() + "º C");
            txtRate.setText(praia.getRate() + "");
            txtLongitude.setText(praia.getLongitude().toString());
            txtLatitude.setText(praia.getLatitude().toString());
            txtNome.setText(praia.getNome());

            load.setVisibility(View.GONE);
//        }
*/
    }
}

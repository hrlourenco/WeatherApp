package mei.weatherapp.asynctasks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import mei.weatherapp.basedados.MyOpenHelper;
import mei.weatherapp.contratos.Praia;
import mei.weatherapp.contratos.User;
import mei.weatherapp.webservice.WeatherIPCAWebService;


public class RatePraiaAPI extends AsyncTask<Praia, Void, Praia> {
    String auxMessage;
    String auxRate;
    String internalError;
    JSONObject auxUser;

    TextView txtRate;
    TextView txtUser;
    int rating;
    String userId;
    String praiaId;
    Context ctx;

    public RatePraiaAPI(Context ctx, String praiaId, int rating, TextView txtUser, TextView txtRate, String userId) {
        this.ctx = ctx;
        this.praiaId = praiaId;
        this.rating = rating;
        this.txtRate = txtRate;
        this.txtUser = txtUser;
        this.userId = userId;

        auxRate = (String) txtRate.getText();
        internalError = "";
    }

    @Override
    protected Praia doInBackground(Praia... params) {
        Praia praiaLocal = new Praia();
        praiaLocal = params[0];

        WeatherIPCAWebService ws = new WeatherIPCAWebService();
        String teste = ws.doRatePraia(userId, praiaId, rating);

        JSONObject geral = null;
        try {
            geral = new JSONObject(teste);
            if(geral.has("internalErrorCode")) {
                if(geral.getInt("internalErrorCode") == 150) {
                    internalError = "Não é possível fazer rating";
                } else {
                    internalError = "Erro: " + geral.getString("Message");
                }
            } else {
                if (geral.has("auxUser")) {
                    auxUser = geral.getJSONObject("auxUser");
                    User u = new User();
                    u.setUserId(auxUser.getString("_id"));
                    u.setUsername(auxUser.getString("username"));
                    u.setCreditos(auxUser.getInt("credito"));

                    MyOpenHelper moh = new MyOpenHelper(this.ctx);
                    SQLiteDatabase db = moh.getWritableDatabase();
                    moh.deleteFromUsers(db);
                    moh.insertIntoUsers(db, u);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Praia praia) {
        super.onPostExecute(praia);
        if(auxUser != null) {
            try {
                txtUser.setText(auxUser.getString("username") + " :: " + auxUser.getInt("credito") + " créditos");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(internalError!=null) {
            Toast.makeText(this.ctx, internalError, Toast.LENGTH_LONG).show();
        }
    }
}

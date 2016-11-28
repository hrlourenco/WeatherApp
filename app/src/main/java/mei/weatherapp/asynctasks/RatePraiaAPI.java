package mei.weatherapp.asynctasks;

import android.os.AsyncTask;
import android.widget.TextView;

import org.w3c.dom.Text;

import mei.weatherapp.contratos.Praia;


public class RatePraiaAPI extends AsyncTask<Praia, Void, Praia> {
    TextView txtRate;
    TextView txtMessageRate;

    @Override
    protected Praia doInBackground(Praia... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Praia praia) {
        super.onPostExecute(praia);
    }
}

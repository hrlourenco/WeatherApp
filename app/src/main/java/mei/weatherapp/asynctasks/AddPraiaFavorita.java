package mei.weatherapp.asynctasks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import mei.weatherapp.basedados.MyOpenHelper;
import mei.weatherapp.contratos.Praia;


public class AddPraiaFavorita extends AsyncTask<Praia, Void, Void> {
  private Context ctx;
  private MyOpenHelper moh;

  public AddPraiaFavorita(Context ctx) {
    this.ctx = ctx;
    moh = new MyOpenHelper(ctx);
  }

  @Override
  protected Void doInBackground(Praia... praias) {
    SQLiteDatabase db = moh.getWritableDatabase();

    moh.insertIntoPaias(db, praias[0]);

    return null;
  }
}

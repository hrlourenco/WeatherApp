package mei.weatherapp.asynctasks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import mei.weatherapp.basedados.MyOpenHelper;


public class DeletePraiaFavorita extends AsyncTask<Void, Void, Void> {
  private Context ctx;
  private MyOpenHelper moh;

  public DeletePraiaFavorita(Context ctx) {
    this.ctx = ctx;
    moh = new MyOpenHelper(ctx);
  }

  @Override
  protected Void doInBackground(Void... voids) {
    SQLiteDatabase db = moh.getWritableDatabase();

    moh.deleteFromPraias(db);

    return null;
  }
}

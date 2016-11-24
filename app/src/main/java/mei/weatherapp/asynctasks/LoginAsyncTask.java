package mei.weatherapp.asynctasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import mei.weatherapp.MainActivity;
import mei.weatherapp.basedados.MyOpenHelper;
import mei.weatherapp.contratos.User;
import mei.weatherapp.webservice.WeatherIPCAWebService;

public class LoginAsyncTask extends AsyncTask<String, Void, User> {

  private Context ctx;
  private User user;
  private JSONObject res;
  private MyOpenHelper moh;

  public LoginAsyncTask(Context ctx) {
    moh = new MyOpenHelper(ctx);
    this.ctx = ctx;
  }

  @Override
  protected User doInBackground(String... strings) {
    try {
      WeatherIPCAWebService ws = new WeatherIPCAWebService();
      res = new JSONObject(ws.doLogin(strings[0]));
      if(!res.has("internalErrorCode")){
        user = new User(res.getString("_id"), res.getString("username"));
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }

    SQLiteDatabase db = moh.getWritableDatabase();
    moh.deleteFromUsers(db);
    moh.insertIntoUsers(db, user);

    return user;
  }

  @Override
  protected void onPostExecute(User user) {
    try {
      if(res.getInt("internalErrorCode")==100){
        Toast toast = Toast.makeText(ctx, "Erro no servidor", Toast.LENGTH_SHORT);
        toast.show();
      }
      if(res.getInt("internalErrorCode")==101){
        Toast toast = Toast.makeText(ctx, "Utilizador não encontrado", Toast.LENGTH_SHORT);
        toast.show();
      }
      if(res.getInt("internalErrorCode")==102){
        Toast toast = Toast.makeText(ctx, "Acesso negado", Toast.LENGTH_SHORT);
        toast.show();
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    if(user != null){
      Activity a = (Activity) ctx;
      Intent intenteMain = new Intent(ctx, MainActivity.class);
      intenteMain.putExtra("user", user);
      a.setResult(Activity.RESULT_OK);
      a.finish();
    }
  }

}

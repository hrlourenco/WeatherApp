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
import mei.weatherapp.contratos.Praia;
import mei.weatherapp.contratos.User;
import mei.weatherapp.webservice.WeatherIPCAWebService;

/**
 * Created by joaofaria on 23/11/16.
 */

public class AddUserAsyncTask extends AsyncTask<Void, Void, User> {

  private Context ctx;
  private User user;
  private String username;
  private String password;
  private MyOpenHelper moh;
  private JSONObject res;
  private Boolean keep;

  public AddUserAsyncTask(Context ctx, String username, String password, Boolean keep) {
    this.ctx = ctx;
    this.username = username;
    this.password = password;
    this.keep = keep;
    moh = new MyOpenHelper(ctx);
  }

  @Override
  protected User doInBackground(Void... voids) {
    try {
      WeatherIPCAWebService ws = new WeatherIPCAWebService();
      res = new JSONObject(ws.doAddUser(username, username + password));
      if (!res.has("internalErrorCode")){
        user = new User(res.getString("_id"), res.getString("username"), res.getInt("credito"));

        if(keep){
          SQLiteDatabase db = moh.getWritableDatabase();
          moh.deleteFromUsers(db);
          moh.insertIntoUsers(db, user);
        }

      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return user;
  }

  @Override
  protected void onPostExecute(User user) {
    try {
      if(res.getInt("internalErrorCode")==100){
        Toast toast = Toast.makeText(ctx, "Erro no servidor", Toast.LENGTH_SHORT);
        toast.show();
      }
      if(res.getInt("internalErrorCode")==103){
        Toast toast = Toast.makeText(ctx, "Utilizador já existente", Toast.LENGTH_SHORT);
        toast.show();
      }
      if(res.getInt("internalErrorCode")==104){
        Toast toast = Toast.makeText(ctx, "Dados inválidos", Toast.LENGTH_SHORT);
        toast.show();
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    if(user != null){
      Activity a = (Activity) ctx;
      a.setResult(Activity.RESULT_OK, new Intent(ctx, MainActivity.class).putExtra("user", user));
      a.finish();
    }
  }
}

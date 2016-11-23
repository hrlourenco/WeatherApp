package mei.weatherapp.asynctasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import mei.weatherapp.MainActivity;
import mei.weatherapp.contratos.User;
import mei.weatherapp.webservice.WeatherIPCAWebService;

public class LoginAsyncTask extends AsyncTask<String, Void, User> {

  private Context ctx;
  private User user;

  public LoginAsyncTask(Context ctx) {
    this.ctx = ctx;
  }

  @Override
  protected User doInBackground(String... strings) {
    try {
      WeatherIPCAWebService ws = new WeatherIPCAWebService();
      JSONObject res = new JSONObject(ws.doLogin(strings[0]));
      if(res.has("internalErrorCode")){
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
      }else {
        user = new User(res.getString("_id"), res.getString("username"));
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return user;
  }

  @Override
  protected void onPostExecute(User user) {
    if(user != null){
      Activity a = (Activity) ctx;
      Intent intenteMain = new Intent(ctx, MainActivity.class);
      intenteMain.putExtra("user", user);
      a.setResult(Activity.RESULT_OK);
      a.finish();
    }
  }

}

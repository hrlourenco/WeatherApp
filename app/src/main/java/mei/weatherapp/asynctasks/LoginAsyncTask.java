package mei.weatherapp.asynctasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import mei.weatherapp.MainActivity;
import mei.weatherapp.contratos.User;
import mei.weatherapp.webservice.WeatherIPCAWebService;


/**
 * Created by joaofaria on 22/11/16.
 */
public class LoginAsyncTask extends AsyncTask<String, Integer, User> {

  Context ctx;
  User aux;
  User user;

  public LoginAsyncTask(Context ctx) {
    this.ctx = ctx;
  }

  @Override
  protected User doInBackground(String... strings) {
    try {
      WeatherIPCAWebService ws = new WeatherIPCAWebService();
      JSONObject res = new JSONObject(ws.doLogin(strings[0]));
      if(res.has("httpCodeResponse")){
        if(res.getInt("httpCodeResponse")==404){
          Toast toast = Toast.makeText(ctx, "Utilizador não encontrado", Toast.LENGTH_SHORT);
          toast.show();
        }
        if(res.getInt("httpCodeResponse")==500){
          Toast toast = Toast.makeText(ctx, "Erro no servidor", Toast.LENGTH_SHORT);
          toast.show();
        }
      }
      aux = new User(res.getString("_id"), res.getString("username"));
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return aux;
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

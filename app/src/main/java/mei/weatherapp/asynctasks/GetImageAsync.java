package mei.weatherapp.asynctasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import mei.weatherapp.contratos.Praia;
import mei.weatherapp.webservice.PanoramioWebservice;


public class GetImageAsync extends AsyncTask<Praia, Void, Bitmap> {

  private PanoramioWebservice wsPan;
  ImageView imgView;


  public GetImageAsync(ImageView imgView) {
    this.wsPan = new PanoramioWebservice();
    this.imgView = imgView;
  }

  @Override
  protected Bitmap doInBackground(Praia... praias) {
    Bitmap image = null;
    Double x, y;

    try {
      x = Double.valueOf(praias[0].getLongitude()).doubleValue();
      y = Double.valueOf(praias[0].getLatitude()).doubleValue();

      JSONObject panJsonObj = new JSONObject(wsPan.doGetImage(Double.toString(x-0.01), Double.toString(y-0.01), Double.toString(x+0.01), Double.toString(y+0.01)));
      JSONArray imageArray = panJsonObj.getJSONArray("photos");
      JSONObject imageObj = imageArray.getJSONObject(0);
      InputStream in = new java.net.URL(imageObj.getString("photo_file_url")).openStream();
      image = BitmapFactory.decodeStream(in);
    } catch (JSONException e) {
      e.printStackTrace();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return image;
  }

  @Override
  protected void onPostExecute(Bitmap bitmap) {
    super.onPostExecute(bitmap);

    imgView.setImageBitmap(bitmap);

  }
}

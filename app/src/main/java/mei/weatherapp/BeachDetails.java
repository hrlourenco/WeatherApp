package mei.weatherapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import mei.weatherapp.adapter.forecastAdapter;
import mei.weatherapp.asynctasks.GetImageAsync;
import mei.weatherapp.contratos.Praia;

public class BeachDetails extends AppCompatActivity {

  private ImageView imgView;
  private ListView lv_forecast;
  private Context ctx;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_beachdetails);

    imgView = (ImageView) findViewById(R.id.imgFoto);
    Praia praia = (Praia) this.getIntent().getSerializableExtra("praia");

    GetImageAsync getImage = new GetImageAsync(imgView);
    getImage.execute(praia.getNome());

    lv_forecast = (ListView) findViewById(R.id.lv_forecast);
    lv_forecast.setAdapter(new forecastAdapter(BeachDetails.this, praia.getForecast()));
  }
}

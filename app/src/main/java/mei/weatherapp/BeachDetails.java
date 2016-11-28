package mei.weatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mei.weatherapp.adapter.forecastAdapter;
import mei.weatherapp.contratos.Praia;


public class BeachDetails extends AppCompatActivity {

  private ImageView imgView;
  private ListView lv_forecast;
  private TextView tv_name;
  private Praia praia;
  private TextView tv_rating;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_beachdetails);

    imgView = (ImageView) findViewById(R.id.imgFoto);
    praia = (Praia) this.getIntent().getSerializableExtra("praia");
    lv_forecast = (ListView) findViewById(R.id.lv_forecast);
    tv_name = (TextView) findViewById(R.id.tv_name);
    tv_rating = (TextView) findViewById(R.id.txt_rating);

    Picasso.with(BeachDetails.this).load(praia.getImagem()).into(imgView);

    tv_name.setText(praia.getNome());
    if(praia.getNumRating()>0){
      tv_rating.setText("Rating: " + Math.round(praia.getRating() / praia.getNumRating()) + " (" + praia.getNumRating() + " votos)");
    } else {
      tv_rating.setText("Ainda sem rating");
    }

    lv_forecast.setAdapter(new forecastAdapter(BeachDetails.this, praia.getForecast()));
  }
}

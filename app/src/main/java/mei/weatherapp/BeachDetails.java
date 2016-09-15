package mei.weatherapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import mei.weatherapp.adapter.forecastAdapter;
import mei.weatherapp.asynctasks.GetImageAsync;
import mei.weatherapp.contratos.Praia;

public class BeachDetails extends AppCompatActivity {

  private ImageView imgView;
  private ListView lv_forecast;
  private TextView tv_name;
  private CheckBox cb_fav;
  private Context ctx;
  private Praia praia;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_beachdetails);

    imgView = (ImageView) findViewById(R.id.imgFoto);
    praia = (Praia) this.getIntent().getSerializableExtra("praia");

    GetImageAsync getImage = new GetImageAsync(imgView);
    getImage.execute(praia.getNome());

    lv_forecast = (ListView) findViewById(R.id.lv_forecast);
    lv_forecast.setAdapter(new forecastAdapter(BeachDetails.this, praia.getForecast()));

    tv_name = (TextView) findViewById(R.id.tv_name);
    tv_name.setText(praia.getNome());

    cb_fav = (CheckBox) findViewById(R.id.cb_fav);
    cb_fav.setChecked(praia.getFavorita()==0 ? Boolean.FALSE : Boolean.TRUE);
  }

  public void onCheckboxClicked(View view) {
    boolean checked = ((CheckBox) view).isChecked();

    switch(view.getId()) {
      case R.id.cb_fav:
        if (checked){
          praia.setFavorita(1);
        }else{
          praia.setFavorita(0);
        }
        break;
    }
  }
}

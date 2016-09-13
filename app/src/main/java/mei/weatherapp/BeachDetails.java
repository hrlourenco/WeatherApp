package mei.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import mei.weatherapp.asynctasks.GetImageAsync;
import mei.weatherapp.contratos.Praia;

public class BeachDetails extends AppCompatActivity {

  private ImageView imgView;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_beachdetails);

    imgView = (ImageView) findViewById(R.id.imgFoto);
    Praia praia = (Praia) this.getIntent().getSerializableExtra("praia");

    GetImageAsync getImage = new GetImageAsync(imgView);
    getImage.execute(praia.getNome());
  }
}

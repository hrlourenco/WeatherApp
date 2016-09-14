package mei.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import mei.weatherapp.asynctasks.AccuweatherCurrentConditions;
import mei.weatherapp.contratos.Condicoes;
import mei.weatherapp.contratos.Praia;

public class MainActivity extends FragmentActivity {

    String TAG = "<MyAutoComplete Google>";
    TextView txtAdress;
    ImageView imgTemp;
    TextView txtTemp;
    TextView txtMsg;
    RelativeLayout load;

    Button btnDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtAdress = (TextView) findViewById(R.id.txtAdress);
        imgTemp = (ImageView) findViewById(R.id.imgTemp);
        txtTemp = (TextView) findViewById(R.id.txtTemp);
        txtMsg = (TextView) findViewById(R.id.txtMsg);
        btnDetails = (Button) findViewById(R.id.btnDetails);
        load = (RelativeLayout) findViewById(R.id.loading);
        load.setVisibility(View.GONE);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener()
        {

            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, "Place: " + place.getName());

                load.setVisibility(View.VISIBLE);

                Praia praia = new Praia();
                praia.setNome(place.getName().toString());
                LatLng ll = place.getLatLng();
                praia.setLatitude(Double.toString(ll.latitude));
                praia.setLongitude(Double.toString(ll.longitude));
                praia.setMorada(place.getAddress().toString());

                AccuweatherCurrentConditions awcc = new AccuweatherCurrentConditions(MainActivity.this, imgTemp, txtAdress, txtTemp, txtMsg, load);
                awcc.execute(praia);
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
                Toast.makeText(MainActivity.this, "Erro ao obter localização", Toast.LENGTH_LONG).show();
            }
        });

        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Praia p = new Praia(0, 1, "0", "0", "Ofir", "morada");
                List<Condicoes> lista = new ArrayList<Condicoes>();
                Condicoes c = new Condicoes();
                c.setId(1);
                c.setTemperature("12");
                c.setWeatherIcon(1);
                c.setData("01-01-2016");
                c.setTemperatureMax("40");
                c.setTemperatureMin("10");
                lista.add(c);
                c = new Condicoes();
                c.setId(2);
                c.setTemperature("13");
                c.setWeatherIcon(2);
                c.setData("02-01-2016");
                c.setTemperatureMax("40");
                c.setTemperatureMin("10");
                lista.add(c);
                c = new Condicoes();
                c.setId(3);
                c.setTemperature("14");
                c.setWeatherIcon(3);
                c.setData("03-01-2016");
                c.setTemperatureMax("40");
                c.setTemperatureMin("10");
                lista.add(c);
                c = new Condicoes();
                c.setId(4);
                c.setTemperature("15");
                c.setWeatherIcon(4);
                c.setData("04-01-2016");
                c.setTemperatureMax("40");
                c.setTemperatureMin("10");
                lista.add(c);
                c = new Condicoes();
                c.setId(5);
                c.setTemperature("16");
                c.setWeatherIcon(5);
                c.setData("05-01-2016");
                c.setTemperatureMax("40");
                c.setTemperatureMin("10");
                lista.add(c);
                p.setForecast(lista);


                Intent viewDetails = new Intent(MainActivity.this, BeachDetails.class);
                viewDetails.putExtra("praia", p);
                if (viewDetails.resolveActivity(getPackageManager()) != null) {
                    startActivity(viewDetails);
                }
            }
        });

    }
}

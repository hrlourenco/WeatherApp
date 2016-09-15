package mei.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

import mei.weatherapp.asynctasks.AccuweatherCurrentConditions;
import mei.weatherapp.asynctasks.AccuweatherForecast;
import mei.weatherapp.contratos.Condicoes;
import mei.weatherapp.contratos.Praia;

public class MainActivity extends FragmentActivity {

    String TAG = "<MyAutoComplete Google>";

    RelativeLayout load;
    TextView txtPercentagem;
    TextView txtAdress;
    ImageView imgTemp;
    TextView txtTemp;
    TextView txtMsg;
    TextView txtHumidade;
    TextView txtVento;
    TextView txtRajadas;
    TextView txtRaiosUV;
    TextView txtNuvens;
    TextView txtPressao;
    TextView txtPrecipitacao;
    private Praia praiaGlobal;


    Button btnDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtAdress = (TextView) findViewById(R.id.txtAdress);
        imgTemp = (ImageView) findViewById(R.id.imgTemp);
        txtTemp = (TextView) findViewById(R.id.txtTemp);
        txtMsg = (TextView) findViewById(R.id.txtMsg);
        txtHumidade = (TextView) findViewById(R.id.txtHumidade);
        txtVento = (TextView) findViewById(R.id.txtVento);
        txtRajadas = (TextView) findViewById(R.id.txtRajadas);
        txtRaiosUV = (TextView) findViewById(R.id.txtRaiosUV);
        txtNuvens = (TextView) findViewById(R.id.txtNuvens);
        txtPressao = (TextView) findViewById(R.id.txtPressao);
        txtPrecipitacao = (TextView) findViewById(R.id.txtPrecipitacao);
        txtPercentagem = (TextView) findViewById(R.id.txtPercentagem);

        btnDetails = (Button) findViewById(R.id.btnDetails);
        load = (RelativeLayout) findViewById(R.id.loading);
        load.setVisibility(View.GONE);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setText("Praia ");


        autocompleteFragment.setBoundsBias(new LatLngBounds(
                new LatLng(37.026228, -8.988789),
                new LatLng(41.685452, -6.624795)));



        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener()
        {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, "Place: " + place.getName());
                findViewById(R.id.ini).setVisibility(View.GONE);

                load.setVisibility(View.VISIBLE);

                Praia praia = new Praia();
                praia.setNome(place.getName().toString());
                LatLng ll = place.getLatLng();
                praia.setLatitude(Double.toString(ll.latitude));
                praia.setLongitude(Double.toString(ll.longitude));
                praia.setMorada(place.getAddress().toString());

                praiaGlobal = praia;
                AccuweatherCurrentConditions awcc = new AccuweatherCurrentConditions(MainActivity.this, load, txtPercentagem, txtAdress, imgTemp, txtTemp, txtMsg, txtHumidade, txtVento, txtRajadas, txtRaiosUV, txtNuvens, txtPressao, txtPrecipitacao);
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

                Intent viewDetails = new Intent(MainActivity.this, BeachDetails.class);
                viewDetails.putExtra("praia", praiaGlobal);
                if (viewDetails.resolveActivity(getPackageManager()) != null) {
                    startActivity(viewDetails);
                }
            }
        });

    }
}

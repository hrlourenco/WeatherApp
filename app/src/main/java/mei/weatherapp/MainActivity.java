package mei.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Double2;
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

import mei.weatherapp.asynctasks.AccuweatherCurrentConditions;
import mei.weatherapp.asynctasks.GetPraiaFromDB;
import mei.weatherapp.asynctasks.GetPraiasAPI;
import mei.weatherapp.contratos.Praia;

public class MainActivity extends FragmentActivity {

    String TAG = "<MyAutoComplete Google>";

    RelativeLayout load;
    TextView txtPercentagem;
    ImageView imgTemp;
    TextView txtTemp;
    TextView txtRate;
    TextView txtMsg;
    TextView txtLatitude;
    TextView txtLongitude;
    TextView txtNome;
    private Praia praiaGlobal;

    Context ctx;


    Button btnDetails;

    Button btnTestes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTestes = (Button) findViewById(R.id.btn_testes);

        ctx = MainActivity.this;

        imgTemp = (ImageView) findViewById(R.id.imgTemp);
        txtTemp = (TextView) findViewById(R.id.txtTemp);
        txtRate = (TextView) findViewById(R.id.txtRate);
        txtMsg = (TextView) findViewById(R.id.txtMsg);
        txtPercentagem = (TextView) findViewById(R.id.txtPercentagem);
        txtLatitude = (TextView) findViewById(R.id.txtLatitude);
        txtLongitude = (TextView) findViewById(R.id.txtLongitude);
        txtNome = (TextView) findViewById(R.id.txtNome);

        btnDetails = (Button) findViewById(R.id.btnDetails);
        load = (RelativeLayout) findViewById(R.id.loading);
        load.setVisibility(View.GONE);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setText("Praia ");

        autocompleteFragment.setBoundsBias(new LatLngBounds(
                new LatLng(37.026228, -8.988789),
                new LatLng(41.685452, -6.624795)));

/*        GetPraiaFromDB getPraias = new GetPraiaFromDB(getApplicationContext(),
          txtMsg, txtTemp, txtRate, imgTemp, (RelativeLayout) findViewById(R.id.ini), txtLatitude, txtLongitude, txtNome);
        getPraias.execute();*/

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
                praia.setLatitude(ll.latitude);
                praia.setLongitude(ll.longitude);

                praiaGlobal = praia;

                GetPraiasAPI ws = new GetPraiasAPI(MainActivity.this, imgTemp, load, txtMsg, txtPercentagem, txtTemp, null);
                ws.execute(praia);

                AccuweatherCurrentConditions awcc = new AccuweatherCurrentConditions(MainActivity.this, load, txtPercentagem,
                  imgTemp, txtTemp, txtMsg);
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
                Intent viewDetails = new Intent(ctx, BeachDetails.class);
                if(praiaGlobal==null){
                    praiaGlobal = new Praia();
                    praiaGlobal.setLatitude(Double.parseDouble(txtLatitude.getText().toString()));
                    praiaGlobal.setLongitude(Double.parseDouble(txtLongitude.getText().toString()));
                    praiaGlobal.setNome((String)txtNome.getText());
                }
                viewDetails.putExtra("praia", praiaGlobal);
                if (viewDetails.resolveActivity(getPackageManager()) != null) {
                    startActivity(viewDetails);
                }
            }
        });


        btnTestes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(ctx, Login.class);
                if (login.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(login, 1);
                }
            }
        });

    }
}

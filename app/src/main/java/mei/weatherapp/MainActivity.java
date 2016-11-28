package mei.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
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
import mei.weatherapp.contratos.GPSData;
import mei.weatherapp.contratos.Praia;
import mei.weatherapp.contratos.User;
import mei.weatherapp.interfaces.AsyncResponse;
import mei.weatherapp.uteis.GPSLocationProvider;

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
    Button btnLogin;
    Button btnActualLocation;
    private User user;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        User user = (User) data.getSerializableExtra("user");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //localização actual
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

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
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnActualLocation = (Button) findViewById(R.id.btnActualLocation);

        praiaGlobal = new Praia();

        //fragmento Google
        final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        //limites de portugal para filtro de localizações principais
        autocompleteFragment.setBoundsBias(new LatLngBounds(
                new LatLng(37.026228, -8.988789),
                new LatLng(41.685452, -6.624795)));

        //alterações de localização
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
                //actualizar praiaGlobal
                praiaGlobal = praia;
                //chamar dados da API
                GetPraiasAPI ws = new GetPraiasAPI(MainActivity.this, imgTemp, load, txtMsg, txtPercentagem, txtTemp, txtRate, null, new AsyncResponse(){
                    @Override
                    public void processFinish(Praia output) {
                        praiaGlobal = output;
                    }
                });
                ws.execute(praia).getStatus();
                //desactivar rating
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
                Toast.makeText(MainActivity.this, "Erro ao obter localização", Toast.LENGTH_LONG).show();
            }
        });


        //Obter dados da actualização actual
        GPSLocationProvider.requestLocation(MainActivity.this, new GPSLocationProvider.LocationCallback() {
            @Override
            public void onNewLocation(GPSData data) {
                if(data.lon != -1) {
                    //actualizar praiaGlobal
                    praiaGlobal.setLatitude((double) data.lat);
                    praiaGlobal.setLongitude((double) data.lon);
                    praiaGlobal.setNome(data.cidade);
                    //actualizar layout
                    txtLatitude.setText(Double.toString(praiaGlobal.getLatitude()));
                    txtLatitude.setText(Double.toString(praiaGlobal.getLongitude()));
                    txtNome.setText(praiaGlobal.getNome());
                    //actualizar fragmento
                    autocompleteFragment.setText(data.cidade);
                    //chamar dados da API
                    GetPraiasAPI ws = new GetPraiasAPI(MainActivity.this, imgTemp, load, txtMsg, txtPercentagem, txtTemp, txtRate, null, new AsyncResponse(){
                        @Override
                        public void processFinish(Praia output) {
                            praiaGlobal = output;
                        }
                    });
                    ws.execute(praiaGlobal);
                } else {
                    Toast.makeText(MainActivity.this, "Impossivel obter localização", Toast.LENGTH_LONG).show();
                    autocompleteFragment.setText("Praia ");
                }
            }
        });

        /*EVENTOS DE BOTÕES*/
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


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(ctx, Login.class);
                if (login.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(login, 1);
                }
            }
        });

        btnActualLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSLocationProvider.requestLocation(MainActivity.this, new GPSLocationProvider.LocationCallback() {
                    @Override
                    public void onNewLocation(GPSData data) {
                        if(data.lon != -1) {
                            //actualizar praiaGlobal
                            praiaGlobal.setLatitude((double) data.lat);
                            praiaGlobal.setLongitude((double) data.lon);
                            praiaGlobal.setNome(data.cidade);
                            //actualizar layout
                            txtLatitude.setText(Double.toString(praiaGlobal.getLatitude()));
                            txtLatitude.setText(Double.toString(praiaGlobal.getLongitude()));
                            txtNome.setText(praiaGlobal.getNome());
                            //actualizar fragmento
                            autocompleteFragment.setText(data.cidade);
                        } else {
                            Toast.makeText(MainActivity.this, "Impossivel obter localização", Toast.LENGTH_LONG).show();
                            autocompleteFragment.setText("Praia ");
                        }
                    }
                });
            }
        });
    }
}

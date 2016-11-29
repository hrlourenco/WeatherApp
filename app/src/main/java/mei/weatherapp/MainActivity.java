package mei.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.Double2;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mei.weatherapp.asynctasks.AccuweatherCurrentConditions;
import mei.weatherapp.asynctasks.GetPraiaFromDB;
import mei.weatherapp.asynctasks.GetPraiasAPI;
import mei.weatherapp.asynctasks.RatePraiaAPI;
import mei.weatherapp.contratos.GPSData;
import mei.weatherapp.contratos.Praia;
import mei.weatherapp.contratos.User;
import mei.weatherapp.interfaces.AsyncResponse;
import mei.weatherapp.uteis.GPSLocationProvider;

import static mei.weatherapp.R.id.btnPhoto;

public class MainActivity extends FragmentActivity {

    static final int REQUEST_LOGIN = 1;
    static final int REQUEST_TAKE_PHOTO = 2;

    String mCurrentPhotoPath;
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
    TextView txtUser;
    TextView txtRatingMessage;
    RatingBar ratingBar;
    LinearLayout llRating;
    private Praia praiaGlobal;
    Context ctx;
    Button btnDetails;
    Button btnLogin;
    Button btnActualLocation;
    Button btnPhoto;
    private User user;

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
        txtUser = (TextView) findViewById(R.id.txtUser);
        txtRatingMessage = (TextView) findViewById(R.id.txtRatingMessage);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        llRating = (LinearLayout) findViewById(R.id.llRating);

        btnDetails = (Button) findViewById(R.id.btnDetails);
        load = (RelativeLayout) findViewById(R.id.loading);
        load.setVisibility(View.GONE);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnActualLocation = (Button) findViewById(R.id.btnActualLocation);
        btnPhoto = (Button) findViewById(R.id.btnPhoto);
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
                //é uma praia diferente da localização actual, não tem acesso a fazer rating
                llRating.setVisibility(View.GONE);

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
                        if(praiaGlobal.getNumRating()<3) {
                            txtRatingMessage.setText("Votos insuficientes");
                            txtRate.setText("");
                            ratingBar.setRating(Float.parseFloat("0.0"));
                        } else {
                            txtRatingMessage.setText("Well done!");
                            ratingBar.setRating(Float.parseFloat(praiaGlobal.getRate().toString()));
                        }
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


        //Obter dados da actualização actual ao iniciar
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
                            if(praiaGlobal.getNumRating()<3) {
                                txtRatingMessage.setText("Votos insuficientes");
                                txtRate.setText("");
                                ratingBar.setRating(Float.parseFloat(praiaGlobal.getRate().toString()));
                            } else {
                                txtRatingMessage.setText("Well done!");
                                ratingBar.setRating(Float.parseFloat(praiaGlobal.getRate().toString()));
                            }
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
                    startActivityForResult(login, REQUEST_LOGIN);
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
                            if(user!=null) {
                                //é uma praia da localização actual, tem acesso a fazer rating
                                llRating.setVisibility(View.VISIBLE);
                            }

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
                            GetPraiasAPI ws = new GetPraiasAPI(MainActivity.this, imgTemp, load, txtMsg, txtPercentagem, txtTemp, txtRate, null, new AsyncResponse(){
                                @Override
                                public void processFinish(Praia output) {
                                    praiaGlobal = output;
                                    if(praiaGlobal.getNumRating()<3) {
                                        txtRatingMessage.setText("Votos insuficientes");
                                        txtRate.setText("");
                                        ratingBar.setRating(Float.parseFloat(praiaGlobal.getRate().toString()));
                                    } else {
                                        txtRatingMessage.setText("Well done!");
                                        ratingBar.setRating(Float.parseFloat(praiaGlobal.getRate().toString()));
                                    }
                                }
                            });
                            ws.execute(praiaGlobal);
                        } else {
                            Toast.makeText(MainActivity.this, "Impossivel obter localização", Toast.LENGTH_LONG).show();
                            autocompleteFragment.setText("Praia ");
                        }
                    }
                });
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                txtRatingMessage.setText("Rate: " + String.valueOf(rating));
                if(user != null) {
                    RatePraiaAPI rp = new RatePraiaAPI(MainActivity.this, praiaGlobal.getPraiaId(), (int)Math.round(rating), txtUser, txtRate, user.getUserId());
                    rp.execute(praiaGlobal);
                }
            }
        });

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(MainActivity.this,
                          "com.example.android.fileprovider",
                          photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    }
                }
            }
        });
    }



    //receber dados de activity for result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN && resultCode == Activity.RESULT_OK) {
            llRating.setVisibility(View.VISIBLE);
            user = (User) data.getSerializableExtra("user");
            txtUser.setText(user.getUsername() + " :: " + user.getCreditos() + " créditos");
        }

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {

        }
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        if(user!=null) {
            llRating.setVisibility(View.VISIBLE);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
          imageFileName,  /* prefix */
          ".jpg",         /* suffix */
          storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}

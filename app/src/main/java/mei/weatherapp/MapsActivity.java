package mei.weatherapp;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import mei.weatherapp.contratos.Praia;
import mei.weatherapp.contratos.Proximas;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Praia praia = (Praia) getIntent().getSerializableExtra("praia");
        // Add a marker in Sydney and move the camera
        LatLng praiaLocal = new LatLng(praia.getLatitude(), praia.getLongitude());
        MarkerOptions mo = new MarkerOptions().position(praiaLocal).title("Eu estou aqui");
        mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
        mMap.addMarker(mo);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(praiaLocal));
        ArrayList<Proximas> proximas = praia.getProximas();

        if(proximas!=null) {
            for (int i = 0; i < proximas.size(); i++) {
                Proximas aux = proximas.get(i);
                praiaLocal = new LatLng(aux.getLatitude(), aux.getLongitude());
                mMap.addMarker(new MarkerOptions().position(praiaLocal).title(aux.getNome()));
            }
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(praia.getLatitude(), praia.getLongitude()), 14.0f));
    }
}

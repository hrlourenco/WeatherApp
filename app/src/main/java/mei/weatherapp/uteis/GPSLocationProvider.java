package mei.weatherapp.uteis;


import android.Manifest;
import android.app.ApplicationErrorReport;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ListFragment;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import mei.weatherapp.contratos.GPSData;

public class GPSLocationProvider {
    //este interface será utilizado na chamada à função de busca de localização
    public static interface LocationCallback {
        public void onNewLocation(GPSData location);
    }

    public static void requestLocation(final Context ctx, final LocationCallback callback) {
        //chamada ao serviço de localização
        final LocationManager locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        //é possível verificar a actualização actual através da internet.
        //para este trabalho vamos procurar através de GPS mas fica implementada a função de internet
        boolean isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Criteria criteria = new Criteria();
        boolean proceed = false;

        if (isGPS) {
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            proceed = true;
        } else if( isNetwork) {
            proceed = false;
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        }

        //se está activado um dos providers, GPS ou network
        if(proceed) {
            //verificar permissões
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            locationManager.requestSingleUpdate(criteria, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //FUNÇÃO PARA DEVOLVER A CIDADE ACTUAL ATRAVÉS DO GEOCODER
                    Geocoder geoCoder = new Geocoder(ctx, Locale.getDefault());
                    StringBuilder builder = new StringBuilder();
                    String addrFinal = "";
                    try {
                        List<Address> addr = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        int numLinhas = addr.get(0).getMaxAddressLineIndex();
                        for (int i = 0; i < numLinhas; i++) {
                            String aux = addr.get(0).getAddressLine(i); //do resultado anterior pegar linha a linha
                            builder.append(aux); //adicionar a palavra e um espaço
                            //só adiciona o espaço se nao é a última palavra
                            if (i < numLinhas - 1) {
                                builder.append(", ");
                            }
                        }
                    } catch (IOException e) {
                        builder.append("");
                    } catch (NullPointerException e) {
                        builder.append("");
                    } catch (Exception e) {
                        builder.append("");
                    }
                    addrFinal = builder.toString();
                    //'implementação' do nosso interface
                    callback.onNewLocation(new GPSData(location.getLatitude(), location.getLongitude(), addrFinal));
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {
                    Toast.makeText(ctx, "Provider activado: " + provider, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Toast.makeText(ctx, "Provider desactivado: " + provider, Toast.LENGTH_LONG).show();
                }
            }, null);
        }
    }
}

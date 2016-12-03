package vera.moon.com.d2_hiker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity implements LocationListener {

    LocationManager manager;
    String provider;

    TextView lat,lng,acc,spe,ber,alt,adress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = manager.getBestProvider(new Criteria(), false);

        lat = (TextView)findViewById(R.id.textViewLat);
        lng = (TextView)findViewById(R.id.textViewLong);
        acc = (TextView)findViewById(R.id.textViewAcu);
        spe = (TextView)findViewById(R.id.textViewSpeed);
        ber = (TextView)findViewById(R.id.textViewBear);
        alt = (TextView)findViewById(R.id.textViewAlt);

        adress = (TextView)findViewById(R.id.textViewAdress);



    }

    @Override
    public void onLocationChanged(Location location) {

        Double lati = location.getLatitude();
        Double lngo = location.getLongitude();

        Double alti = location.getAltitude();
        float berr = location.getBearing();
        float speedd = location.getSpeed();
        float acur = location.getAccuracy();

        //Addresss
        Geocoder geo = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> list = geo.getFromLocation(lati,lngo,1);

            if(list != null && list.size()>0){

                String resul="";

                //getMaxAddressLineIndex() to get the data
                for(int i=0;i<list.get(0).getMaxAddressLineIndex();i++){

                    resul += list.get(0).getAddressLine(i)+"\n";
                }
                adress.setText(resul+"");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        lat.setText(lati+"");
        lng.setText(lngo+"");
        alt.setText(alti+" m");
        spe.setText(speedd+"");
        ber.setText(berr+" m/s");
        acc.setText(acur+" m");

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.requestLocationUpdates(provider, 400, 1, this);
    }
}

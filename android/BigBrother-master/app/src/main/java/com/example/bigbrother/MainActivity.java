package com.example.bigbrother;


import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;


public class MainActivity extends Activity {

    private TextView currLat, currLong, lastLat, lastLong, textAlt;
    private Button locateBTN;

    LocationManager locationManager;
    LocationListener locationListener;
    Location location;

    WebView myWebView;
    StringBuilder stringBuilder = new StringBuilder("http://maps.google.com/maps");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currLat = (TextView) findViewById(R.id.lat_output);
        currLong = (TextView) findViewById(R.id.long_output);
        lastLat = (TextView) findViewById(R.id.lastLatDisplay);
        lastLong = (TextView) findViewById(R.id.lastLongDisplay);

        locateBTN = (Button) findViewById(R.id.locate_button);

        locationManager =
                (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        locationListener =
                new myLocationlistener();

        locationManager
                .requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        0, 0, locationListener);

        locateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationManager =
                        (LocationManager)getSystemService(Context.LOCATION_SERVICE);

                location =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                lastLat.setText(Double.toString(location.getLatitude()));
                lastLong.setText(Double.toString(location.getLongitude()));
            }
        });
    }

    private Double calculateDistance(Stack<Location> mDistances){
        //STUB
        return null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        locationListener =
                new myLocationlistener();

        locationManager
                .requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        0, 0, locationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();

        locationManager.removeUpdates(locationListener);
        locationManager = null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class myLocationlistener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            if ( location != null ){
                double pLong = location.getLongitude();
                double pLat = location.getLatitude();

                currLat.setText(Double.toString(pLat));
                currLong.setText(Double.toString(pLong));

            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}

package com.ecip.msdp.ecitest1;

import android.Manifest;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;



import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // LogCat tag
    private static final String TAG = MapsActivity.class.getSimpleName();

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    Geocoder geocoder;

    List<Address> addresses;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // First we need to check availability of play services
        geocoder = new Geocoder(this, Locale.ENGLISH);


        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
            //displayLocation();
        }
    }

    private void displayLocation() {

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
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

            if (mLastLocation != null) {
                double latitude = mLastLocation.getLatitude();
                double longitude = mLastLocation.getLongitude();
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //lblLocation.setText(latitude + ", " + longitude);
                    Log.d(TAG,"latitude"+latitude);
                    Toast.makeText(this,"latitude"+latitude+"longitude"+longitude,Toast.LENGTH_LONG).show();
                    Log.d(TAG,"longitude"+longitude);
            } else {

                //lblLocation
                  //      .setText("(Couldn't get the location. Make sure location is enabled on the device)");
                Toast.makeText(this,"Couldn't get the location. Make sure location is enabled on the device",Toast.LENGTH_LONG).show();
            }
        Toast.makeText(this,""+addresses.get(0).getPostalCode(),Toast.LENGTH_LONG).show();
        Log.d(TAG,addresses.get(0).getAddressLine(0));
        Log.d(TAG,addresses.get(0).getLocality());
        Log.d(TAG,addresses.get(0).getPostalCode().toString());
        }

        /**
         * Creating google api client object
         * */
        protected synchronized void buildGoogleApiClient() {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();
        }

        /**
         * Method to verify google play services on the device
         * */
        private boolean checkPlayServices() {
            int resultCode = GooglePlayServicesUtil
                    .isGooglePlayServicesAvailable(this);
            if (resultCode != ConnectionResult.SUCCESS) {
                if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                    GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                            PLAY_SERVICES_RESOLUTION_REQUEST).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "This device is not supported.", Toast.LENGTH_LONG)
                            .show();
                    finish();
                }
                return false;
            }
            return true;
        }

        @Override
        protected void onStart() {
            super.onStart();
            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            }
        }

        @Override
        protected void onResume() {
            super.onResume();

            checkPlayServices();
        }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }
}

package com.ecip.msdp.ecitest1;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class Splash extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = Splash.class.getSimpleName();
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters
    SharedPreferences pf;
    Geocoder geocoder;
    List<Address> addresses;
    private Location mLastLocation;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent intent = getIntent();
        boolean approval = intent.getBooleanExtra("approval",false);
        Toast.makeText(Splash.this, "splash" + approval, Toast.LENGTH_SHORT).show();

        geocoder = new Geocoder(this, Locale.ENGLISH);


        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
            //displayLocation();
        }
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
       new GetLocation().execute();
       /* if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {

            Person currentPerson = Plus.PeopleApi
                    .getCurrentPerson(mGoogleApiClient);
            String personName = currentPerson.getDisplayName();

            try {
                int genderInt = currentPerson.getGender();
                String   gender     = String.valueOf(genderInt);
                String birthday = currentPerson.getBirthday();

               Log.d(TAG,"gender"+gender+"  birthday"+birthday);

            }


            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
            Log.d(TAG,""+email);
        }*/
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

    private class GetLocation extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... params) {

            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //return;
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

                SharedPreferences.Editor editor = getSharedPreferences("location", MODE_PRIVATE).edit();
                editor.putLong("latitude", Double.doubleToLongBits(latitude));
                editor.putLong("longitude", Double.doubleToLongBits(longitude));
                editor.commit();
                //lblLocation.setText(latitude + ", " + longitude);
                pf = getSharedPreferences("location", MODE_PRIVATE);
                Log.d(TAG, "latitude" + Double.longBitsToDouble(pf.getLong("latitude", 0)));
                //Toast.makeText(getApplicationContext(), "latitude" + latitude + "longitude" + longitude, Toast.LENGTH_LONG).show();
                Log.d(TAG, "longitude" + Double.longBitsToDouble(pf.getLong("longitude", 0)));
            } else {

                //lblLocation
                //      .setText("(Couldn't get the location. Make sure location is enabled on the device)");
                Log.d("TAG", "Couldn't get the location. Make sure location is enabled on the device");
            }




            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, addresses.get(0).getAddressLine(0));
            Log.d(TAG, addresses.get(0).getAddressLine(1));
            Log.d(TAG, addresses.get(0).getAddressLine(2));

            Log.d(TAG, addresses.get(0).getLocality());

//            Log.d(TAG, addresses.get(0).getPostalCode().toString());
            Log.d(TAG, addresses.get(0).getAdminArea());

            Log.d(TAG, addresses.get(0).getFeatureName());

//            Log.d(TAG, addresses.get(0).getPremises());

//            Log.d(TAG, addresses.get(0).getSubAdminArea());

            //          Log.d(TAG, addresses.get(0).getSubLocality());

            SharedPreferences.Editor editor = getSharedPreferences("address",MODE_PRIVATE).edit();
            editor.putString("addressline",addresses.get(0).getAddressLine(0));
            editor.putString("state",addresses.get(0).getAdminArea());
            editor.putString("locality",addresses.get(0).getLocality());
            editor.putString("postalcode", addresses.get(0).getPremises());
            editor.commit();
            startActivity(new Intent(getApplicationContext(),Installation.class));
        }
    }
}

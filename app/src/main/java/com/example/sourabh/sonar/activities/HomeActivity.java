package com.example.sourabh.sonar.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sourabh.sonar.R;
import com.example.sourabh.sonar.SettingsPreferences;
import com.example.sourabh.sonar.activities.base.AbsPermissionActivity;
import com.example.sourabh.sonar.mvcviews.HomeViewImp;
import com.example.sourabh.sonar.mvcviews.base.HomeView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class HomeActivity extends AbsPermissionActivity implements HomeView.OnLocationSendListener {

    private HomeView mHomeView;
    private FusedLocationProviderClient mFusedLocaitonProvideClient;
    private Location lastKnownLocation;
    private float minDistanceBetweenUpdates; //in meteres
    private long minTimeBetweenUpdates; //in minutes
    private boolean isDistanceMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updatePreferences();

        mHomeView = new HomeViewImp(getLayoutInflater(), this);
        setContentView(mHomeView.getRootView());

        requestLocationPermission();
        requestMessagePermission();
        mHomeView.setOnLocationSendListener(this);
        mFusedLocaitonProvideClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if(locationResult != null){
                    for(Location location : locationResult.getLocations()){
                        if(lastKnownLocation == null || !isDistanceMode || lastKnownLocation.distanceTo(location) > minDistanceBetweenUpdates) {
                            String msg = "Last known location: (" + location.getLatitude() + "," +
                                    location.getLongitude() + ")";
                            Log.d("debug",msg);
                            sendLocationMessage(msg);
                            lastKnownLocation = location;
                        }
                    }
                }
            }
        };
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(minTimeBetweenUpdates*60);
    }

    void updatePreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        minDistanceBetweenUpdates = Float.parseFloat(preferences.getString(MainActivity.MIN_DISTANCE_BEFORE_RESEND, "20"));
        minTimeBetweenUpdates = Long.parseLong(preferences.getString(MainActivity.MIN_TIME_BEFORE_RESEND, "1"));
        isDistanceMode = preferences.getBoolean(MainActivity.DISTANCE_MODE, false);
        Log.d("debug","minDistance: " + minDistanceBetweenUpdates);
        Log.d("debug","minTime: " + minTimeBetweenUpdates);
        Log.d("debug","isDistanceMode: " + isDistanceMode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == ACCESS_LOCATION_PERMISSION){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mHomeView.setOnLocationSendListener(this);
            }
        }else if(requestCode == SEND_SMS_PERMISSION){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mHomeView.setOnLocationSendListener(this);
            }
        }
    }

    @Override
    public void OnOneTimeSend() {
        if(requestLocationPermission()) {
            try {
                mFusedLocaitonProvideClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                String msg = "Last known location: (" + location.getLatitude() + "," +
                                        location.getLongitude() + ")";
                                Log.d("debug",msg);
                                if(requestMessagePermission()){
                                    sendLocationMessage(msg);
                                }
                            }
                        });

            }catch (SecurityException exc){
                exc.printStackTrace();
            }
        }
    }

    void sendLocationMessage(String msg){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String customMessage = preferences.getString(MainActivity.CUSTOM_MESSAGE, null);
        String finalMessage;
        if(customMessage != null){
            finalMessage = customMessage + " " + msg;
        }else{
            finalMessage = msg;
        }
        String distressNumber = preferences.getString(MainActivity.DISTRESS_NUMBER, null);
        if(distressNumber != null) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(distressNumber, null, finalMessage, null, null );
            Toast.makeText(getApplicationContext(), "Location sent (probably)", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Set distress number in the settings", Toast.LENGTH_LONG).show();
        }
    }

    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;

    @Override
    public void OnSonar() {
        try {
            mFusedLocaitonProvideClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        }catch (SecurityException exc){
            exc.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFusedLocaitonProvideClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings_menu:
                Intent settingsIntent = new Intent(this, SettingsPreferences.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePreferences();
        mLocationRequest.setInterval(minTimeBetweenUpdates*60);
    }
}

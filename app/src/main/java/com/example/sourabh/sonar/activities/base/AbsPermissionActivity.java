package com.example.sourabh.sonar.activities.base;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public abstract class AbsPermissionActivity extends AppCompatActivity {

    public static int ACCESS_LOCATION_PERMISSION = 123;
    public static int SEND_SMS_PERMISSION = 456;

    public Boolean requestLocationPermission(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_LOCATION_PERMISSION);
            return false;
        }
        return true;
    }

    public Boolean requestMessagePermission(){
        Log.d("debug","HERE");
        if(ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_PHONE_STATE) !=
                        PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE},
                    SEND_SMS_PERMISSION);
            return false;
        }
        return true;
    }
}

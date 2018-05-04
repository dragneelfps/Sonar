package com.example.sourabh.sonar.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    static public String MY_SHAREDPREFERENCES = "custom_prefs";
    static String FIRST_TIME_ACCESS = "first_time";
    static public String DISTRESS_NUMBER = "distress_number";
    static String MIN_DISTANCE_BEFORE_RESEND = "min_dist";
    static String MIN_TIME_BEFORE_RESEND = "min_time";
    static String CUSTOM_MESSAGE = "custom_message";
    static String DISTANCE_MODE = "distance_mode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getBoolean(FIRST_TIME_ACCESS, true)){
            Log.d("debug","First Time access");
            Intent intent = new Intent(getApplicationContext(), SetupActivity.class);
            startActivity(intent);
            finish();
        }else{
            Log.d("debug","Distress number is " + sharedPreferences.getString(DISTRESS_NUMBER, "INVALID"));
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

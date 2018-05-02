package com.example.sourabh.sonar.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    static String MY_SHAREDPREFERENCES = "custom_prefs";
    static String FIRST_TIME_ACCESS = "first_time";
    static String DISTRESS_NUMBER = "distress_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(MY_SHAREDPREFERENCES, Context.MODE_PRIVATE);
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

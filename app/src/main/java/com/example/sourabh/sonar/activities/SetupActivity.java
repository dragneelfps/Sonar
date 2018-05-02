package com.example.sourabh.sonar.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.sourabh.sonar.mvcviews.base.FirstTimeView;
import com.example.sourabh.sonar.mvcviews.FirstTimeViewImp;

public class SetupActivity extends AppCompatActivity implements FirstTimeView.OnContactPickListener {

    private FirstTimeView mFirstTimeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirstTimeView = new FirstTimeViewImp(getLayoutInflater(), this);
        setContentView(mFirstTimeView.getRootView());
        mFirstTimeView.setOnContactPickListener(this);
    }

    @Override
    public void onContactPick(String contactNumber) {
        if(contactNumber.length() == 10){
            SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.MY_SHAREDPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Log.d("debug","Saving number: " + contactNumber);
            editor.putString(MainActivity.DISTRESS_NUMBER, contactNumber);
            editor.putBoolean(MainActivity.FIRST_TIME_ACCESS, false);
            editor.apply();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }else{
            //Do Nothing
        }
    }
}

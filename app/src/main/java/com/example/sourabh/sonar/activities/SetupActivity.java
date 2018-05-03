package com.example.sourabh.sonar.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.sourabh.sonar.mvcviews.FirstTimeViewImp;
import com.example.sourabh.sonar.mvcviews.base.FirstTimeView;

public class SetupActivity extends AppCompatActivity implements FirstTimeView.OnContactPickListener {

    private FirstTimeView mFirstTimeView;
    static private int REQ_CONTACT_DIRECTORY = 12;

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
            Toast.makeText(getApplicationContext(), "Invalid phone number entered", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onContactPick() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, REQ_CONTACT_DIRECTORY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQ_CONTACT_DIRECTORY){
                contactPicked(data);
            }
        }
    }

    void contactPicked(Intent data){
        String phoneNo;
        Uri uri = data.getData();
        try(Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
            cursor.moveToFirst();
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            phoneNo = cursor.getString(phoneIndex);
            mFirstTimeView.updateDistressNumber(phoneNo);
            Log.d("debug","Contact Picked: " + phoneNo);
        }catch (NullPointerException exc){
            exc.printStackTrace();
        }
    }
}

package com.example.sourabh.sonar.mvcviews;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sourabh.sonar.R;
import com.example.sourabh.sonar.activities.HomeActivity;
import com.example.sourabh.sonar.activities.MainActivity;
import com.example.sourabh.sonar.mvcviews.base.HomeView;

import java.lang.ref.WeakReference;

public class HomeViewImp implements HomeView {

    private View mRootView;
    private WeakReference<HomeActivity> refActivity;
    private OnLocationSendListener mLocationSendListener;
    private Button mOneTimeSendButton;
    private Button mSonarButton;


    public HomeViewImp(LayoutInflater inflater, final HomeActivity activity){
        mRootView = inflater.inflate(R.layout.home_layout, null, false);
        mOneTimeSendButton = mRootView.findViewById(R.id.send_location_btn);
        mSonarButton = mRootView.findViewById(R.id.sonar_location_btn);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        String distressNumber = preferences.getString(MainActivity.DISTRESS_NUMBER, null);
        TextView distressNumberTextView = mRootView.findViewById(R.id.distress_number);
        if(distressNumber == null){
            distressNumberTextView.setText("INVALID");
            View.OnClickListener featureNotAvailableListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity.getApplicationContext(), "Feature not available", Toast.LENGTH_SHORT).show();
                }
            };
            mOneTimeSendButton.setOnClickListener(featureNotAvailableListener);
            mSonarButton.setOnClickListener(featureNotAvailableListener);
        }else {
            ((TextView) mRootView.findViewById(R.id.distress_number)).setText(distressNumber);

            mOneTimeSendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("debug", "One time clicked");
                    if (mLocationSendListener != null) {
                        mLocationSendListener.OnOneTimeSend();
                    }
                }
            });
            mSonarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("debug", "Sonar clicked");
                    if (mLocationSendListener != null) {
                        mLocationSendListener.OnSonar();
                    }
                }
            });
        }
    }

    @Override
    public void setOnLocationSendListener(OnLocationSendListener listener) {
        mLocationSendListener = listener;
    }

    @Override
    public View getRootView() {
        return mRootView;
    }
}

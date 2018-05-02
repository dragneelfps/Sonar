package com.example.sourabh.sonar.mvcviews;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.sourabh.sonar.activities.HomeActivity;
import com.example.sourabh.sonar.R;
import com.example.sourabh.sonar.mvcviews.base.HomeView;

import java.lang.ref.WeakReference;

public class HomeViewImp implements HomeView {

    private View mRootView;
    private WeakReference<HomeActivity> refActivity;
    private OnLocationSendListener mLocationSendListener;
    private Button mOneTimeSendButton;
    private Button mSonarButton;


    public HomeViewImp(LayoutInflater inflater, HomeActivity activity){
        mRootView = inflater.inflate(R.layout.home_layout, null, false);
        mOneTimeSendButton = mRootView.findViewById(R.id.send_location_btn);
        mSonarButton = mRootView.findViewById(R.id.sonar_location_btn);
        mOneTimeSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("debug","One time clicked");
                if(mLocationSendListener != null){
                    mLocationSendListener.OnOneTimeSend();
                }
            }
        });
        mSonarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("debug","Sonar clicked");
                if(mLocationSendListener != null){
                    mLocationSendListener.OnSonar();
                }
            }
        });
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

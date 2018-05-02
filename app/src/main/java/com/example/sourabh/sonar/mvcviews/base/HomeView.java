package com.example.sourabh.sonar.mvcviews.base;

public interface HomeView extends RootView {
    interface OnLocationSendListener{
        void OnOneTimeSend();
        void OnSonar();
    }
    void setOnLocationSendListener(OnLocationSendListener listener);
}

package com.example.sourabh.sonar.mvcviews.base;

public interface FirstTimeView extends RootView {
    interface OnContactPickListener{
        void onContactPick(String contactNumber);
        void onContactPick();
    }
    void setOnContactPickListener(OnContactPickListener listener);
    void updateDistressNumber(String phoneNo);
}

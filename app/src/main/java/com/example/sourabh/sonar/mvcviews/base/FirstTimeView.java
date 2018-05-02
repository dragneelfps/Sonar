package com.example.sourabh.sonar.mvcviews.base;

public interface FirstTimeView extends RootView {
    interface OnContactPickListener{
        void onContactPick(String contactNumber);
    }
    void setOnContactPickListener(OnContactPickListener listener);
}

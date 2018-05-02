package com.example.sourabh.sonar.mvcviews;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sourabh.sonar.R;
import com.example.sourabh.sonar.activities.SetupActivity;
import com.example.sourabh.sonar.mvcviews.base.FirstTimeView;

import java.lang.ref.WeakReference;

public class FirstTimeViewImp implements FirstTimeView, View.OnClickListener {

    private View mRootView;
    private WeakReference<SetupActivity> mRefActivity;
    private OnContactPickListener mOnContactPickListener;
    private EditText mNumberInput;
    private Button mSaveButton;

    public FirstTimeViewImp(LayoutInflater inflater, SetupActivity activity){
        mRootView = inflater.inflate(R.layout.first_time_layout, null, false);
        mNumberInput = mRootView.findViewById(R.id.number_input);
        mSaveButton = mRootView.findViewById(R.id.save_number_btn);
        mSaveButton.setOnClickListener(this);
        mRefActivity = new WeakReference<>(activity);
    }

    @Override
    public void onClick(View v) {
        Log.d("debug","Entered number is : " + mNumberInput.getText());
        if(mOnContactPickListener != null){
            mOnContactPickListener.onContactPick(mNumberInput.getText() + "");
        }
    }

    @Override
    public void setOnContactPickListener(OnContactPickListener listener) {
        mOnContactPickListener = listener;
    }

    @Override
    public View getRootView() {
        return mRootView;
    }
}

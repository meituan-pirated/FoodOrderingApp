package com.example.foododeringapp.rider;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.foododeringapp.R;
import com.example.foododeringapp.control.BaseActivity;

public class Activity_Rider_Main extends BaseActivity {
    public static int networkState = -1;//0：网络不可用  1：网络可用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__rider__main);
    }
}
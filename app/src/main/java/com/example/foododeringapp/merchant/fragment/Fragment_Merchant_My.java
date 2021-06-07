package com.example.foododeringapp.merchant.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import com.example.foododeringapp.R;

public class Fragment_Merchant_My extends Fragment {
    private Toolbar toolbar;

    private String userName, userImg;

    private LinearLayout my_store, my_money,my_commet;

    private Button logout;

    private ViewPager2 fragment_merchant_my_pager;



    //用于刷新
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private ProgressDialog pg;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__merchant__my, container, false);
        initView(view);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initView(View view) {

        my_store = view.findViewById(R.id.my_store);
        my_money = view.findViewById(R.id.my_money);
        my_commet = view.findViewById(R.id.my_commet);

        logout = view.findViewById(R.id.logout);

    }


}
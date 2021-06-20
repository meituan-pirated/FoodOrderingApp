package com.example.foododeringapp.user.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.User;
import com.example.foododeringapp.user.Activity_Address_Manage;
import com.example.foododeringapp.user.Activity_User_Balance;
import com.example.foododeringapp.user.Activity_User_Main;
import com.example.foododeringapp.user.Activity_User_Payment;
import com.example.foododeringapp.user.adapter.Adapter_User_allMerchants;
import com.example.foododeringapp.user.service.UserRequestUtility;

public class Fragment_User_My extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
//<!--    my_id my_name my_sex my_phone my_address user_my_logout-->
    private LinearLayout my_address_manage, my_userInfo_manage;
    private TextView my_id, my_name, my_sex, my_phone, my_address, user_my_logout;
    User userInfo;
    int userID = 201821101;

    //用于刷新
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private ProgressDialog pg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment__user__my, container, false);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化控件
        initView();
        showList();
    }

    private void showList() {
        pg.setMessage("数据加载中...");
        pg.show();
        if (Activity_User_Main.networkState == 0) {
            Toast.makeText(getActivity(), "网络连接失败，请检查网络连接设置！", Toast.LENGTH_SHORT).show();
            pg.dismiss();
            return;
        }

        new Thread() {
            public void run() {
                try {
                    userInfo = UserRequestUtility.getUserInfoByID(userID);
                    handler.post(runnableOrderList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Runnable runnableOrderList = new Runnable() {
        @Override
        public void run() {
            pg.dismiss();

            if (userInfo != null) {
                my_id.setText(userID);
                my_name.setText(userInfo.getName());
                my_phone.setText(userInfo.getPhoneNumber());
                my_sex.setText(userInfo.getSex());
            }
        }
    };

    private void initView() {
        findViewByID();
        pg = new ProgressDialog(getActivity());
        user_my_logout.setOnClickListener(this);
        my_address_manage.setOnClickListener(this);
        my_userInfo_manage.setOnClickListener(this);
    }

//    my_id, my_name, my_sex, my_phone, my_address, user_my_logout;
    private void findViewByID() {
        my_id = getActivity().findViewById(R.id.my_id);
        my_name = getActivity().findViewById(R.id.my_name);
        my_sex = getActivity().findViewById(R.id.my_sex);
        my_phone = getActivity().findViewById(R.id.my_phone);
        user_my_logout = getActivity().findViewById(R.id.user_my_logout);
        my_address_manage = getActivity().findViewById(R.id.my_address_manage);
        my_userInfo_manage = getActivity().findViewById(R.id.my_userInfo_manage);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_my_logout:
                //跳转到登录页面
                break;
            case R.id.my_address_manage:
                Intent intent = new Intent(getActivity(), Activity_Address_Manage.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                break;
            case R.id.my_userInfo_manage:
                //跳转到用户信息管理页面
                break;
            default:
                break;
        }
    }
}
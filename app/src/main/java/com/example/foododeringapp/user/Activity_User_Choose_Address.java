package com.example.foododeringapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Address;
import com.example.foododeringapp.user.adapter.Adapter_User_Choosing_Address;
import com.example.foododeringapp.user.service.UserRequestUtility;
import com.example.foododeringapp.util.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class Activity_User_Choose_Address extends AppCompatActivity implements View.OnClickListener {
//    choosing_address ly_choosing_address
    private RecyclerView choosing_address;
    private int userID = 201821101;
    private List<Address> addressList = new ArrayList<>();
    public static String remark;//标记是从哪个页面跳转过来的
    private Handler handler = new Handler();
    private ProgressDialog pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__user__choose__address);
        pg = new ProgressDialog(Activity_User_Choose_Address.this);
        getIntentData();
        initView();
    }

//    public static void actionStart(Context context, String remark) {
//        Intent intent = new Intent(context, Activity_User_Choose_Address.class);
//        intent.putExtra("remark", remark);
//        context.startActivity(intent);
//    }

    /**
     * 获取remark，标记是哪个页面跳转
     */
    private void getIntentData() {
        Intent intent = getIntent();
        remark = intent.getStringExtra("remark");
        userID = intent.getIntExtra("userID", -1);
    }

    private void findViewById() {
        choosing_address = findViewById(R.id.choosing_address);
    }

    private void initView() {
        findViewById();
        getAddressData(userID);
    }

    /**
     * 通过用户id获取地址信息
     *
     * @param userId
     */
    private void getAddressData(final int userId) {
        pg.setMessage("获取地址中...");
        pg.show();
        if (!Util.checkNetwork(this)) {
            pg.dismiss();
            return;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    addressList = UserRequestUtility.getAddressListById(userID);
                    handler.post(runnableAddress);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Runnable runnableAddress = new Runnable() {
        @Override
        public void run() {
            pg.dismiss();
            Log.i("addressListNumber:", String.valueOf(addressList.size()));
            LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_User_Choose_Address.this);
            choosing_address.setLayoutManager(layoutManager);
            Adapter_User_Choosing_Address adapter_address = new Adapter_User_Choosing_Address(addressList);
            choosing_address.setAdapter(adapter_address);

            //为RecyclerView添加item点击事件
            adapter_address.setOnItemClickListener((view, position) -> {
                if (remark.equals("结算页面")) {
                    //向结算页面返回结果
                    Address address = addressList.get(position);
                    Intent intent = new Intent();
                    intent.putExtra("name", address.getReceiveName());
                    intent.putExtra("phoneNumber", address.getReceivePhone());
                    Log.i("phonenum", String.valueOf(address.getReceivePhone()));
                    intent.putExtra("address", address.getAddressName());
                    intent.putExtra("sex", address.getSex());
                    intent.putExtra("addressID", address.getAddressId());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    };

    @Override
    public void onClick(View v) {
    }
//
//    public static void actionStart(Context context, String remark) {
//        Intent intent = new Intent(context, Activity_User_Choose_Address.class);
//        intent.putExtra("remark", remark);
//        context.startActivity(intent);
//    }
}
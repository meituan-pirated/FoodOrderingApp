package com.example.foododeringapp.user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Address;
import com.example.foododeringapp.bean.Foods;
import com.example.foododeringapp.user.adapter.Adapter_Address_Manage;
import com.example.foododeringapp.user.adapter.Adapter_User_Balance;
import com.example.foododeringapp.user.adapter.Adapter_User_allFoods;
import com.example.foododeringapp.user.service.UserRequestUtility;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Activity_Address_Manage extends AppCompatActivity implements View.OnClickListener{
//    addressManage, addAddressTv
    private RecyclerView addressManageRV;
    private int userID;
    private TextView addAddressTv;
    private List<Address> addressList;
    private Adapter_Address_Manage adapter;
    private ProgressDialog pg;

    //用于刷新
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__address__manage);
        findViewById();
        getIntentData();
        initView();
        showList();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        userID = intent.getIntExtra("userID", -1);
    }

    private void showList() {
        pg.setMessage("数据加载中...");
        pg.show();
        if (Activity_User_Main.networkState == 0) {
            Toast.makeText(getApplicationContext(), "网络连接失败，请检查网络连接设置！", Toast.LENGTH_SHORT).show();
            pg.dismiss();
            return;
        }
        new Thread() {
            public void run() {
                try {
                    addressList = UserRequestUtility.getAllAddress(userID);
                    if(addressList.size() != 0){
                        handler.post(runnableFoodList);
                    }else{
                        Toast.makeText(getApplicationContext(), "没有您的送餐地址信息", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Runnable runnableFoodList = new Runnable() {
        @Override
        public void run() {
            if (pg != null){
                pg.dismiss();

            }
            //这里导入adapter
            if (addressList != null && addressList.size() > 0) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_Address_Manage.this);
                adapter = new Adapter_Address_Manage(Activity_Address_Manage.this, addressList, userID);
                addressManageRV.setLayoutManager(layoutManager);
                addressManageRV.setAdapter(adapter);
            }
        }
    };

    private void findViewById() {
        addressManageRV = findViewById(R.id.addressManageRV);
        addAddressTv = findViewById(R.id.addAddressTv);
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolbarText = findViewById(R.id.toolbar_text);
        toolbarText.setText("地址管理");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        pg = new ProgressDialog(this);

        addAddressTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addAddressTv:
                Intent intent = new Intent(this, Activity_Add_Address.class);
                intent.putExtra("userID",userID);
                startActivityForResult(intent, 104);
                break;
            default:
                break;
        }
    }
}
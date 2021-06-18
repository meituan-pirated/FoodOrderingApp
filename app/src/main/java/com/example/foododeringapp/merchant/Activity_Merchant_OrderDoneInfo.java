package com.example.foododeringapp.merchant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.OrderDoneInfoForM;
import com.example.foododeringapp.bean.Products;
import com.example.foododeringapp.merchant.adapter.Adapter_OrderDetails;
import com.example.foododeringapp.merchant.adapter.Adapter_OrdersWait;
import com.example.foododeringapp.merchant.fragment.Fragment_Merchant_Wait;
import com.example.foododeringapp.merchant.service.MerchantRequestUtility;
import com.example.foododeringapp.util.Util;
import com.example.foododeringapp.widget.EmptyRecyclerView;

public class Activity_Merchant_OrderDoneInfo extends AppCompatActivity {

    private int order_id;
    private OrderDoneInfoForM orderDoneInfoForM;

    private View mEmptyView;//无数据视图

    private EmptyRecyclerView recyclerView;
    private TextView order_Address, rider_name, rider_phone, orderId, order_time, arrive_time;

    private Adapter_OrderDetails orderDetailsAdapter;

    //网络请求时，弹出此框等待网络数据
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
        setContentView(R.layout.activity__merchant__order_done_info);

        //将android自带的返回键启动，并通过重写onOptionsItemSelected实现返回键的功能
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getIntentData();

        getOrderDoneData();

    }

    private void getIntentData() {
        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", 0);
    }

    private void getOrderDoneData(){
//        pg.setMessage("加载中...");
//        pg.show();
        if(!Util.checkNetwork(this)){
            pg.dismiss();
            return;
        }

        new Thread(){
            @Override
            public void run() {
                try {
                    orderDoneInfoForM = MerchantRequestUtility.getOrderDoneInfo(order_id);
                    handler.post(runnableOrderDoneInfo);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Runnable runnableOrderDoneInfo = new Runnable() {
        @Override
        public void run() {
//            pg.dismiss();
            initView();
        }
    };

    private void initView(){
        recyclerView = findViewById(R.id.orderDoneRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        orderDetailsAdapter = new Adapter_OrderDetails(orderDoneInfoForM.getOrderDetailsList());
        recyclerView.setAdapter(orderDetailsAdapter);
        recyclerView.setEmptyView(mEmptyView);

        order_Address = findViewById(R.id.order_address);
        rider_name = findViewById(R.id.rider_name);
        rider_phone = findViewById(R.id.rider_phone);
        orderId = findViewById(R.id.order_id);
        order_time = findViewById(R.id.order_time);
        arrive_time = findViewById(R.id.arrive_time);

        order_Address.setText(orderDoneInfoForM.getAddress().getAddressName());
        rider_name.setText(orderDoneInfoForM.getRider().getName());
        rider_phone.setText(orderDoneInfoForM.getRider().getPhoneNumber());

        Log.i("orderDoneInfo",orderDoneInfoForM.getOrder_id()+"");
        orderId.setText(orderDoneInfoForM.getOrder_id()+"");
        order_time.setText(orderDoneInfoForM.getOrderTime());
        arrive_time.setText(orderDoneInfoForM.getArriveTime());
    }

    //重写返回键方法
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                intent.putExtras(bundle);
                setResult(215, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //baseActibity方法，删除当前页面
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果是返回键
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //重写返回键
            //向Fragment_Home页面返回结果
            Intent intent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putInt("business_id", business_id);
//            bundle.putString("product_id", "123123" );
//            intent.putExtras(bundle);
            setResult(215, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
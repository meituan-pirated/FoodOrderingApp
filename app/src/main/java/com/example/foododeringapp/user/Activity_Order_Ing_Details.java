package com.example.foododeringapp.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.OrderDetails;
import com.example.foododeringapp.bean.OrderDetailsForR;
import com.example.foododeringapp.bean.OrderDetailsForU;
import com.example.foododeringapp.control.BaseActivity;
import com.example.foododeringapp.rider.Activity_Order_details;
import com.example.foododeringapp.rider.adapter.Adapter_Food_Record;
import com.example.foododeringapp.rider.service.RiderRequestUtility;
import com.example.foododeringapp.user.service.UserRequestUtility;

import java.text.NumberFormat;
import java.util.ArrayList;

public class Activity_Order_Ing_Details extends BaseActivity implements View.OnClickListener {

    private Context context;
    private TextView merchant_name,buyer_add;
    private TextView rider_name,rider_phone,order_time;
    private Button btn_wait,btn_accept,btn_delivering,btn_get;//取和送的图标
    private TextView order_note,tv_cost,state;
    private Button btn_contact;
    private ImageView close_order;
    private RecyclerView rvFoods;
    private Adapter_Food_Record adapter;
    private NumberFormat nf;
    private int statusCode = 0;

    private String orderID;//从intent那取来的万恶之源
    double cost = 0, deliver_fee = 1;//总价格,配送费
    private OrderDetailsForU orderDetails;
    private ArrayList<OrderDetails> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_order_ing_details);
        getIntentData();
        initView();
    }
    private void getIntentData() {
        Intent intent = getIntent();
        orderID = intent.getStringExtra("orderID");

    }

    private void findViewById() {
        merchant_name = (TextView) findViewById(R.id.merchant_name);
        buyer_add = (TextView) findViewById(R.id.buyer_add);
        rider_name = (TextView) findViewById(R.id.rider_name);
        rider_phone = (TextView) findViewById(R.id.rider_phone);
        order_time = (TextView) findViewById(R.id.order_time);
        order_note = (TextView) findViewById(R.id.order_note);
        tv_cost = (TextView) findViewById(R.id.cost);
        rvFoods = (RecyclerView) findViewById(R.id.foodRecyclerView);

        //需要监听的
        btn_contact = (Button)findViewById(R.id.btn_contact);
        state = (TextView)findViewById(R.id.state);
        close_order = (ImageView)findViewById(R.id.close_order);

        btn_wait = (Button)findViewById(R.id.btn_wait);
        btn_accept = (Button)findViewById(R.id.btn_accept);
        btn_get = (Button)findViewById(R.id.btn_get);
        btn_delivering = (Button)findViewById(R.id.btn_delivering);

    }
    private void initView() {
        context = Activity_Order_Ing_Details.this;

        //更改工具栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolbarText = (TextView) findViewById(R.id.toolbar_text);
        toolbarText.setText("订单详情");

        //获取订单详细数据
        orderDetails = UserRequestUtility.getOrderDetailsForU(orderID);
        String s = orderDetails.getOrder().getOrderState();
        Log.i("订单状态；",s);
        arrayList = orderDetails.getOrder().getOrderDetailsList(); //把食物记录先抓出来


        //获得所有控件
        findViewById();

        //为er个按键设置监听
        btn_contact.setOnClickListener(this);
        close_order.setOnClickListener(this);

        //初始Adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_Order_Ing_Details.this);
        rvFoods.setLayoutManager(layoutManager);
        adapter = new Adapter_Food_Record(Activity_Order_Ing_Details.this, arrayList);
        rvFoods.setAdapter(adapter);

        // 计算出总价格
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        for (int i = 0; i < arrayList.size(); i++) {
            OrderDetails item = arrayList.get(i);
            cost += item.getAmount() * item.getProduct().getSalePrice();
        }
        cost += 1; //加上配送费，配送费锁死就是1
        tv_cost.setText(nf.format(cost));

        //将订单信息都填到控件里

        merchant_name.setText(orderDetails.getBusiness().getBusinessName());
        buyer_add.setText(orderDetails.getOrder().getAddress().getAddressName());
        if(orderDetails.getRider()==null){
            rider_name .setText("暂无骑手");
        }else{
            rider_name.setText(orderDetails.getRider().getName());
            rider_phone.setText(orderDetails.getRider().getPhoneNumber());
        }
        order_time.setText(orderDetails.getOrder().getOrderTime());
        order_note.setText(orderDetails.getOrder().getOrderNote());


        //若已到店，取字图标亮起并且按钮内容变成已送达
//        String s = orderDetails.getOrder().getOrderState();
//        Log.i("订单状态；",s);
        if(s.equals("pay")){
            state.setText("商家待结单");
        }else if(s.equals("maccept")){
            state.setText("商家已接单");
            btn_wait.setBackgroundResource(R.drawable.states_finished);
        }else if(s.equals("raccept")){
            state.setText("骑手已接单");
            btn_wait.setBackgroundResource(R.drawable.states_finished);
            btn_accept.setBackgroundResource(R.drawable.states_finished);
        }else if(s.equals("at_res")){
            state.setText("骑手已到店");
            btn_wait.setBackgroundResource(R.drawable.states_finished);
            btn_accept.setBackgroundResource(R.drawable.states_finished);
            btn_get.setBackgroundResource(R.drawable.states_finished);
        }else if(s.equals("delivering")){
            state.setText("配送中");
            btn_wait.setBackgroundResource(R.drawable.states_finished);
            btn_accept.setBackgroundResource(R.drawable.states_finished);
            btn_get.setBackgroundResource(R.drawable.states_finished);
            btn_delivering.setBackgroundResource(R.drawable.states_finished);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_contact://想要拨号
                //根据电话拨号！
                String phoneNum = orderDetails.getRider().getPhoneNumber();
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + phoneNum);
                intent.setData(data);
                Activity_Order_Ing_Details.this.startActivity(intent);
//               还有拨打电话的权限 <uses-permission android:name="android.permission.CALL_PHONE" />
                break;
            case R.id.close_order://关闭详细页面,并且指定返回的是上一个activity的哪个fragment
                setResult(1222);
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Activity_Order_Ing_Details.this.setResult(1222);
                Activity_Order_Ing_Details.this.finish();
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}

package com.example.foododeringapp.user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Foods;
import com.example.foododeringapp.control.BaseActivity;
import com.example.foododeringapp.user.adapter.Adapter_User_Balance;
import com.example.foododeringapp.util.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.text.NumberFormat;
import java.util.ArrayList;

public class Activity_User_Balance extends BaseActivity implements View.OnClickListener{

//    <!--    ub_AddressInfo 地址的 LinearLayout部分: -->
//    <!--    user_name 名字 user_phone 电话 user_address 地址-->
//    <!--    ub_remarkInfo 订单备注LinearLayout: ub_remark 订单备注-->
//    <!--    swipeRefreshLayout 下拉刷新控件 orderRecyclerView 订单-->
//    <!--    ub_CostBalance 总计金额（不包括配送费）-->
//    <!--    ub_Balance 付款按钮   ubCost 总金额（ub_CostBalance + 1）  ub_CostBalance 总计 -->

    private Context context;
    private LinearLayout ub_AddressInfo, ub_remarkInfo;//地址信息 备注信息
    private RelativeLayout have_address;
    private TextView user_name, user_sex, user_phone, user_address, ub_remark, no_address;
    private TextView ub_CostBalance, ub_Balance, ubCost;//商品金额，付款按钮，总金额
    private RecyclerView orderRecyclerView;
    private Adapter_User_Balance adapter;
    private String name, sex, addressName, avatar, remarkContent;
    private String orderNote = "";
    private String phoneNumber;
    private NumberFormat nf;
    //private double longitude, latitude;

    private int userID, addressID;
    private Double paycost;
    private int merchantID;
    private int statusCode = 200;
    double cost = 0, costDistribution = 1;//总价格,配送费
    private ArrayList<Foods> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__user__balance);
        getIntentData();
        initView();
    }


    private void findViewById() {
        user_name = findViewById(R.id.user_name);
        user_sex = findViewById(R.id.user_sex);
        user_phone = findViewById(R.id.user_phone);
        user_address = findViewById(R.id.user_address);
        ub_remark = findViewById(R.id.ub_remark);
        ub_Balance = findViewById(R.id.ub_Balance);
        ub_CostBalance = findViewById(R.id.ub_CostBalance);
        ubCost = findViewById(R.id.ubCost);

        orderRecyclerView = findViewById(R.id.orderRecyclerView);

        ub_AddressInfo = findViewById(R.id.ub_AddressInfo);
        ub_remarkInfo = findViewById(R.id.ub_remarkInfo);
        no_address = findViewById(R.id.no_address);
        have_address = findViewById(R.id.have_address);
    }


    private void initView() {
        context = Activity_User_Balance.this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolbarText = findViewById(R.id.toolbar_text);
        toolbarText.setText("提交订单");
//        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        findViewById();

//      给地址选择、备注选择和结算添加了响应
        ub_remarkInfo.setOnClickListener(this);
        ub_AddressInfo.setOnClickListener(this);
        ub_Balance.setOnClickListener(this);

        adapter = new Adapter_User_Balance(Activity_User_Balance.this, arrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        orderRecyclerView.setLayoutManager(layoutManager);
        orderRecyclerView.setAdapter(adapter);

        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);

        // 计算出总价格
        for (int i = 0; i < arrayList.size(); i++) {
            Foods item = arrayList.get(i);
            cost += item.count * item.getPrice();
        }
        ub_CostBalance.setText(nf.format(cost));
        ubCost.setText(nf.format(cost + 1));
        paycost = cost + 1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ub_Balance://结算
                Log.i("address", (String) user_name.getText());
                if (user_name.getText() == "") {
                    Util.showToast(Activity_User_Balance.this, "此收货地址未包含地址信息，请重新选择地址");
                }
                else {
                    //Activity_User_Payment.actionStart(Activity_User_Balance.this, userId, cost, statusCode, userName, address, arrayList, remarkContent);
                    Intent intent = new Intent(context, Activity_User_Payment.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectedList", arrayList);
                    bundle.putInt("userID", userID);
                    bundle.putInt("merchantID", merchantID);
                    bundle.putString("remarkContent", remarkContent);
                    bundle.putString("orderState", "PAY");
                    bundle.putInt("addressID", addressID);
                    bundle.putDouble("order_price", paycost);
                    bundle.putString("orderNote", orderNote);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            case R.id.ub_remarkInfo://备注信息
                Intent intentRemark = new Intent(Activity_User_Balance.this, Activity_User_Remark.class);
                //System.out.print("点击了备注信息");
                startActivityForResult(intentRemark, 102);
                break;
            case R.id.ub_AddressInfo://地址信息
                Intent intent = new Intent(context, Activity_User_Choose_Address.class);
                intent.putExtra("remark", "结算页面");
                intent.putExtra("userID", userID);
                intent.putExtra("selectedList", arrayList);
                startActivityForResult(intent, 103);
                break;
            default:
                break;
        }
    }

    /**
     * 获取从地址页面回传的数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 103://地址选择页面需要返回data（包括String类型：name, phoneNumber, sex, addressName)
                if (resultCode == RESULT_OK) {
                    name = data.getStringExtra("name");
                    phoneNumber = data.getStringExtra("phoneNumber");
                    sex = data.getStringExtra("sex");
                    addressName = data.getStringExtra("address");
                    addressID = data.getIntExtra("addressID", -1);
                    user_name.setText(name);
                    user_phone.setText(phoneNumber);
                    user_address.setText(addressName);

                    if (sex.equals("男")) {
                        user_sex.setText("先生");
                    } else if (sex.equals("女")) {
                        user_sex.setText("女士");
                    } else {
                        user_sex.setText(sex);
                    }
                    no_address.setVisibility(View.GONE);
                    have_address.setVisibility(View.VISIBLE);
                }
                break;
            case 102://备注页面（返回备注信息remarkContent：String类型）
                if (resultCode == RESULT_OK) {
                    orderNote = data.getStringExtra("remarkContent");
                    ub_remark.setText(orderNote);
                }
                break;
            default:
                break;
        }
    }

    private void getIntentData() {
        Intent intent = getIntent();
        arrayList = (ArrayList<Foods>) intent.getSerializableExtra("selectList");
        userID = intent.getIntExtra("userID", -1);
//        Log.i("UserID:", String.valueOf(userID));
//        userName = intent.getStringExtra("userName");
        merchantID = intent.getIntExtra("merchantID", -1);
//        statusCode = intent.getIntExtra("statusCode", -1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
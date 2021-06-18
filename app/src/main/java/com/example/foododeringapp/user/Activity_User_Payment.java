package com.example.foododeringapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Foods;
import com.example.foododeringapp.user.service.UserRequestUtility;
import com.example.foododeringapp.util.Util;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;


public class Activity_User_Payment extends AppCompatActivity implements View.OnClickListener {
    private  int userID, merchantID, addressID;
    private Double order_price;
    private String remarkContent, orderState;
    private TextView merchant, price, user;
    private Button success;
    private ProgressDialog pg;
    private NumberFormat nf;
    private ArrayList<Foods> selectedList;
    private Foods selectedFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__user__payment);
        getContentData();
        initView();

    }

    private void initView() {
        findViewByID();

        pg = new ProgressDialog(this);
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        String cost = nf.format(order_price);

        success.setOnClickListener(this);

        merchant.setText(String.valueOf(merchantID));
        user.setText(String.valueOf(userID));
        price.setText(cost);
    }

    private void findViewByID() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolbarText = findViewById(R.id.toolbar_text);
        toolbarText.setText("支付");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        merchant = findViewById(R.id.merchantID);
        user = findViewById(R.id.userID);
        price = findViewById(R.id.orderPrice);
        success = findViewById(R.id.success);
    }


//    intent.putExtra("userID", userID);
//                    intent.putExtra("merchantID", merchantID);
//                    intent.putExtra("remarkContent", remarkContent);
//                    intent.putExtra("orderState", "WAIT");
//                    intent.putExtra("addressID", addressID);
//                    intent.putExtra("order_price", Integer.valueOf((String) ubCost.getText()));
    private void getContentData() {
        Intent intent = getIntent();
        merchantID = intent.getIntExtra("merchantID", -1);
        userID = intent.getIntExtra("userID", -1);
        addressID = intent.getIntExtra("addressID", -1);
        order_price = intent.getDoubleExtra("order_price", -1);
        // Log.i("price", String.valueOf(order_price));
        remarkContent = intent.getStringExtra("remarkContent");
        orderState = intent.getStringExtra("remarkContent");
        selectedList = (ArrayList<Foods>) intent.getSerializableExtra("selectList");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.success:
                orderState = "WAIT";
                boolean isOperationOK;
                isOperationOK = UserRequestUtility.insertOrder(userID, merchantID, addressID, order_price, remarkContent, orderState, selectedList);
                if(isOperationOK){
                    Util.showToast(Activity_User_Payment.this, "下单失败");
                }else{
                    Util.showToast(Activity_User_Payment.this, "下单成功");
                }
                break;
            default:
                break;
        }
    }

    //重写返回键方法
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                intent.putExtras(bundle);
                setResult(101, intent);
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
            setResult(101, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
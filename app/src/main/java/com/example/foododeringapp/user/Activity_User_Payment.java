package com.example.foododeringapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Foods;
import com.example.foododeringapp.bean.orderProducts;
import com.example.foododeringapp.user.service.UserRequestUtility;
import com.example.foododeringapp.util.Util;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Activity_User_Payment extends AppCompatActivity implements View.OnClickListener {
    private  int userID, merchantID, addressID;
    private Double order_price;
    private String remarkContent, orderState, orderNote;
    private TextView merchant, price, user;
    private Button pay_operation;
    private ProgressDialog pg;
    private NumberFormat nf;
    private ArrayList<Foods> selectedList = new ArrayList<Foods>();
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

        pay_operation.setOnClickListener(this);

        merchant.setText(String.valueOf(merchantID));
        user.setText(String.valueOf(userID));
        price.setText(cost);
    }

    private void findViewByID() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolbarText = findViewById(R.id.toolbar_text);
        toolbarText.setText("??????");
//        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        merchant = findViewById(R.id.merchantID);
        user = findViewById(R.id.userID);
        price = findViewById(R.id.orderPrice);
        pay_operation = findViewById(R.id.pay_operation);
    }


//    intent.putExtra("userID", userID);
//                    intent.putExtra("merchantID", merchantID);
//                    intent.putExtra("remarkContent", remarkContent);
//                    intent.putExtra("orderState", "WAIT");
//                    intent.putExtra("addressID", addressID);
//                    intent.putExtra("order_price", Integer.valueOf((String) ubCost.getText()));
    private void getContentData() {
        Intent intent = getIntent();
        orderNote = "";
        merchantID = intent.getIntExtra("merchantID", -1);
        userID = intent.getIntExtra("userID", -1);
        addressID = intent.getIntExtra("addressID", -1);
        order_price = intent.getDoubleExtra("order_price", -1);
        // Log.i("price", String.valueOf(order_price));
        remarkContent = intent.getStringExtra("remarkContent");
        orderState = intent.getStringExtra("remarkContent");
        orderNote = intent.getStringExtra("orderNote");
        selectedList = (ArrayList<Foods>) intent.getSerializableExtra("selectedList");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pay_operation:
                Uri uri = Uri.parse("https://admin.zhanzhangfu.com/foolpay?code=568359");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {

                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 3000);//3?????????????????????????????????
                pay_operation.setText("????????????");
                orderState = "PAY";
//                Log.i("time", getTime());
                int res = UserRequestUtility.insertOrder(userID, merchantID, addressID, orderNote, order_price, remarkContent, orderState, getTime());
                for (int i = 0; i < selectedList.size(); i++) {
                    Foods food = new Foods();
                    food = selectedList.get(i);
                    UserRequestUtility.insertOrderDetails(res, food.getId(), food.getCount());
                }
//                if(isOperationOK){
//                    Util.showToast(Activity_User_Payment.this, "????????????");
//                }else{
//                    Util.showToast(Activity_User_Payment.this, "????????????");
//                }
                break;
            default:
                break;
        }
    }

    private String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//??????????????????
        String str  = formatter.format(curDate);
        return str;
    }

    //?????????????????????
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

    //baseActibity???????????????????????????
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //??????????????????
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //???????????????
            //???Fragment_Home??????????????????
            Intent intent = new Intent();
            setResult(101, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
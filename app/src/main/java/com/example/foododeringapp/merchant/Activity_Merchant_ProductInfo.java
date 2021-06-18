package com.example.foododeringapp.merchant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Products;
import com.example.foododeringapp.control.BaseActivity;
import com.example.foododeringapp.merchant.service.MerchantRequestUtility;
import com.example.foododeringapp.util.Util;

public class Activity_Merchant_ProductInfo extends BaseActivity {

    private int product_id;
    private Products products;

    private TextView product_name, product_sale_price, product_delivery_price, descriptions;
    private Button btn_product_save;

    private ProgressDialog pg ;


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
        setContentView(R.layout.activity__merchant__product_info);
        pg = new ProgressDialog(this);

        //将android自带的返回键启动，并通过重写onOptionsItemSelected实现返回键的功能
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getIntentData();

        getProductData();
    }

    public static void actionStart(Context context, int product_id) {
        Intent intent = new Intent(context, Activity_Merchant_ProductInfoList.class);
        Bundle bundle = new Bundle();
        bundle.putInt("product_id", product_id);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    private void getIntentData() {
        Intent intent = getIntent();
        product_id = intent.getIntExtra("product_id", 0);
//        Log.i("business_id", product_id+"");
    }

    private void getProductData(){
        pg.setMessage("加载中...");
        pg.show();
        if(!Util.checkNetwork(this)){
            pg.dismiss();
            return;
        }

        new Thread(){
            @Override
            public void run() {
                try {
                    products = MerchantRequestUtility.getProductInfo(product_id);
                    handler.post(runnableProductInfo);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Runnable runnableProductInfo = new Runnable() {
        @Override
        public void run() {
            pg.dismiss();
            initView();
        }
    };

    private void initView(){
        product_name = findViewById(R.id.product_name);
        product_sale_price = findViewById(R.id.product_sale_price);
        product_delivery_price = findViewById(R.id.product_delivery_price);
        descriptions = findViewById(R.id.descriptions);

        product_name.setText(products.getProductName());
        product_sale_price.setText(products.getSalePrice()+"");
//        product_delivery_price.setText(products.getDeliveryPrice()+"");
        descriptions.setText(products.getDescriptions());
    }

    //重写返回键方法
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                intent.putExtras(bundle);
                setResult(312, intent);
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
            setResult(312, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
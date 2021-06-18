package com.example.foododeringapp.merchant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Products;
import com.example.foododeringapp.bean.RestFulBean;
import com.example.foododeringapp.control.BaseActivity;
import com.example.foododeringapp.merchant.service.MerchantPostUtility;
import com.example.foododeringapp.merchant.service.MerchantRequestUtility;
import com.example.foododeringapp.util.BottomDialog;
import com.example.foododeringapp.util.ImageTools;
import com.example.foododeringapp.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.util.Base64.decode;

public class Activity_Merchant_changeProductInfo extends BaseActivity{
// implements BottomDialog.onItemClickListener
    private int product_id;
    private Products products;

    private ImageView change_img ,img_changed;
    private EditText product_name, product_sale_price, product_delivery_price, descriptions;

    private Button btn_product_change;

    private SharedPreferences sharePreference;

    private ProgressDialog pg ;

    private String postFormChangeProduct;
    private boolean productChanged = false;


    private static final int PHOTO_FROM_GALLERY = 304;
    private static final int PHOTO_FROM_CAMERA = 305;
    private File appDir;
    private Uri uriForCamera;
    private Date date;
    private String str = "";

    private boolean selectClicked = false;

    BottomDialog fragment;
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
        setContentView(R.layout.activity__merchant_change_product_info);

        pg = new ProgressDialog(this);
        //将android自带的返回键启动，并通过重写onOptionsItemSelected实现返回键的功能
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        initView();

        init();

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

    private void init(){
        product_name = findViewById(R.id.product_name);
        product_sale_price = findViewById(R.id.product_sale_price);
        product_delivery_price = findViewById(R.id.product_delivery_price);
        descriptions = findViewById(R.id.descriptions);
        change_img = findViewById(R.id.change_img);
        img_changed = findViewById(R.id.img_changed);

        btn_product_change = findViewById(R.id.btn_product_change);
        change_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectClicked = true;
                FragmentManager fm = getSupportFragmentManager();
                //实例化对象
                fragment = new BottomDialog();
                if(selectClicked){
                    Log.i("infoclick", "infoclick");
                    fragment.show(fm, "fragment_bottom_dialog");
                }
            }
        });

        btn_product_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productChanged = true;
                try {
                    saveProductChange();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void getIntentData() {
        Intent init_intent = getIntent();
        product_id = init_intent.getIntExtra("product_id", 0);
        Log.i("business_id", product_id+"");
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
        Log.i("infoclick", "infoclick");

        product_name.setText(products.getProductName());
        product_sale_price.setText(products.getSalePrice()+"");
//        product_delivery_price.setText(products.getDeliveryPrice()+"");
        descriptions.setText(products.getDescriptions());


        //Android不推荐使用全局变量，我在这里使用了sharePreference
        sharePreference = getSharedPreferences("changeProductInfo", Activity.MODE_PRIVATE);



    }

    private void saveProductChange() throws IOException {
        products.setProduct_id(products.getProduct_id());
        if("".equals(product_name.getText().toString())){
            Util.showToast(Activity_Merchant_changeProductInfo.this, "商品名称不能为空，请注意输入!");
            return;
        }
        Log.i("product_name", product_name.getText().toString());
        products.setProductName(product_name.getText().toString());
        if("".equals((product_sale_price.getText().toString()))){
            Util.showToast(Activity_Merchant_changeProductInfo.this, "商品价格不能为空，请注意输入!");
            return;
        }
        products.setSalePrice(Integer.parseInt(product_sale_price.getText().toString()));
        products.setDescriptions(descriptions.getText().toString());
        if (!Util.checkNetwork(this)) {
            Toast.makeText(this, "网络连接失败，请检查网络连接设置！", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri = Uri.parse(sharePreference.getString("uri",""));
        img_changed.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(img_changed.getDrawingCache());
        img_changed.setDrawingCacheEnabled(false);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // 压缩图片
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] byte_arr = stream.toByteArray();
        // Base64图片转码为String
        String encodedString = Base64.encodeToString(byte_arr, 0);

        products.setImage(encodedString);


        new Thread(){
            @Override
            public void run() {
                try {
                    Log.i("changedProduct",products.getSalePrice()+"");
                    postFormChangeProduct = MerchantPostUtility.saveProductChange(products);
//                    Log.i("postForm:", postFormChangeProduct);
                    handler.post(runnableChangeOrderState);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private Runnable runnableChangeOrderState = new Runnable() {
        @Override
        public void run() {
            pg.dismiss();
//            if(postFormChangeOrderState == null || postFormChangeOrderState.length()>100){
//            if(postFormChangeOrderState == null){
//                Util.showToast(getActivity(), "接单失败，请重试！");
//                return;
//            }
            //接单成功，则弹窗显示成功
            Gson gson = new Gson();
            RestFulBean<String> restFulBean = gson.fromJson(postFormChangeProduct,  new TypeToken<RestFulBean<String>>() {
            }.getType());

            if(restFulBean.getStatus() == -1){
                Util.showToast(Activity_Merchant_changeProductInfo.this, "操作失败");
                return;
            }else if(restFulBean.getStatus() == 0){
                Util.showToast(Activity_Merchant_changeProductInfo.this, "操作成功");
//                changedState = true;
            }else{
                Util.showToast(Activity_Merchant_changeProductInfo.this, restFulBean.getStatus()+restFulBean.getData());
            }
        }
    };

    //重写返回键方法
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putBoolean("productChanged", productChanged);
                intent.putExtras(bundle);
                setResult(312, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //baseActibity方法，删除当前页面
    @Override
    protected void onDestroy() {
        Log.i("destroy", "onDestroy");
        if(fragment != null){
            fragment.dismiss();
        }
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
package com.example.foododeringapp.merchant;

import androidx.annotation.NonNull;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Business;
import com.example.foododeringapp.control.BaseActivity;
import com.example.foododeringapp.merchant.service.MerchantRequestUtility;
import com.example.foododeringapp.util.Util;

/*import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;*/


//import com.lzy.imagepickerdemo.R;
//import com.lzy.imagepickerdemo.SelectDialog;
//import com.lzy.imagepickerdemo.imageloader.GlideImageLoader;



public class Activity_Merchant_StoreInfo extends BaseActivity {
    private int business_id;
    private Business business;

    private EditText business_name;
    private TextView refectory;
    private EditText descriptions;

    private Handler handler = new Handler();
    private ProgressDialog pg ;

    public static void actionStart(Context context, int business_id) {
        Intent intent = new Intent(context, Activity_Merchant_StoreInfo.class);
        Bundle bundle = new Bundle();
        bundle.putInt("business_id", business_id);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__merchant__store_info);

        pg = new ProgressDialog(this);
        //将android自带的返回键启动，并通过重写onOptionsItemSelected实现返回键的功能
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getIntentData();

        getStoreData();

//        initImagePicker();



    }

//    private void initImagePicker() {
//        ImagePicker imagePicker = ImagePicker.getInstance();
//        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
//        imagePicker.setShowCamera(true);                      //显示拍照按钮
//        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
//        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
//        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
//        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
//        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
//        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
//    }


    private void getIntentData() {
        Intent intent = getIntent();
        business_id = intent.getIntExtra("business_id", 0);
//        Log.i("business_id", business_id+"");
    }

    /**
     * 获取商家信息列表
     */
    private void getStoreData(){
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
                    business = MerchantRequestUtility.getBusinessInfo(business_id);
                    handler.post(runnableBusinessInfo);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Runnable runnableBusinessInfo = new Runnable() {
        @Override
        public void run() {
            pg.dismiss();
            initView();
        }
    };

    private void initView(){
        business_name = findViewById(R.id.business_name);
        refectory = findViewById(R.id.refectory);
        descriptions = findViewById(R.id.descriptions);

        business_name.setText(business.getBusiness_name());
        refectory.setText(business.getRefectory());
        descriptions.setText(business.getDescriptions());
    }


    //重写返回键方法
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("business_id", business_id);
//                bundle.putString("product_id", "123123" );
                intent.putExtras(bundle);
                setResult(311, intent);
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
            Bundle bundle = new Bundle();
            bundle.putInt("business_id", business_id);
//            bundle.putString("product_id", "123123" );
            intent.putExtras(bundle);
            setResult(311, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
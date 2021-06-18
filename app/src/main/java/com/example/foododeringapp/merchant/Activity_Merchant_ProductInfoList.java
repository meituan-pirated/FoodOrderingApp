package com.example.foododeringapp.merchant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.ProductBriefForM;
import com.example.foododeringapp.bean.RestFulBean;
import com.example.foododeringapp.control.BaseActivity;
import com.example.foododeringapp.merchant.adapter.Adapter_ProductBriefForM;
import com.example.foododeringapp.merchant.service.MerchantPostUtility;
import com.example.foododeringapp.merchant.service.MerchantRequestUtility;
import com.example.foododeringapp.util.Util;
import com.example.foododeringapp.widget.EmptyRecyclerView;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class Activity_Merchant_ProductInfoList extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Integer business_id;
    private List<ProductBriefForM> productBriefForMList;

    private View eEmptyView;//无数据视图
    private EmptyRecyclerView productRecyclerView;

    private BottomSheetLayout bottomSheetLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

//    private Integer business_id;
    private String postFormDeleteProduct;
    private Adapter_ProductBriefForM adapter;
    private Handler handler = new Handler();
    private ProgressDialog pg ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__merchant__product_info_list);
        pg = new ProgressDialog(this);
        //将android自带的返回键启动，并通过重写onOptionsItemSelected实现返回键的功能
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getIntentData();
        getProductData();
    }

    public static void actionStart(Context context, int business_id) {
        Intent intent = new Intent(context, Activity_Merchant_ProductInfoList.class);
        Bundle bundle = new Bundle();
        bundle.putInt("business_id", business_id);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        business_id = intent.getIntExtra("business_id", 0);
        Log.i("business_id", business_id+"");
    }

    /**
     * 获取简略商品列表
     */
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
                    productBriefForMList = MerchantRequestUtility.getProductForM(business_id);
                    handler.post(runnableProductBrief);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Runnable runnableProductBrief = new Runnable() {
        @Override
        public void run() {
            pg.dismiss();
            initView();
        }
    };

    private void initView(){
        bottomSheetLayout =  findViewById(R.id.bottom_sheet_layout);
        //刷新控件
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        productRecyclerView = findViewById(R.id.productRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        productRecyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter_ProductBriefForM(productBriefForMList, this);

        adapter.setOnItemClickListener(new Adapter_ProductBriefForM.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(Activity_Merchant_ProductInfoList.this, Activity_Merchant_ProductInfo.class);
                Log.i("productList", productBriefForMList.size()+"");
                Log.i("position", position+"");
                intent.putExtra("product_id", productBriefForMList.get(position).getProduct_id());
                startActivityForResult(intent, 303);
            }
        });

        productRecyclerView.setAdapter(adapter);

//        adapter.setOnItemClickListener(new Adapter_ProductBriefForM.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                ProductBriefForM productBriefForM = productBriefForMList.get(position);
//                int product_id = productBriefForM.getProduct_id();
//
//                Intent intent = new Intent(Activity_Merchant_ProductInfoList.this, Activity_Merchant_ProductInfo.class);
//                intent.putExtra("product_id", product_id);
//                startActivityForResult(intent, 303);
//            }
//        });


    }


    /**
     * 根据商品编号删除商品
     * @param product_id
     */
    public void deleteProductByProductId(int product_id){
//        if(state == "ING"){
//            pg.setMessage("接单中");
//        }else{
//            pg.setMessage("拒单中");
//        }
//        pg.show();
        if (Activity_Merchant_Main.networkState == 0) {
            Toast.makeText(this, "网络连接失败，请检查网络连接设置！", Toast.LENGTH_SHORT).show();
            pg.dismiss();
            return;
        }

        new Thread(){
            @Override
            public void run() {
                try {
                    postFormDeleteProduct = MerchantPostUtility.deleteProduct(product_id);
//                    Log.i("postForm:", postFormChangeOrderState);
                    handler.post(runnableDeleteProduct);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private Runnable runnableDeleteProduct = new Runnable() {
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
//            Log.i("postForm",postFormChangeOrderState);
            RestFulBean<String> restFulBean = gson.fromJson(postFormDeleteProduct,  new TypeToken<RestFulBean<String>>() {
            }.getType());
//            Log.i("restfulBean:", restFulBean.getMsg());
//            String msg = restFulBean.getMsg();

            if(restFulBean.getStatus() == -1){
                Util.showToast(Activity_Merchant_ProductInfoList.this, "操作失败");
                return;
            }else{
                Util.showToast(Activity_Merchant_ProductInfoList.this, "操作成功");
//                changedState = true;
//                showList();
                getProductData();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode){
//            case 312:
//                if(data.getBooleanExtra("productChanged", false)){
//                    Log.i("changed", "changed");
//                    getProductData();
//                }
//        }
        Log.i("changed", "changed");
        getProductData();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    //重写返回键方法
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("business_id", business_id);
                bundle.putString("product_id", "123123" );
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
            bundle.putString("product_id", "123123" );
            intent.putExtras(bundle);
            setResult(312, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 重写刷新方法
    @Override
    public void onRefresh() {
        getProductData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //刷新控件停止两秒后消失
                    Thread.sleep(1000);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
package com.example.foododeringapp.merchant.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.OrderDoneBriefForM;
import com.example.foododeringapp.merchant.Activity_Merchant_Main;
import com.example.foododeringapp.merchant.Activity_Merchant_OrderDoneInfo;
import com.example.foododeringapp.merchant.adapter.Adapter_OrdersDone;
import com.example.foododeringapp.merchant.service.MerchantRequestUtility;
import com.example.foododeringapp.widget.EmptyRecyclerView;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.List;

public class Fragment_Merchant_Order_Done2 extends LazyFragment  implements SwipeRefreshLayout.OnRefreshListener{
    private List<OrderDoneBriefForM> ordersList;     //待处理的订单合集

    private View mEmptyView;//无数据视图

    private EmptyRecyclerView recyclerView;

    //xml文件控件
    private BottomSheetLayout bottomSheetLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Integer business_id= 12345678;
    private Adapter_OrdersDone orderDoneAdapter;

    //网络请求时，弹出此框等待网络数据
    private ProgressDialog pg;

    //用于刷新
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private String postFormOrderDoneDetails;

    private boolean changedState = false;

    private boolean isCreateView = false;
    private boolean isLoadData = false;

    private View mFragmentView;

    public Fragment_Merchant_Order_Done2() {
    }

    public static Fragment_Merchant_Order_Done2 newInstance(int business_id){
        Fragment_Merchant_Order_Done2 fragment = new Fragment_Merchant_Order_Done2();
        Bundle args = new Bundle();
        args.putInt("business_id", business_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    protected int getLayoutRes() {
        Log.i("donegetLayoutRes", R.layout.fragment__merchant__order__done+"");
        return R.layout.fragment__merchant__order__done;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("done", "onActivityCreated");
//        initView;

        if(getUserVisibleHint()){
            Log.i("done-show", "hint");
            showList();
        }

    }

    @Override
    protected void onFragmentLoad(){
        showList();
    }

    @Override
    protected void initView(View view) {
        bottomSheetLayout =  view.findViewById(R.id.bottom_sheet_layout);
        //刷新控件
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = view.findViewById(R.id.doneRecyclerView);


        pg = new ProgressDialog(getActivity());
    }

    /**
     * 加载进行中订单信息
     */
    private void showList() {
        if(!changedState){
            pg.setMessage("数据加载中...");
            pg.show();
        }

        changedState = false;
        if (Activity_Merchant_Main.networkState == 0) {
            Toast.makeText(getActivity(), "网络连接失败，请检查网络连接设置！", Toast.LENGTH_SHORT).show();
            pg.dismiss();
            return;
        }

        new Thread() {
            public void run() {
                try {
                    ordersList = MerchantRequestUtility.getDoneOrders(business_id);
                  Log.i("done-showlist", ordersList.size()+"");
                    handler.post(runnableOrderList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Runnable runnableOrderList = new Runnable() {
        @Override
        public void run() {
            pg.dismiss();

            if (ordersList != null && ordersList.size() > 0) {
//                给ordersList创建RecyclerView
//                Log.i("orderList", ordersList.get(0).getOrderState());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                orderDoneAdapter = new Adapter_OrdersDone(ordersList,  getActivity(), Fragment_Merchant_Order_Done2.this);

                orderDoneAdapter.setOnItemClickListener(new Adapter_OrdersDone.OnItemClickListener(){
                    //跳转到orderDoneInfo
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.i("orderDone","fragment-adapterClick");
                        Intent intent = new Intent(getActivity(), Activity_Merchant_OrderDoneInfo.class);
//                        Log.i("productList", productBriefForMList.size()+"");
//                        Log.i("position", position+"");
                        intent.putExtra("order_id", ordersList.get(position).getOrder_id());
                        startActivityForResult(intent, 203);
                    }
                });

                recyclerView.setAdapter(orderDoneAdapter);
                recyclerView.setEmptyView(mEmptyView);


            }
        }
    };

    public void jumpToOrderDoneInf(int position){
        Log.i("orderDone","fragment-adapterClick");
        Intent intent = new Intent(getActivity(), Activity_Merchant_OrderDoneInfo.class);
//                        Log.i("productList", productBriefForMList.size()+"");
//                        Log.i("position", position+"");
        intent.putExtra("order_id", ordersList.get(position).getOrder_id());
        startActivityForResult(intent, 203);
    }


//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        Log.i("done-visible","lazyload");
//        super.setUserVisibleHint(isVisibleToUser);
//        if(isVisibleToUser && isCreateView){
//            Log.i("don-visible","lazyload");
//            lazyLoad();
//        }
//    }

//    //懒加载
//    private void lazyLoad(){
//        if(!isLoadData){
//            Log.i("isloadData","showlist");
//            showList();
//            isLoadData = true;
//        }
//        showList();
//
//    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("done","resume");
//        showList();

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("done", "pause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("done", "destroy");

    }



    @Override
    public void onRefresh() {
        showList();
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

package com.example.foododeringapp.rider.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Order;
import com.example.foododeringapp.bean.OrderForR;
import com.example.foododeringapp.bean.OrderStaForR;
import com.example.foododeringapp.merchant.Activity_Merchant_Main;
import com.example.foododeringapp.merchant.adapter.Adapter_OrdersWait;
import com.example.foododeringapp.merchant.fragment.Fragment_Merchant_Wait;
import com.example.foododeringapp.rider.adapter.Adapter_OrdersFinished;
import com.example.foododeringapp.rider.service.RiderRequestUtility;
import com.example.foododeringapp.rider.service.RiderRequestUtility;
import com.example.foododeringapp.widget.EmptyRecyclerView;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.List;

public class Fragment_Rider_Sta extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private OrderStaForR staInfo;
    private List<OrderForR> ordersList;     //待处理的订单合集
    private Integer income,quatity;


    private View mEmptyView;//无数据视图

    //xml文件控件
    private BottomSheetLayout bottomSheetLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EmptyRecyclerView recyclerView;
    private TextView tv_income, tv_quatity;

    private String rider_id; //我们从sp中取得
    private Adapter_OrdersFinished adapter;


    //用于刷新
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    private ProgressDialog pg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rider_sta, container, false);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化控件
        initView();
        pg = new ProgressDialog(getActivity());
        showList();

    }

    private void initView() {
        bottomSheetLayout =  getActivity().findViewById(R.id.bottom_sheet_layout);
        //刷新控件
        swipeRefreshLayout = getActivity().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        tv_income = getActivity().findViewById(R.id.income);
        tv_quatity = getActivity().findViewById(R.id.quantity);
        recyclerView = getActivity().findViewById(R.id.FinishedRecyclerView);

    }

    private void showList() {
        pg.setMessage("数据加载中...");
        pg.show();

        if (Activity_Merchant_Main.networkState == 0) {
            Toast.makeText(getActivity(), "网络连接失败，请检查网络连接设置！", Toast.LENGTH_SHORT).show();
            pg.dismiss();
            return;
        }

        new Thread() {
            public void run() {
                try {
                    //获取统计信息
                    SharedPreferences preferences = getActivity().getSharedPreferences("LoginInfo", Activity.MODE_PRIVATE);
                    rider_id = preferences.getString("userId","");
                    staInfo = RiderRequestUtility.GetOrderStaInfo(rider_id);
                    ordersList = staInfo.getFinishedOrderList();
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
//                整RecyclerView的数据
                Log.i("已完成的订单数为",String.valueOf(ordersList.size()));
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                adapter = new Adapter_OrdersFinished(ordersList, Fragment_Rider_Sta.this, getActivity());
                recyclerView.setAdapter(adapter);
//                设置今日单数和收入
                tv_income.setText(String.valueOf(staInfo.getIncome()));
                String count = String.valueOf(staInfo.getQuantity())+"单";
                tv_quatity.setText(count);
                recyclerView.setEmptyView(mEmptyView);
            }
        }
    };


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

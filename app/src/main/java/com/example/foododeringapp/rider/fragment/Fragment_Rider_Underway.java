package com.example.foododeringapp.rider.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Order;
import com.example.foododeringapp.bean.OrderForR;
import com.example.foododeringapp.rider.Activity_Rider_Main;
import com.example.foododeringapp.rider.adapter.Adapter_OrdersUnderway;
import com.example.foododeringapp.rider.service.RiderRequestUtility;
import com.example.foododeringapp.widget.EmptyRecyclerView;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.List;

public class Fragment_Rider_Underway extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private BottomSheetLayout bottomSheetLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EmptyRecyclerView recyclerView;
    private View mEmptyView;//无数据视图

    private List<OrderForR> ordersList;     //待处理的订单合集

    private String userName, userId, email, sex, phoneNumber, nickname; //骑手的信息
    private String merchant_name, order_id, merchant_add, buyer_add; //显示的订单会用到的
    private Adapter_OrdersUnderway adapter;


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
        View view = inflater.inflate(R.layout.fragment_rider_underway,container,false);
        //返回fragment视图，布局里只有recyclerview
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化控件
        //initView();
        pg = new ProgressDialog(getActivity());
        //showList();

    }

    private void initView() {
        bottomSheetLayout =  getActivity().findViewById(R.id.bottom_sheet_layout);
        //刷新控件
        swipeRefreshLayout = getActivity().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = getActivity().findViewById(R.id.UnderwayRecyclerView);

    }

    private void showList() {
        pg.setMessage("数据加载中...");
        pg.show();
        if (Activity_Rider_Main.networkState == 0) {
            Toast.makeText(getActivity(), "网络连接失败，请检查网络连接设置！", Toast.LENGTH_SHORT).show();
            pg.dismiss();
            return;
        }

        new Thread() {
            public void run() {
                //加载recyclerView
                try {
                    ordersList = RiderRequestUtility.getUnderwayOrdersForR(userId);   //获取数据库里本外卖员接的订单
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
//              ordersList有内容时  将ordersList传入并生成RecyclerView
                adapter = new Adapter_OrdersUnderway(Fragment_Rider_Underway.this, getActivity(), ordersList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);  //把recyclerView的adapter（我写的要死的那个）给装进去
                recyclerView.setEmptyView(mEmptyView); //不知是啥但应该有用
            }
        }
    };

    public void update(boolean refreshOrderList) {
        //更新adapter列表
        if (adapter != null && refreshOrderList) {
            adapter.notifyDataSetChanged();
        }
    }

    public void onRefresh() {

    }
}

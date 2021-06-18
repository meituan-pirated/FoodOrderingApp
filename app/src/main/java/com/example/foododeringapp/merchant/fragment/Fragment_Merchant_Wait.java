package com.example.foododeringapp.merchant.fragment;

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
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Order;
import com.example.foododeringapp.merchant.adapter.Adapter_OrdersWait;
import com.example.foododeringapp.service.RequestUtility;
import com.example.foododeringapp.widget.EmptyRecyclerView;
import com.example.foododeringapp.merchant.Activity_Merchant_Main;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.List;

public class Fragment_Merchant_Wait extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private List<Order> ordersList;     //待处理的订单合集

    private View mEmptyView;//无数据视图

    //xml文件控件
    private BottomSheetLayout bottomSheetLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EmptyRecyclerView recyclerView;


    private Integer business_id= 12345678;
    private Adapter_OrdersWait adapter;


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
        View view = inflater.inflate(R.layout.fragment__merchant__wait, container, false);
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

        recyclerView = getActivity().findViewById(R.id.waitRecyclerView);

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
                    ordersList = RequestUtility.getWaitOrders(business_id);
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
                adapter = new Adapter_OrdersWait(ordersList, Fragment_Merchant_Wait.this, getActivity());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.setEmptyView(mEmptyView);
            }
        }
    };

    /**
     * 下拉刷新事件
     */
    @Override
    public void onRefresh() {

    }

    /**
     * ---------数据的处理--------
     */

    private void updateSelectedOrdersList(Order item){

    }

    /**
     * 根据订单编号获取订单状况
     *
     * @param id 订单编号
     * @return
     */
    public void changeOrderStateById(int id, String state){
        Order temp = ordersList.get(id);
        if(temp != null){
            temp.setOrderState(state);
        }

        update(true);

    }

    public void update(boolean refreshOrderList) {
        //更新adapter列表
        if (adapter != null && refreshOrderList) {
            adapter.notifyDataSetChanged();
        }
    }

}
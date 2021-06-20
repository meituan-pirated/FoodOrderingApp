package com.example.foododeringapp.user.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.UserOrder;
import com.example.foododeringapp.user.Activity_User_Main;
import com.example.foododeringapp.user.adapter.Adapter_User_allOrders;
import com.example.foododeringapp.user.service.UserRequestUtility;
import com.example.foododeringapp.widget.EmptyRecyclerView;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.List;

public class Fragment_User_Order extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private List<UserOrder> ordersList;     //该用户的全部订单合集
    private View mEmptyView;//无数据视图

    //xml文件控件
    private BottomSheetLayout bottomSheetLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EmptyRecyclerView recyclerView;

    private Integer user_id= 2018001;
    private Adapter_User_allOrders adapter;

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
        View view = inflater.inflate(R.layout.fragment__user__order, container, false);
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

        recyclerView = getActivity().findViewById(R.id.orderRecyclerView);

    }

    private void showList() {
        pg.setMessage("数据加载中...");
        pg.show();
        if (Activity_User_Main.networkState == 0) {
            Toast.makeText(getActivity(), "网络连接失败，请检查网络连接设置！", Toast.LENGTH_SHORT).show();
            pg.dismiss();
            return;
        }

        new Thread() {
            public void run() {
                try {
                    ordersList = UserRequestUtility.getAllUserOrders(user_id);
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
            //  给ordersList创建RecyclerView
                adapter = new Adapter_User_allOrders(ordersList, Fragment_User_Order.this, getActivity());
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
}
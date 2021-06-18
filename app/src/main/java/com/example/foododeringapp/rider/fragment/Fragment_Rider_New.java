package com.example.foododeringapp.rider.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Order;
import com.example.foododeringapp.bean.OrderForR;
import com.example.foododeringapp.rider.Activity_Rider_Main;
import com.example.foododeringapp.rider.adapter.Adapter_OrdersNew;
import com.example.foododeringapp.rider.service.RiderRequestUtility;
import com.example.foododeringapp.rider.service.RiderRequestUtility;
import com.example.foododeringapp.widget.EmptyRecyclerView;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.List;

public class Fragment_Rider_New extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private List<OrderForR> ordersList;     //待接单的订单合集

    private View mEmptyView;//无数据视图

    //xml文件控件
    private BottomSheetLayout bottomSheetLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EmptyRecyclerView recyclerView;
    private Activity mActivity;


    private String rider_id;

    private Adapter_OrdersNew adapter;


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
        View view = inflater.inflate(R.layout.fragment_rider_new, container, false);
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

        recyclerView = getActivity().findViewById(R.id.NewRecyclerView);

    }

    private void showList() {

        SharedPreferences preferences = getActivity().getSharedPreferences("LoginInfo", Activity.MODE_PRIVATE);
        rider_id = preferences.getString("userId","");

        pg.setMessage("数据加载中...");
        pg.show();
        if (Activity_Rider_Main.networkState == 0) {
            Toast.makeText(getActivity(), "网络连接失败，请检查网络连接设置！", Toast.LENGTH_SHORT).show();
            pg.dismiss();
            return;
        }

        new Thread() {
            public void run() {
                try {
                    ordersList = RiderRequestUtility.getNewOrdersForR();
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
                adapter = new Adapter_OrdersNew(ordersList, Fragment_Rider_New.this, getActivity());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                adapter.buttonSetOnclick(new Adapter_OrdersNew.ButtonInterface() {
                    @Override
                    public void onclick(View view, int position) {
                        //获取被点击接单的订单id
                        String order_id = String.valueOf(ordersList.get(position).getOrder_id());

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("确认接单？");
                        builder.setTitle("提示");
                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() { @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Integer res = RiderRequestUtility.AcceptOrder(rider_id,order_id);//后台把这个order和rider给绑了
                            Integer res2 = RiderRequestUtility.ChangeOrderStateForR(order_id,"raccept");//并且更改订单状态
                            dialog.dismiss();
                        }});
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                        { @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                        });
                        builder.create().show();
                    }
                });
                recyclerView.setEmptyView(mEmptyView);
            }
        }
    };

    /**
     * 下拉刷新事件
     */
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
        OrderForR temp = ordersList.get(id);
        if(temp != null){
            temp.setOrder_state(state);
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

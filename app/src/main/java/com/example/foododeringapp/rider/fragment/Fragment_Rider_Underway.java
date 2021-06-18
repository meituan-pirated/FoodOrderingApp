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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.OrderForR;
import com.example.foododeringapp.rider.Activity_Order_details;
import com.example.foododeringapp.rider.Activity_Rider_Main;
import com.example.foododeringapp.rider.adapter.Adapter_OrdersUnderway;
import com.example.foododeringapp.rider.service.RiderRequestUtility;
import com.example.foododeringapp.widget.EmptyRecyclerView;
import com.example.foododeringapp.rider.service.RiderRequestUtility;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.List;

public class Fragment_Rider_Underway extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private BottomSheetLayout bottomSheetLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EmptyRecyclerView recyclerView;
    private View mEmptyView;//无数据视图

    private List<OrderForR> ordersList;     //待处理的订单合集

    private String rider_id; //骑手的信息
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
        initView();
        pg = new ProgressDialog(getActivity());
        showList();

    }

    private void initView() {
        SharedPreferences preferences = getActivity().getSharedPreferences("LoginInfo", Activity.MODE_PRIVATE);
        rider_id = preferences.getString("userId","");
        bottomSheetLayout =  getActivity().findViewById(R.id.bottom_sheet_layout);
        //刷新控件
        swipeRefreshLayout = getActivity().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = getActivity().findViewById(R.id.UnderwayRecyclerView);

    }

    private Adapter_OrdersUnderway.OnItemClickListener  MyItemClickListener = new Adapter_OrdersUnderway.OnItemClickListener () {
        @Override
        public void onItemClick(View v,int orderID) {
            // 跳转到订单信息页。依据id获取订单信息
            //真正需要重写方法的地方
            Log.i("被选中的order_id",String.valueOf(orderID));
            Intent intent2 = new Intent(getActivity(), Activity_Order_details.class);//跳转到订单详细页面
            Bundle bundle = new Bundle();
            bundle.putString("orderID",String.valueOf(orderID));
            intent2.putExtras(bundle);
            getActivity().startActivityForResult(intent2,3111);
        }

        //假设须要刷新某个特定界面。则參数中包括position
        @Override
        public void onStatusChange(View v,final OrderForR order) {
            //更新订单的状态啦
            //真正需要重写方法的地方
            Log.i("外部","!!!!!");
            String state = order.getOrder_state();
            String orderID = String.valueOf(order.getOrder_id());
            if(state.equals("raccept")){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确认已取餐？");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() { @Override
                public void onClick(DialogInterface dialog, int which) {
                    Integer res = RiderRequestUtility.ChangeOrderStateForR(orderID,"at_res");
                    Button state = v.findViewById(R.id.btn_state);
                    state.setText(R.string.arrive);
                    dialog.dismiss();
                }});
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                { @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
                });
                builder.create().show();

            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确认已送达？");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() { @Override
                public void onClick(DialogInterface dialog, int which) {
                    Integer res = RiderRequestUtility.ChangeOrderStateForR(orderID,"arrive");
                    res = RiderRequestUtility.MarkOrderArriveTime(orderID);
                    dialog.dismiss();
                    //如果记得最好刷新下页面
                }});
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                { @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
                });
                builder.create().show();
            }


        }

        @Override
        /**
         * 拨打电话（直接拨打电话）
         * @param phoneNum 电话号码
         */
        public void onPhoneContact(View v,OrderForR order) {
            //调用方法获得电话
            String phoneNum = order.getAddress().getReceivePhone();
            //根据电话拨号！
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + phoneNum);
            intent.setData(data);
            startActivity(intent);//暂时不懂怎么用
//               还有拨打电话的权限 <uses-permission android:name="android.permission.CALL_PHONE" />

        }


    };

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
                    ordersList = RiderRequestUtility.getUnderwayOrdersForR(rider_id);   //获取数据库里本外卖员接的订单
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
                adapter.setOnItemClickListener(MyItemClickListener);
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

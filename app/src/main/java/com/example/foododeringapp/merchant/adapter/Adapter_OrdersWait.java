package com.example.foododeringapp.merchant.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Order;
import com.example.foododeringapp.merchant.fragment.Fragment_Merchant_Wait;
import com.example.foododeringapp.widget.EmptyRecyclerView;

import java.text.NumberFormat;
import java.util.List;

/**
 * fragment_merchant_wait.xml中的RecyclerView处理类
 * RecyclerView对应的布局文件为item_orders_wait
 */
public class Adapter_OrdersWait extends RecyclerView.Adapter<Adapter_OrdersWait.ViewHolder> {
    private View mEmptyView;//无数据视图
    // 数据属性
    private Fragment_Merchant_Wait mContext;
    private Context fragment_context;
    private List<Order> mOrderWaitList;
    private NumberFormat nf;
    private int orderListNum;
    private Adapter_OrderDetails adapter;

    //item_orders_wait.xml界面组件


    public Adapter_OrdersWait(List<Order> mOrderWaitList, Fragment_Merchant_Wait mContext, Context context) {
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        this.mContext = mContext;
        this.fragment_context = context;
        this.mOrderWaitList = mOrderWaitList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders_wait, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.btn_accept.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Fragment_Merchant_Wait fragment = mContext;
                int position = holder.getAdapterPosition();
                Order order = mOrderWaitList.get(position);
                fragment.changeOrderStateById(order.getOrder_id(),"ING");

            }
        });

        holder.btn_refuse.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Fragment_Merchant_Wait fragment = mContext;
               int position = holder.getAdapterPosition();
               Order order = mOrderWaitList.get(position);
               fragment.changeOrderStateById(order.getOrder_id(), "CANCEL");


            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ordersWait_item_view.setTag(orderListNum);
        if(orderListNum < 100){
            orderListNum ++;
        }
        else{
            orderListNum = 0;
        }
        final Order order = mOrderWaitList.get(position);
        holder.ordersWaitList_num.setText(orderListNum+"");
        Log.i("orderNote:", order.getOrderNote()+"");
        holder.ordersWait_Note.setText(order.getOrderNote());

        holder.ordersWait_ReceiveName.setText(order.getAddress().getReceiveName());
        holder.ordersWait_ReceivePhone.setText(order.getAddress().getReceivePhone());
        holder.ordersWait_AddressName.setText(order.getAddress().getAddressName());

        //给订单详情创建RecyclerView的adapter
        Log.i("details-product-name:",order.getOrderDetailsList().get(position).getProduct().getProductName());
        adapter = new Adapter_OrderDetails(order.getOrderDetailsList(), mContext, position);
        LinearLayoutManager layoutManager = new LinearLayoutManager(fragment_context);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setEmptyView(mEmptyView);

        holder.ordersWait_Amount.setText(order.getOrderPrice());
    }

    @Override
    public int getItemCount() {
        return mOrderWaitList.size();
    }

    /**
     * 将item_orders_wait中的组件封装在一个类中（说法不太准确，大概这么个意思）
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        private List<Order> ordersList;     //待处理的订单合集

        //item_orders_wait.xml的组件
        View ordersWait_item_view;
        TextView ordersWaitList_num,ordersWait_Note;
        TextView ordersWait_ReceiveName,ordersWait_ReceivePhone,ordersWait_AddressName;
        EmptyRecyclerView recyclerView;
        TextView ordersWait_Amount;
        Button btn_accept, btn_refuse;



        public ViewHolder(View itemView) {
            super(itemView);
            ordersWait_item_view = itemView.findViewById(R.id.ordersWait_item_view);
            ordersWaitList_num = itemView.findViewById(R.id.ordersWaitList_num);
            ordersWait_Note = itemView.findViewById(R.id.ordersWait_Note);
            ordersWait_ReceiveName = itemView.findViewById(R.id.ordersWait_ReceiveName);
            ordersWait_ReceivePhone = itemView.findViewById(R.id.ordersWait_ReceivePhone);
            ordersWait_AddressName = itemView.findViewById(R.id.ordersWait_AddressName);

            recyclerView = itemView.findViewById(R.id.ordersWait_details);

            ordersWait_Amount = itemView.findViewById(R.id.ordersWait_Amount);

            btn_refuse = itemView.findViewById(R.id.btn_refuse);
            btn_accept = itemView.findViewById(R.id.btn_accept);
        }
    }
}

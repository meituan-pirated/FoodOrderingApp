package com.example.foododeringapp.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.UserOrder;
import com.example.foododeringapp.user.fragment.Fragment_User_Order;

import java.text.NumberFormat;
import java.util.List;

/**
 * fragment_User_Order.xml中的RecyclerView处理类
 * RecyclerView对应的布局文件为item_allorders.xml
 */
//!--    order_id order_note order_state
//        order_address order表中没有，需要向address表根据address_id-->

public class Adapter_User_allOrders  extends RecyclerView.Adapter<Adapter_User_allOrders.ViewHolder>{
    private View mEmptyView;//无数据视图
    // 数据属性
    private Fragment_User_Order mContext;
    private Context fragment_context;
    private List<UserOrder> allOrderList;
    private NumberFormat nf;
    private int orderListNum;
    private Adapter_User_OrderDetails adapter;

    //item_orders_wait.xml界面组件


    public Adapter_User_allOrders(List<UserOrder> allOrderList, Fragment_User_Order mContext, Context context) {
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        this.mContext = mContext;
        this.fragment_context = context;
        this.allOrderList = allOrderList;
    }


    @NonNull
    @Override
    public Adapter_User_allOrders.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allorders, parent, false);
        final Adapter_User_allOrders.ViewHolder holder = new Adapter_User_allOrders.ViewHolder(view);
        return holder;
    }

    //!--    order_id order_note order_state
//        order_address order表中没有，需要向address表根据address_id-->
    @Override
    public void onBindViewHolder(@NonNull Adapter_User_allOrders.ViewHolder holder, int position) {
        final UserOrder uorder = allOrderList.get(position);
        holder.order_address.setText(uorder.getOrder_address());
        holder.order_id.setText(uorder.getOrder_id());
        holder.order_note.setText(uorder.getOrder_note());
        holder.order_state.setText(uorder.getOrder_state());
    }

    @Override
    public int getItemCount() {
        return allOrderList.size();
    }

//    <!--    order_id order_note order_state user_order_list(LinearLayout)
//    order_address order表中没有，需要向address表根据address_id-->
    static class ViewHolder extends RecyclerView.ViewHolder {
        private List<UserOrder> ordersList;     //待处理的订单合集

        View user_order_list;
        TextView order_id, order_note, order_state, order_address;

        public ViewHolder(View itemView) {
            super(itemView);
            user_order_list = itemView.findViewById(R.id.user_order_list);
            order_id = itemView.findViewById(R.id.order_id);
            order_note = itemView.findViewById(R.id.order_note);
            order_state = itemView.findViewById(R.id.order_state);
            order_address = itemView.findViewById(R.id.order_address);
        }
    }
}

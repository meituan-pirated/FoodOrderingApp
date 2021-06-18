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
import com.example.foododeringapp.bean.OrderIngForM;
import com.example.foododeringapp.merchant.fragment.Fragment_Merchant_Order_Ing2;
import com.example.foododeringapp.widget.EmptyRecyclerView;

import java.util.List;

public class Adapter_OrdersIng  extends RecyclerView.Adapter<Adapter_OrdersIng.ViewHolder> {
    private View mEmptyView;//无数据视图

    private List<OrderIngForM> mOrderIngList;
    private Context activity_context;
    private Fragment_Merchant_Order_Ing2 fragment_context2;


    public Adapter_OrdersIng(List<OrderIngForM> mOrderIngList, Context activity_context, Fragment_Merchant_Order_Ing2 fragment_context) {
        this.mOrderIngList = mOrderIngList;
        this.activity_context = activity_context;
        this.fragment_context2 = fragment_context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("ing","createViewHodler");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_morder_ing, parent, false);
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_morder_ing, null);
        final Adapter_OrdersIng.ViewHolder holder = new Adapter_OrdersIng.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i("ing","bindViewHodler");
        final OrderIngForM order = mOrderIngList.get(position);
        holder.order_state.setText(order.getOrderState());
        if(order.getRider() != null){
            holder.rider_name.setText(order.getRider().getRider_name());
            holder.rider_phone.setText(order.getRider().getRider_phone());
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity_context){
            //            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }

        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.ordersIng_details.setLayoutManager(layoutManager);

        Adapter_OrderDetails adapter = new Adapter_OrderDetails(order.getOrderDetailsList());
        holder.ordersIng_details.setAdapter(adapter);
        holder.ordersIng_details.setEmptyView(mEmptyView);

        //下面两句是防止刷新内部的recyclerView导致外部recyclerView也发生滑动
        holder.ordersIng_details.setFocusableInTouchMode(false);
        holder.ordersIng_details.requestFocus();
    }

    @Override
    public int getItemCount() {
        return mOrderIngList.size();
    }

    /**
     * 将item_orders_wait中的组件封装在一个类中
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
//        private List<Order> ordersList;     //待处理的订单合集

        TextView order_ing_num,order_state,rider_name, rider_phone;
        EmptyRecyclerView ordersIng_details;
        Button btn_rGet;



        public ViewHolder(View itemView) {
            super(itemView);

            order_ing_num = itemView.findViewById(R.id.order_ing_num);
            order_state = itemView.findViewById(R.id.order_state);
            rider_name = itemView.findViewById(R.id.rider_name);
            rider_phone = itemView.findViewById(R.id.rider_phone);
            ordersIng_details = itemView.findViewById(R.id.ordersIng_details);
            btn_rGet = itemView.findViewById(R.id.btn_rGet);


        }
    }
}

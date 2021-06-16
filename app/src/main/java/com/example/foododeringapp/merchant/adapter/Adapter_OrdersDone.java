package com.example.foododeringapp.merchant.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.OrderDoneBriefForM;
import com.example.foododeringapp.merchant.Activity_Merchant_OrderDoneInfo;
import com.example.foododeringapp.merchant.fragment.Fragment_Merchant_Order_Done2;

import java.util.List;

public class Adapter_OrdersDone extends RecyclerView.Adapter<Adapter_OrdersDone.ViewHolder> implements View.OnClickListener{
    private View mEmptyView;//无数据视图

    private List<OrderDoneBriefForM> mOrderDoneList;
    private Context activity_context;
    private Fragment_Merchant_Order_Done2 fragment_context2;
    private OnItemClickListener mOnItemClickListener;

    public Adapter_OrdersDone(List<OrderDoneBriefForM> mOrderDoneList, Context activity_context, Fragment_Merchant_Order_Done2 fragment_context) {
        this.mOrderDoneList = mOrderDoneList;
        this.activity_context = activity_context;
        this.fragment_context2 = fragment_context;
    }



    /**
     * 定义RecyclerView单机事件的回调接口
     */
    public static interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(Adapter_OrdersDone.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    @NonNull
    @Override
    public Adapter_OrdersDone.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_morder_done, parent, false);
        Log.i("adapter_done", "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_morder_done, parent, false);
        final Adapter_OrdersDone.ViewHolder holder = new Adapter_OrdersDone.ViewHolder(view);
        holder.orderDone_item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("orderDoneAdater","click");
                Fragment_Merchant_Order_Done2 fragment = fragment_context2;
                int position = holder.getAdapterPosition();
                OrderDoneBriefForM order = mOrderDoneList.get(position);
                //调用fragment中的方法，实现数据库的修改
                fragment.jumpToOrderDoneInf(position);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_OrdersDone.ViewHolder holder, int position) {
        final OrderDoneBriefForM orderDoneBriefForM = mOrderDoneList.get(position);
        Log.i("adapter-done-List", mOrderDoneList.size()+"");
        if(orderDoneBriefForM.getBusinessScore() != null){
            holder.business_score.setVisibility(View.VISIBLE);
            holder.business_score.setNumStars(orderDoneBriefForM.getBusinessScore());
            holder.business_score_state.setVisibility(View.GONE);
        }


        Log.i("adap-done-order_price", orderDoneBriefForM.getOrderPrice()+"");
        holder.order_price.setText(orderDoneBriefForM.getOrderPrice()+"");
        holder.amount.setText(orderDoneBriefForM.getTotalAmount()+"");


    }

    @Override
    public int getItemCount() {
        return mOrderDoneList.size();
    }



    /**
     * 将item_orders_wait中的组件封装在一个类中
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
//        private List<Order> ordersList;     //待处理的订单合集
        View orderDone_item_view;

        TextView business_score_state,order_state,order_price, amount;

        RatingBar business_score;


        public ViewHolder(View itemView) {
            super(itemView);
            orderDone_item_view = itemView.findViewById(R.id.orderDone_item_view);
            business_score = itemView.findViewById(R.id.business_score);
            business_score_state = itemView.findViewById(R.id.business_score_state);
            order_state = itemView.findViewById(R.id.order_state);
            order_price = itemView.findViewById(R.id.order_price);
            amount = itemView.findViewById(R.id.amount);



        }
    }

}

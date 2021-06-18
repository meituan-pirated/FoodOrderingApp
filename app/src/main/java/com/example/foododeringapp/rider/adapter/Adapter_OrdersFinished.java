package com.example.foododeringapp.rider.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Order;
import com.example.foododeringapp.bean.OrderForR;
import com.example.foododeringapp.rider.fragment.Fragment_Rider_New;
import com.example.foododeringapp.rider.fragment.Fragment_Rider_Sta;

import java.text.NumberFormat;
import java.util.List;

public class Adapter_OrdersFinished extends RecyclerView.Adapter<Adapter_OrdersFinished.MyViewHolder>{

    private View mEmptyView;//无数据视图
    // 数据属性
    private Fragment_Rider_Sta rContext;
    private Context fragment_context;
    private List<OrderForR> rOrderList;
    private NumberFormat nf;


    public Adapter_OrdersFinished(List<OrderForR> list, Fragment_Rider_Sta rContext, Context context) {
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        this.rContext = rContext;
        this.fragment_context = context;
        this.rOrderList = list;
        Log.i("list传进来了，长度为",String.valueOf(list.size()));
    }

    @NonNull
    @Override
    public Adapter_OrdersFinished.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(this.fragment_context).
                inflate(R.layout.item_rorder_finished, parent, false);
        return new Adapter_OrdersFinished.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_OrdersFinished.MyViewHolder holder, int position) {
        OrderForR order = rOrderList.get(position);
        Log.i("我在bind了",order.getBusinessInfo().getBusinessName());
        holder.merchant_name.setText(order.getBusinessInfo().getBusinessName());
        holder.order_id.setText(String.valueOf(order.getOrder_id()));
        Integer s = order.getRiderScore();
        //判断是否被打分，打分则显示星星，没有就显示“用户未评价
        if(s != null){
            holder.score.setRating(s);
        }else{
            holder.score.setVisibility(View.GONE);
            holder.no_comment.setVisibility(View.VISIBLE);
        }
        holder.arrive_time.setText(order.getArriveTime());

    }

    @Override
    public int getItemCount() {
        return rOrderList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        private List<Order> ordersList;     //待处理的订单合集

        //item_orders_underway.xml的组件
        TextView merchant_name, order_id, no_comment,arrive_time;
        RatingBar score;
        LinearLayout relative_container;

        public MyViewHolder(View itemView){
            super(itemView);

            merchant_name = (TextView)itemView.findViewById(R.id.merchant_name);
            order_id = (TextView)itemView.findViewById(R.id.order_id);
            score = (RatingBar)itemView.findViewById(R.id.score_ratingBar);
            no_comment = (TextView)itemView.findViewById(R.id.no_comment);
            arrive_time = (TextView)itemView.findViewById(R.id.arrive_time);


        }

        /*public void bind(OrderForR order) {
            //根据订单内容，设定控件显示的文本
            Log.i("我在bind了",order.getBusinessInfo().getBusinessName());
            merchant_name.setText(order.getBusinessInfo().getBusinessName());
            order_id.setText(order.getOrder_id());
            Integer s = order.getRiderScore();
            //判断是否被打分，打分则显示星星，没有就显示“用户未评价
            if(s != null){
                score.setRating(s);
            }else{
                score.setVisibility(View.GONE);
                no_comment.setVisibility(View.VISIBLE);
            }
            arrive_time.setText(order.getArriveTime());

        }*/
    }



}

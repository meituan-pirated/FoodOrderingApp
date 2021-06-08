package com.example.foododeringapp.rider.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Order;
import com.example.foododeringapp.bean.OrderForR;
import com.example.foododeringapp.rider.fragment.Fragment_Rider_Underway;

import java.text.NumberFormat;
import java.util.List;

public class Adapter_OrdersUnderway extends RecyclerView.Adapter<Adapter_OrdersUnderway.MyViewHolder> {

    private View mEmptyView;//无数据视图
    // 数据属性
    private Fragment_Rider_Underway rContext;
    private Context fragment_context; //最外面的activity
    private List<OrderForR> rOrderList;
    private NumberFormat nf;
    private int orderListNum; //

    private Context mContext;

    public Adapter_OrdersUnderway(Fragment_Rider_Underway rContext, Context context,List<OrderForR> list){
        this.rContext = rContext;
        this.fragment_context = context;
        this.rOrderList = list;
    }


    //先写我们自己定义的view_holder
    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private List<Order> ordersList;     //待处理的订单合集

        //item_orders_underway.xml的组件
        TextView merchant_name, order_id, merchant_add, buyer_add;
        Button btn_state;
        Button btn_contact;
        RelativeLayout relative_container;
        IMyViewHolderClicks mListener;

        //viewholder的构造方法
        public MyViewHolder(View itemView, IMyViewHolderClicks listener) {
            super(itemView);
            merchant_name = itemView.findViewById(R.id.merchant_name);
            order_id = itemView.findViewById(R.id.order_id);
            merchant_add = itemView.findViewById(R.id.merchant_add);
            buyer_add = itemView.findViewById(R.id.buyer_add);

            mListener = listener;
            relative_container = (RelativeLayout)itemView.findViewById(R.id.relative_container);
            btn_state = itemView.findViewById(R.id.btn_state);
            btn_contact = itemView.findViewById(R.id.btn_contact);

            btn_state.setOnClickListener(this);
            btn_contact.setOnClickListener(this);
            relative_container.setOnClickListener(this);

        }

        public void bind(Order order) {
            //判断订单状态来设置按钮显示内容
            if(order.getOrderState().contentEquals("待取餐")){
                btn_state.setText("已取餐");
            }else{
                btn_state.setText("已送达");
            }



            merchant_name.setText(order.getBusiness_id());
            order_id.setText(order.getOrder_id());
            merchant_add.setText("商家地址");
            buyer_add.setText(order.getAddress().getAddressName());
            //将实体绑定到view上面
            btn_state.setTag(order); //把当前订单的信息和按键绑定了
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_state:
                    //如果点击的是状态，可以由显示“已取餐”到“已送达”，若点了已送达那订单就完成了
                    mListener.onStatusChange((Order) btn_state.getTag(),getLayoutPosition());
                    break;
                case R.id.relative_container:
                    //若选中整项则进入订单
                    int chosed_id = ((Order)btn_state.getTag()).getOrder_id();
                    mListener.onItemClick(chosed_id);
                    break;
                case R.id.btn_contact:
                    //若选择拨打电话则拨打电话
                    mListener.onPhoneContact(((Order)btn_state.getTag()).getOrder_id());
                    break;
            }
        }



    }


    //item_orders_wait.xml界面组件

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.item_rorders_underway, viewGroup, false);

        return new MyViewHolder(itemView, new IMyViewHolderClicks() {
            @Override
            public void onItemClick(int uid) {
                // 跳转到订单信息页。依据id获取订单信息
                //真正需要重写方法的地方
            }

            //假设须要刷新某个特定界面。则參数中包括position
            @Override
            public void onStatusChange(final Order order,final int position) {
                //更新订单的状态啦
                //真正需要重写方法的地方
                notifyItemChanged(position);


            }

            @Override
            /**
             * 拨打电话（直接拨打电话）
             * @param phoneNum 电话号码
             */
            public void onPhoneContact(int order_id) {
                //调用方法获得电话
                String phoneNum = "18006986080";
                //根据电话拨号！
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + phoneNum);
                intent.setData(data);
                fragment_context.startActivity(intent);//暂时不懂怎么用
//               还有拨打电话的权限 <uses-permission android:name="android.permission.CALL_PHONE" />

            }


        });
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder,final int position) {
        //会根据具体订单的状态进行数据的填写
        OrderForR order = rOrderList.get(position);
        myViewHolder.btn_state.setTag("取餐按钮");
        myViewHolder.order_id.setText(position);

        //myViewHolder.bind(order);
    }


    private interface IMyViewHolderClicks{
        //单击整个item跳转到订单界面。须要传递订单id
        public void onItemClick(int order_id);
        //状态button,更新button的状态，可以由显示“已取餐”到“已送达”，若点了已送达那订单就完成了

        public void onStatusChange(Order order,int position);

        public void onPhoneContact(int order_id);
    }


    @Override
    public int getItemCount() {
        return rOrderList.size();
    }




/*
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
                fragment.changeOrderStateById(order.getOrderId(), "ING");

            }
        });

        holder.btn_refuse.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Fragment_Merchant_Wait fragment = mContext;
                int position = holder.getAdapterPosition();
                Order order = mOrderWaitList.get(position);
                fragment.changeOrderStateById(order.getOrderId(), "CANCEL");


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
        holder.ordersWaitList_num.setText(orderListNum);
        holder.ordersWait_Note.setText(order.getOrderNote());

        holder.ordersWait_ReceiveName.setText(order.getAddress().getReceiveName());
        holder.ordersWait_ReceivePhone.setText(order.getAddress().getReceivePhone());
        holder.ordersWait_AddressName.setText(order.getAddress().getAddressName());

        //给订单详情创建RecyclerView的adapter
        adapter = new Adapter_OrderDetails(order.getOrderDetails(), mContext, position);
        LinearLayoutManager layoutManager = new LinearLayoutManager(fragment_context);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setEmptyView(mEmptyView);

        holder.ordersWait_Amount.setText(order.getOrderAmount());
    }
*/


    /*
     * 将item_orders_wait中的组件封装在一个类中（说法不太准确，大概这么个意思）
     */


}
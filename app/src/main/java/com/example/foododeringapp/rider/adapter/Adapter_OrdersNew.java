package com.example.foododeringapp.rider.adapter;

import android.content.Context;
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
import com.example.foododeringapp.rider.fragment.Fragment_Rider_New;

import java.text.NumberFormat;
import java.util.List;

public class Adapter_OrdersNew extends RecyclerView.Adapter<Adapter_OrdersNew.MyViewHolder> {

    private View mEmptyView;//无数据视图
    // 数据属性
    private Fragment_Rider_New rContext;
    private Context fragment_context;
    private List<OrderForR> rOrderList;
    private ButtonInterface buttonInterface;
    private NumberFormat nf;
    //private OnItemClickListener mlistener;
    private int orderListNum; //


    public Adapter_OrdersNew(List<OrderForR> list, Fragment_Rider_New rContext, Context context) {
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        this.rContext = rContext;
        this.fragment_context = context;
        this.rOrderList = list;
    }

   /* 在fragment里重写监听器时用的构造方法
   public Adapter_OrdersNew(Context context, OnItemClickListener listener){
        this.fragment_context = context;
        this.mlistener = listener;

    }*/
    public Adapter_OrdersNew(Context context){
        this.fragment_context = context;
    }



    //先写我们自己定义的view_holder
    static class MyViewHolder extends RecyclerView.ViewHolder{
        private List<OrderForR> ordersList;     //待处理的订单合集

        //item_orders_underway.xml的组件
        TextView merchant_name, order_id, merchant_add, buyer_add, income;
        Button btn_accept;
        RelativeLayout relative_container;

        public MyViewHolder(View itemView){
            super(itemView);

            merchant_name = itemView.findViewById(R.id.merchant_name);
            order_id = itemView.findViewById(R.id.order_id);
            merchant_add = itemView.findViewById(R.id.merchant_add);
            buyer_add = itemView.findViewById(R.id.buyer_add);
            income = itemView.findViewById(R.id.income);
            btn_accept = itemView.findViewById(R.id.btn_accept);

        }

        public void bind(OrderForR order) {
            //根据订单内容，设定控件显示的文本

            merchant_name.setText(order.getBusinessInfo().getBusinessName());
            //System.out.println(order.getOrder());
            String oid = "#"+String.valueOf(order.getOrder_id());
            order_id.setText(oid);
            merchant_add.setText(order.getBusinessInfo().getRefectory());
            buyer_add.setText(order.getAddress().getAddressName());

            //将实体绑定到view上面
            //btn_accept.setTag(order); //把当前订单的信息和按键绑定了
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(this.fragment_context).
                inflate(R.layout.item_rorders_new, parent, false);
        return new Adapter_OrdersNew.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Adapter_OrdersNew.MyViewHolder myViewHolder, final int position) {
        //会根据具体订单的状态进行数据的填写
        OrderForR order = rOrderList.get(position);
        System.out.println(order.getOrder_id());
        myViewHolder.bind(order);   //绑定一个recyclerview与订单

        myViewHolder.btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonInterface!=null) {
//                  接口实例化后的而对象，调用重写后的方法
                    buttonInterface.onclick(v,position);
                }

            }
        });

    }
    /*public interface OnItemClickListener{
        void onClick(int pos);
    }*/

    /**
     *按钮点击事件需要的方法
     */
    public void buttonSetOnclick(ButtonInterface buttonInterface){
        this.buttonInterface=buttonInterface;
    }

    /**
     * 按钮点击事件对应的接口
     */
    public interface ButtonInterface{
        public void onclick( View view,int position);
    }


    @Override
    public int getItemCount() {
        //return rOrderList.size();
        return rOrderList.size();
    }
}

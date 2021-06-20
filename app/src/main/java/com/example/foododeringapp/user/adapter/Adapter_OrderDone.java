package com.example.foododeringapp.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Order;
import com.example.foododeringapp.bean.OrderForR;
import com.example.foododeringapp.user.fragment.Fragment_User_Order_Done;
import com.example.foododeringapp.user.fragment.Fragment_User_Order_Ing;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Adapter_OrderDone extends RecyclerView.Adapter<Adapter_OrderDone.MyViewHolder> {

    private View mEmptyView;//无数据视图
    // 数据属性
    private Fragment_User_Order_Done rContext;
    private Context fragment_context; //最外面的activity
    private List<OrderForR> rOrderList;
    private NumberFormat nf;
    private int orderListNum; //

    private Context mContext;

    public Adapter_OrderDone(Fragment_User_Order_Done rContext, Context context,List<OrderForR> list){
        this.rContext = rContext;
        this.fragment_context = context;
        this.rOrderList = list;
    }


    //第一步：自定义一个回调接口来实现各Click事件
    public interface OnItemClickListener {
        //单击整个item跳转到订单界面。须要传递订单id
        public void onItemClick(View v,int order_id);
    }

    public Adapter_OrderDone.OnItemClickListener oneOnItemClickListener;//第二步：声明自定义的接口

    //第三步：定义方法并暴露给外面的调用者
    public void setOnItemClickListener(Adapter_OrderDone.OnItemClickListener listener) {
        this.oneOnItemClickListener = listener;
    }



    //先写我们自己定义的view_holder
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private List<Order> ordersList;     //待处理的订单合集

        //item_orders_underway.xml的组件
        TextView merchant_name, buyer_add, order_time,order_price,arrive_time,tv_evaluate;
        Button btn_evaluate;
        LinearLayout linear_container;


        //viewholder的构造方法
        //public MyViewHolder(View itemView, IMyViewHolderClicks listener) {
        public MyViewHolder(View itemView) {
            super(itemView);
            merchant_name = (TextView)itemView.findViewById(R.id.merchant_name);
            buyer_add = (TextView)itemView.findViewById(R.id.buyer_add);
            order_time = (TextView)itemView.findViewById(R.id.order_time);

            arrive_time= (TextView)itemView.findViewById(R.id.arrive_time123);
            tv_evaluate = (TextView)itemView.findViewById(R.id.tv_if_evaluated);

            order_price = (TextView)itemView.findViewById(R.id.order_price);
            btn_evaluate = (Button) itemView.findViewById(R.id.btn_evaluate);
            linear_container = (LinearLayout)itemView.findViewById(R.id.linear_container);

            linear_container.setOnClickListener(this);

        }

        //viewHolder的bind方法
        public void bind(OrderForR order) throws ParseException {
            //判断订单状态来设置按钮显示内容
            merchant_name.setText(order.getBusinessInfo().getBusinessName());
            order_time.setText(order.getOrder_time());
            buyer_add.setText(order.getAddress().getAddressName());
            order_price.setText("共"+order.getOrder_price()+"￥");
            String originalString =order.getArrive_time();
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
            String newString = new SimpleDateFormat("HH:mm").format(date);
            arrive_time.setText(newString);

            if(order.getRider_score()!=null){
                //已评价
                tv_evaluate.setVisibility(View.VISIBLE);
                btn_evaluate.setVisibility(View.GONE);
            }else{
                //将实体绑定到view上面
                btn_evaluate.setTag(order); //把当前订单的信息和布局绑定了
            }
        }

        @Override
        public void onClick(View v) {
            //oneOnItemClickListener.onStatusChange(v,(OrderForR) btn_state.getTag());
            switch (v.getId()){
                case R.id.btn_evaluate:
                    //若选中评价则去评价
                    int chosed_id = ((OrderForR)btn_evaluate.getTag()).getOrder_id();
                    oneOnItemClickListener.onItemClick(v,chosed_id);
                    break;
            }
        }

    }


    @NonNull
    @Override
    public Adapter_OrderDone.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.item_uorder_done, viewGroup, false);
        return new Adapter_OrderDone.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Adapter_OrderDone.MyViewHolder myViewHolder, final int position) {
        //会根据具体订单的状态进行数据的填写
        OrderForR order = rOrderList.get(position);
        try {
            myViewHolder.bind(order);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



    @Override
    public int getItemCount() {
        return rOrderList.size();
    }

}



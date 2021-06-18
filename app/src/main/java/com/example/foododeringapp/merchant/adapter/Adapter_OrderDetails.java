package com.example.foododeringapp.merchant.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Order;
import com.example.foododeringapp.bean.OrderDetails;
import com.example.foododeringapp.bean.Products;
import com.example.foododeringapp.merchant.fragment.Fragment_Merchant_Wait;

import java.text.NumberFormat;
import java.util.List;

public class Adapter_OrderDetails extends RecyclerView.Adapter<Adapter_OrderDetails.ViewHolder>{
    private List<OrderDetails> mOrderDetailList;
    private LayoutInflater mInflater;
//    private Fragment_Merchant_Wait mContext;
//    private int mPosition;
    private NumberFormat nf;



    public Adapter_OrderDetails(List<OrderDetails> mOrderDetailList) {
        this.mOrderDetailList = mOrderDetailList;
//        this.mPosition = position;
//        this.mContext = context;
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
    }


    @NonNull
    @Override
    public Adapter_OrderDetails.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_details, parent, false);
//        View view = mInflater.inflate(R.layout.item_order_details, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_OrderDetails.ViewHolder holder, int position) {
        final OrderDetails orderDetails = mOrderDetailList.get(position);
        Log.i("details-product",orderDetails.getProduct().getProductName());

        Log.i("position",position+"");
        Products products = orderDetails.getProduct();
        holder.orderDetails_name.setText(products.getProductName());
        holder.orderDetails_num.setText(orderDetails.getAmount()+"");
        holder.orderDetails_salePrice.setText(products.getSalePrice()+"");
//        holder.orderDetails_deliveryPrice.setText(products.getDeliveryPrice()+"");
//        holder.orderDetails_sum.setText(((products.getSalePrice() + products.getDeliveryPrice())*orderDetails.getAmount())+"");
        holder.orderDetails_sum.setText(((products.getSalePrice())*orderDetails.getAmount())+"");

    }

    @Override
    public int getItemCount() {
        return mOrderDetailList.size();
    }

    /**
     * 将item_orders_details中的组件封装在一个类中
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderDetails_name, orderDetails_num,orderDetails_salePrice, orderDetails_deliveryPrice, orderDetails_sum;
        public ViewHolder(View itemView) {
            super(itemView);
            orderDetails_name = itemView.findViewById(R.id.orderDetails_name);
            orderDetails_num = itemView.findViewById(R.id.orderDetails_num);
            orderDetails_salePrice = itemView.findViewById(R.id.orderDetails_salePrice);
//            orderDetails_deliveryPrice = itemView.findViewById(R.id.orderDetails_deliveryPrice);
            orderDetails_sum = itemView.findViewById(R.id.orderDetails_sum);
        }
    }
}

package com.example.foododeringapp.merchant.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.ProductBriefForM;
import com.example.foododeringapp.merchant.Activity_Merchant_ProductInfoList;
import com.example.foododeringapp.merchant.Activity_Merchant_changeProductInfo;

import java.util.List;

public class Adapter_ProductBriefForM extends RecyclerView.Adapter<Adapter_ProductBriefForM.ViewHolder> implements View.OnClickListener{

    private Adapter_ProductBriefForM.OnItemClickListener mOnItemClickListener = null;

    private List<ProductBriefForM> productBriefForMList;
    private Activity_Merchant_ProductInfoList context;

    public Adapter_ProductBriefForM(List<ProductBriefForM> productBriefForMList, Activity_Merchant_ProductInfoList context) {
        this.productBriefForMList = productBriefForMList;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter_ProductBriefForM.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mproduct_brief, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.btn_product_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ProductBriefForM productBriefForM = productBriefForMList.get(position);
                Log.i("product_id", productBriefForM.getProduct_id()+"");
                context.deleteProductByProductId(productBriefForM.getProduct_id());

            }
        });

        holder.btn_product_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ProductBriefForM productBriefForM = productBriefForMList.get(position);
                Log.i("id", productBriefForM.getProduct_id()+"");
                Intent intent = new Intent(context, Activity_Merchant_changeProductInfo.class);
                Bundle bundle = new Bundle();
                bundle.putInt("product_id", productBriefForM.getProduct_id());
                intent.putExtras(bundle);
                context.startActivityForResult(intent, 303);
            }
        });

        //设置监听器
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_ProductBriefForM.ViewHolder holder, int position) {
//        holder.productBrief_item_view.setTag(productBriefForMList.get(position).getProduct_id());
        holder.productBrief_item_view.setTag(position);
        final ProductBriefForM productBriefForM = productBriefForMList.get(position);
//        holder.product_image.
        holder.product_name.setText(productBriefForM.getProductName());
//        Log.i("productSale",productBriefForM.getSalePrice()+"");
        holder.product_sale_price.setText(productBriefForM.getSalePrice()+"");
    }

    @Override
    public int getItemCount() {
        return productBriefForMList.size();
    }

    /**
     * 定义RecyclerView单机事件的回调接口
     */
    public static interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(Adapter_ProductBriefForM.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }

    }


    /**
     * 将item_mproduct_brief中的组件封装在一个类中
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        View productBrief_item_view;
        ImageView product_image;
        TextView product_name, product_sale_price;
        Button btn_product_delete, btn_product_edit;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productBrief_item_view = itemView.findViewById(R.id.productBrief_item_view);
            product_image = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            product_sale_price = itemView.findViewById(R.id.product_sale_price);
            btn_product_delete = itemView.findViewById(R.id.btn_product_delete);
            btn_product_edit = itemView.findViewById(R.id.btn_product_edit);
        }
    }
}

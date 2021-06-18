package com.example.foododeringapp.user.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Merchant;
import com.example.foododeringapp.user.Activity_User_Foodlist;

import com.example.foododeringapp.user.fragment.Fragment_User_First;
import com.example.foododeringapp.widget.EmptyRecyclerView;
import java.text.NumberFormat;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_User_allMerchants extends RecyclerView.Adapter<Adapter_User_allMerchants.ViewHolder>  {

    private Fragment_User_First mContext;
    private Context context;
    private List<Merchant> merchantList;
    private Integer userID;

    private NumberFormat nf;

    public Adapter_User_allMerchants(List<Merchant> merchantList, Fragment_User_First mContext, Context context, Integer userID) {
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        this.mContext = mContext;
        this.context = context;
        this.merchantList = merchantList;
        this.userID = userID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_merchants, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_User_allMerchants.ViewHolder holder, int position) {
        final Merchant merchant = merchantList.get(position);
        holder.merchant_address.setText(merchant.getAddress());
        //holder.merchant_avatar.set(merchant.getAvatar());//图片是什么数据类型
        holder.merchant_name.setText(merchant.getName());
        holder.merchant_phone.setText(merchant.getPhoneNumber());
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            int position = holder.getAdapterPosition();
            Merchant merchant = merchantList.get(position);
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getActivity(), Activity_User_Foodlist.class);
                intent.putExtra("merchantID", merchant.getId());
                intent.putExtra("userID", userID);
                // Log.i("mercahntID", merchant.getId().toString());
                // mContext.startActivityForResult(intent,101);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return merchantList.size();
        //return 10;
    }

    /**
     * 将item_user_merchants中的组件封装在一个类中
     */
    //merchant_avatar 头像 merchant_name 姓名 merchant_phone 电话
    //merchant_address 地址
    static class ViewHolder extends RecyclerView.ViewHolder {
        private List<Merchant> merchantList;     //待处理的订单合集

        View item_user_merchants_view;
        TextView merchant_name, merchant_phone, merchant_address;
        CircleImageView merchant_avatar;

        public ViewHolder(View itemView) {
            super(itemView);

            item_user_merchants_view = itemView.findViewById(R.id.item_user_merchants_view);
            merchant_name = itemView.findViewById(R.id.merchant_name);
            merchant_phone = itemView.findViewById(R.id.merchant_phone);
            merchant_address = itemView.findViewById(R.id.merchant_address);
            merchant_avatar = itemView.findViewById(R.id.merchant_avatar);
        }
    }

}

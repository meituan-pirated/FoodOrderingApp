package com.example.foododeringapp.user.adapter;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Address;
import com.example.foododeringapp.bean.Merchant;
import com.example.foododeringapp.user.Activity_Address_Manage;
import com.example.foododeringapp.user.Activity_Change_Address;
import com.example.foododeringapp.user.Activity_User_Balance;
import com.example.foododeringapp.user.Activity_User_Foodlist;
import com.example.foododeringapp.user.fragment.Fragment_User_First;
import com.example.foododeringapp.user.service.UserRequestUtility;

import java.util.List;

public class Adapter_Address_Manage  extends RecyclerView.Adapter<Adapter_Address_Manage.ViewHolder>{
    private final List<Address> dataList;
    private final LayoutInflater mInflater;
    private Activity_Address_Manage mContext;
    private final int userID;



    public Adapter_Address_Manage(Activity_Address_Manage mContext, List<Address> dataList, int userID){
        this.dataList = dataList;
        this.mContext = mContext;
        this.userID = userID;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public Adapter_Address_Manage.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_user_address_manage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Address_Manage.ViewHolder holder, int position) {
        Address address = dataList.get(position);
        int addressID = address.getAddressId();
        holder.address_delete.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
            dialog.setTitle("提示？");
            dialog.setMessage("您确定要清空购物车中所有商品？");
            dialog.setCancelable(false);
            dialog.setPositiveButton("确定", (dialog1, which) -> {
                UserRequestUtility.deleteUserAddress(addressID);
            });
            dialog.setNegativeButton("取消", (dialog12, which) -> {
            });
            dialog.show();
        });
        holder.address_edit.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, Activity_Change_Address.class);
            intent.putExtra("userID", userID);
            intent.putExtra("addressID", addressID);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (dataList.size() != 0) {
            return dataList.size();
        }else{
            return 0;
        }

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView address_nametv, address_phonetv, address_addresstv;
        Button address_delete, address_edit;
//      ImageButton btn_delete, btn_edit;

        public ViewHolder(View itemView) {
            super(itemView);
            address_nametv = itemView.findViewById(R.id.address_nametv);
            address_phonetv = itemView.findViewById(R.id.address_phonetv);
            address_addresstv = itemView.findViewById(R.id.address_addresstv);
            address_delete = itemView.findViewById(R.id.address_delete);
            address_edit = itemView.findViewById(R.id.address_edit);
        }
    }
}

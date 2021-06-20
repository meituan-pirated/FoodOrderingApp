package com.example.foododeringapp.user.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.foododeringapp.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foododeringapp.bean.Address;

import java.util.List;

public class Adapter_User_Choosing_Address extends RecyclerView.Adapter<Adapter_User_Choosing_Address.ViewHolder> implements View.OnClickListener {
//    address_name address_phone address_address btn_delete btn_edit
    private List<Address> addressList;
    private OnItemClickListener mOnItemClickListener = null;

    public Adapter_User_Choosing_Address(List<Address> addressList){
        this.addressList = addressList;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @NonNull
    @Override
    public Adapter_User_Choosing_Address.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_addressmanage, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);      //为每个item添加点击事件
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_User_Choosing_Address.ViewHolder holder, int position) {
        //将position保存在itemView的Tag中，以便点击时进行获取

        holder.itemView.setTag(position);
        Address address = addressList.get(position);
        // System.out.print(address.getAddressName());
        holder.address_name.setText(address.getReceiveName());
        holder.address_phone.setText(String.valueOf(address.getReceivePhone()));
        holder.address_address.setText(address.getAddressName());
    }

    @Override
    public int getItemCount() {
        if(addressList.size() != 0){
            return addressList.size();
        }else {
            return 0;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
//      address_name address_phone address_address btn_delete btn_edit
        TextView address_name, address_phone, address_address;
//      ImageButton btn_delete, btn_edit;

        public ViewHolder(View itemView) {
            super(itemView);
            address_name = itemView.findViewById(R.id.address_name);
            address_phone = itemView.findViewById(R.id.address_phone);
            address_address = itemView.findViewById(R.id.address_address);
        }
    }
}

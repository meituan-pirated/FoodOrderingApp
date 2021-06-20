package com.example.foododeringapp.user.adapter;

import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foododeringapp.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foododeringapp.bean.Foods;
import com.example.foododeringapp.user.Activity_User_Foodlist;

import java.text.NumberFormat;
import java.util.ArrayList;

public class Adapter_User_SelectFoodList extends RecyclerView.Adapter<Adapter_User_SelectFoodList.ViewHolder> {
    private Activity_User_Foodlist activity;
    private SparseArray<Foods> dataList;
    private NumberFormat nf;
    private LayoutInflater mInflater;

    public Adapter_User_SelectFoodList(Activity_User_Foodlist activity, SparseArray<Foods> dataList) {
        this.activity = activity;
        this.dataList = dataList;
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        mInflater = LayoutInflater.from(activity.getApplicationContext());
    }

    @NonNull
    @Override
    public Adapter_User_SelectFoodList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_user_selected_foods, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_User_SelectFoodList.ViewHolder holder, int position) {
        Foods item = dataList.valueAt(position);
        System.out.println(item.getCount());
        holder.tvName.setText(item.getName());
        holder.tvCost.setText(nf.format(item.getPrice()));
        String count = String.valueOf(item.getCount());
        holder.tvCount.setText(count);
    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private Foods item;
        private TextView tvCost, tvCount, tvAdd, tvMinus, tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCost = itemView.findViewById(R.id.tvCost);
            tvCount = itemView.findViewById(R.id.tvCount);
//            tvMinus = itemView.findViewById(R.id.tvMinus);
//            tvAdd = itemView.findViewById(R.id.tvAdd);
//            tvMinus.setOnClickListener(this);
//            tvAdd.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.tvAdd:
//                    activity.add(item, true);
//                    break;
//                case R.id.tvMinus:
//                    activity.remove(item, true);
//                    break;
//                default:
//                    break;
//            }
//        }
    }
}

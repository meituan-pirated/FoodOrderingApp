package com.example.foododeringapp.user.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foododeringapp.R;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foododeringapp.bean.Foods;
import com.example.foododeringapp.user.Activity_User_Balance;

import java.text.NumberFormat;
import java.util.ArrayList;

public class Adapter_User_Balance extends RecyclerView.Adapter<Adapter_User_Balance.ViewHolder> {
//    tvName tvCost tvMinus tvCount tvAdd
    private final ArrayList<Foods> dataList;
    private final NumberFormat nf;
    private final LayoutInflater mInflater;

    public Adapter_User_Balance(Activity_User_Balance activity, ArrayList<Foods> dataList){
        this.dataList = dataList;
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        mInflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public Adapter_User_Balance.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_user_selected_foodlist, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull Adapter_User_Balance.ViewHolder holder, int position) {
        Foods item = dataList.get(position);
        holder.tvName.setText(item.getName());
        holder.tvCost.setText(nf.format(item.getPrice()));
        String count = "×"+ item.count;
        holder.tvCount.setText(count);
    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCost;
        private final TextView tvCount;
        private final TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCost = itemView.findViewById(R.id.tvCost);
            tvCount = itemView.findViewById(R.id.count);
        }

//        @RequiresApi(api = Build.VERSION_CODES.N)
//        public void bindData(Foods item) {
//            tvName.setText(item.getName());
//            tvCost.setText(nf.format(item.getPrice()));
//            tvCount.setText("×"+ item.count);
//        }
    }
}

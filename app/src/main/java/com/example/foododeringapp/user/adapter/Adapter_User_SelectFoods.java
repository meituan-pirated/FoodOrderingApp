package com.example.foododeringapp.user.adapter;

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

public class Adapter_User_SelectFoods extends RecyclerView.Adapter<Adapter_User_SelectFoods.ViewHolder>  {
    private Activity_User_Foodlist activity;    // 显示foodlist的activity
    private SparseArray<Foods> dataList;
    private NumberFormat nf;
    private LayoutInflater mInflater;

    public Adapter_User_SelectFoods(Activity_User_Foodlist activity, SparseArray<Foods> dataList) {
        this.activity = activity;
        this.dataList = dataList;
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        mInflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public Adapter_User_SelectFoods.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_user_selected_foods, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_User_SelectFoods.ViewHolder holder, int position) {
        Foods item = dataList.valueAt(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Foods item;
        private TextView tvAdd, tvCount, tvMinus, tvName, tvCost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvCost = (TextView) itemView.findViewById(R.id.tvCost);
            tvCount = (TextView) itemView.findViewById(R.id.tvCount);
            tvMinus = (TextView) itemView.findViewById(R.id.tvMinus);
            tvAdd = (TextView) itemView.findViewById(R.id.tvAdd);
            tvMinus.setOnClickListener(this);
            tvAdd.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // 在 Activity_User_Foodlist中增加数据
            switch (v.getId()){
                case R.id.tvAdd:
                    activity.add(item, true);
                    break;
                case R.id.tvMinus:
                    activity.remove(item, true);
                    break;
                default:
                    break;
            }
        }

        public void bindData(Foods item){
            this.item = item;
            tvName.setText(item.getName());
            tvCost.setText(nf.format(item.count * item.getPrice()));
            tvCount.setText(String.valueOf(item.count));
        }
    }
}

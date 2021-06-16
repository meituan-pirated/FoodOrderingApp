package com.example.foododeringapp.rider.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foododeringapp.bean.FoodRecordForR;
import com.example.foododeringapp.rider.Activity_Order_details;
import com.example.foododeringapp.R;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by xch on 2017/3/9.
 */

public class Adapter_Food_Record extends RecyclerView.Adapter<Adapter_Food_Record.ViewHolder> {
    private ArrayList<FoodRecordForR> dataList;
    private NumberFormat nf;
    private LayoutInflater mInflater;


    public Adapter_Food_Record(Activity_Order_details activity, ArrayList<FoodRecordForR> dataList) {
        this.dataList = dataList;
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_rfood_record, parent, false);
        return new ViewHolder(view);
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FoodRecordForR item = dataList.get(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private FoodRecordForR item;
        private TextView tvCost, tvCount,tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvCost = (TextView) itemView.findViewById(R.id.tvCost);
            tvCount = (TextView) itemView.findViewById(R.id.count);
        }

//        @RequiresApi(api = Build.VERSION_CODES.N)
        public void bindData(FoodRecordForR item) {
            this.item = item;
            tvName.setText(item.getName());
            tvCost.setText(nf.format(item.count * item.getPrice()));
            tvCount.setText("×"+String.valueOf(item.count));
        }
    }
}

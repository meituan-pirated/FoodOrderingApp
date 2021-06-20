package com.example.foododeringapp.user.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foododeringapp.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_LeftRecycle extends RecyclerView.Adapter<Adapter_LeftRecycle.ViewHolder>{
    private Context context;
    private OnItemClickListener mOnItemClickListener = null;
    private List<Boolean> isClicks;//控件是否被点击,默认为false，如果被点击，改变值，控件根据值改变自身颜色

    public Adapter_LeftRecycle(Context context) {
        this.context = context;
        isClicks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            isClicks.add(false);
            if (i == 0) {
                isClicks.set(0, true);
            }
        }
    }

    @NonNull
    @Override
    public Adapter_LeftRecycle.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_left, parent, false);
        Adapter_LeftRecycle.ViewHolder holder = new Adapter_LeftRecycle.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_LeftRecycle.ViewHolder holder, int position) {
        holder.itemView.setTag(holder.left_item);

        if (isClicks.get(position)) {
            holder.left_item.setTextColor(Color.parseColor("#ff0000"));
        } else {
            holder.left_item.setTextColor(Color.parseColor("#000000"));
        }

        holder.left_item.setText("年级" + position);

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> {
                for (int i = 0; i < isClicks.size(); i++) {
                    isClicks.set(i, false);
                }
                isClicks.set(position, true);
                notifyDataSetChanged();
                mOnItemClickListener.onItemClick(holder.itemView, position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView left_item;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            left_item = itemView.findViewById(R.id.left_item);
        }
    }
}

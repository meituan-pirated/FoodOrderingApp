package com.example.foododeringapp.user.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Foods;
import com.example.foododeringapp.bean.Merchant;
import com.example.foododeringapp.user.Activity_User_Foodlist;

import java.text.NumberFormat;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_User_allFoods extends RecyclerView.Adapter<Adapter_User_allFoods.ViewHolder> implements View.OnClickListener  {
    private Adapter_User_allFoods.OnItemClickListener mOnItemClickListener = null;
    private Activity_User_Foodlist mContext;
    private List<Foods> foodsList;
    private NumberFormat nf;

    public Adapter_User_allFoods(List<Foods> foodsList, Activity_User_Foodlist mContext){
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        this.foodsList = foodsList;
        this.mContext = mContext;
    }

//<!--    food_img food_name food_price foods_item_view-->
//<!--    tvMinus count tvAdd-->
    @NonNull
    @Override
    public Adapter_User_allFoods.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_user_foods, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.foods_item_view.setOnClickListener(this);//为每个item商品的前半部分添加点击事件
        holder.tvMinus.setOnClickListener(v -> {//减号
            Activity_User_Foodlist activity = mContext;
            int position = holder.getAdapterPosition();
            Foods foods = foodsList.get(position);
            int count = activity.getSelectedItemCountById(foods.getId());
            if (count <= 1) {
                holder.tvMinus.setAnimation(getHiddenAnimation());
                holder.tvMinus.setVisibility(View.GONE);
                holder.count.setVisibility(View.GONE);
            }
            count--;
            activity.remove(foods, false);
            holder.count.setText(String.valueOf(count));
        });
        holder.tvAdd.setOnClickListener(v -> {//添加商品
            Activity_User_Foodlist activity = mContext;
            int position = holder.getAdapterPosition();
            Foods foods = foodsList.get(position);
            int count = activity.getSelectedItemCountById(foods.getId());
            if (count <= 0) {
                holder.tvMinus.setAnimation(getShowAnimation());
                holder.tvMinus.setVisibility(View.VISIBLE);
                holder.count.setVisibility(View.VISIBLE);
            }
            activity.add(foods, false);

            count++;
            holder.count.setText(String.valueOf(count));
            //首先点击加号图标，拿到控件在屏幕上的绝对坐标，回调activity显示动画
            int[] loc = new int[2];
            v.getLocationInWindow(loc);
            activity.playAnimation(loc);
        });
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Adapter_User_allFoods.ViewHolder holder, int position) {
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.foods_item_view.setTag(position);
        final Foods foods = foodsList.get(position);
        Activity_User_Foodlist activity = mContext;
        foods.count = activity.getSelectedItemCountById(foods.getId());
        holder.food_name.setText(foods.getName());
        holder.food_price.setText(nf.format(foods.getPrice()));
        holder.food_description.setText(foods.getDescriptions());
        holder.count.setText(String.valueOf(foods.count));
        // Glide.with(mContext).load(foods.getImageUrl()).into(holder.food_img);
        if (foods.count < 1) {
            holder.count.setVisibility(View.GONE);
            holder.tvMinus.setVisibility(View.GONE);
        } else {
            holder.count.setVisibility(View.VISIBLE);
            holder.tvMinus.setVisibility(View.VISIBLE);
        }
        Random random=new Random();
        int i = random.nextInt(10);
        holder.item_number_per_month.setText("月售" + i + "单");
        //holder.item_number_per_month.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return foodsList.size();
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    //<!--    food_img food_name food_price foods_item_view-->
//<!--    tvMinus count tvAdd-->
    static class ViewHolder extends RecyclerView.ViewHolder {
        private List<Merchant> foodsList;     //读取到的食物合集

        View foods_item_view;
        TextView food_name, food_price, tvMinus, count, tvAdd, item_number_per_month, food_description;
        CircleImageView food_img;

        public ViewHolder(View itemView) {
            super(itemView);

            foods_item_view = itemView.findViewById(R.id.foods_item_view);
            food_name = itemView.findViewById(R.id.food_name);
            item_number_per_month = itemView.findViewById(R.id.item_number_per_month);
            food_price = itemView.findViewById(R.id.food_price);
            tvMinus = itemView.findViewById(R.id.tvMinus);
            count = itemView.findViewById(R.id.count);
            food_description = itemView.findViewById(R.id.food_description);
            tvAdd = itemView.findViewById(R.id.tvAdd);
            food_img = itemView.findViewById(R.id.food_img);
        }
    }

    /**
     * 显示减号的动画
     */
    private Animation getShowAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }

    /**
     * 隐藏减号的动画
     */
    private Animation getHiddenAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0, 720, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 2f
                , TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }
}

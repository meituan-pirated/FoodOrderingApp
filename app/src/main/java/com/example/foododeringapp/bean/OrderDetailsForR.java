package com.example.foododeringapp.bean;

import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderDetailsForR {
    private Order order;
    /*包括了
    private int order_id;//订单编号
    private int user_id; //用户编号
    private int merchant_id; //商家编号
    private Address address; //地址信息
            *//*包扩了
            private String ReceiveName;//收件人
            private String Sex;        //性别
            private String ReceivePhone;    //联系电话
            private String AddressName;    //收货地址
           *//*
    private String OrderNote; //商品备注
    private String OrderState;//订单状态：待处理，进行中，已完成，已取消
    private String OrderTime;*/

    private String merchant_name;
    private String merchant_add;
    private ArrayList<FoodRecordForR> foodList;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getMerchant_add() {
        return merchant_add;
    }

    public void setMerchant_add(String merchant_add) {
        this.merchant_add = merchant_add;
    }

    public ArrayList<FoodRecordForR> getFoodList() {
        return foodList;
    }

    public void setFoodList(ArrayList<FoodRecordForR> foodList) {
        this.foodList = foodList;
    }
}

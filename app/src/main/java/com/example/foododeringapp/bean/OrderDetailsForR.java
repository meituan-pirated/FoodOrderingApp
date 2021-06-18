package com.example.foododeringapp.bean;

import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderDetailsForR {
    private Order order;
    /*包括了我需要的
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
    private String OrderTime;
    很重要
    private List<OrderDetails> orderDetailsList;
        private int order_details_id;
        private Integer amount;
        private Products product;
            private Integer product_id;
            private String productName;
            private Integer salePrice;


    */
    private BusinessForR businessInfo;


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public BusinessForR getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(BusinessForR businessInfo) {
        this.businessInfo = businessInfo;
    }
}

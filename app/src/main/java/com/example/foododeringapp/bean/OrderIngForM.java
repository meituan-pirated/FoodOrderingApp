package com.example.foododeringapp.bean;

import java.util.List;

public class OrderIngForM {
    private int order_id;//订单编号
//    private int user_id; //用户编号
    private int business_id; //商家编号
//    private int rider_id; //骑手编号

    private String orderNote; //商品备注
    private String orderState;//订单状态：待处理，进行中，已完成，已取消

    private RiderIngForM rider;

//    private Address address; //地址信息

    private List<OrderDetails> orderDetailsList; //订单详细信息

    private String orderPrice;//实付金额
    private String orderTime;
//    private String CommentState;//评论状态

//    private Integer riderScore;
//    private Integer businessScore;


    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public RiderIngForM getRider() {
        return rider;
    }

    public void setRider(RiderIngForM rider) {
        this.rider = rider;
    }

    public List<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
}

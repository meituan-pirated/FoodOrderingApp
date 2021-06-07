package com.example.foododeringapp.bean;

import java.util.List;

public class Order {
    private int order_id;//订单编号
    private int user_id; //用户编号
    private int business_id; //商家编号
    private int rider_id; //骑手编号


    private String OrderNote; //商品备注
    private String OrderState;//订单状态：待处理，进行中，已完成，已取消

    private Address address; //地址信息

    private List<OrderDetails> orderDetailsList; //订单详细信息

    private String OrderPrice;//实付金额
    private String OrderTime;
    private String CommentState;//评论状态

    private Integer riderScore;
    private Integer businessScore;

//    private double Longitude;    //经度
//    private double Latitude;    //纬度


    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(int business_id) {
        this.business_id = business_id;
    }

    public int getRider_id() {
        return rider_id;
    }

    public void setRider_id(int rider_id) {
        this.rider_id = rider_id;
    }

    public String getOrderNote() {
        return OrderNote;
    }

    public void setOrderNote(String orderNote) {
        OrderNote = orderNote;
    }

    public String getOrderState() {
        return OrderState;
    }

    public void setOrderState(String orderState) {
        OrderState = orderState;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }

    public String getOrderPrice() {
        return OrderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        OrderPrice = orderPrice;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public String getCommentState() {
        return CommentState;
    }

    public void setCommentState(String commentState) {
        CommentState = commentState;
    }

    public Integer getRiderScore() {
        return riderScore;
    }

    public void setRiderScore(Integer riderScore) {
        this.riderScore = riderScore;
    }

    public Integer getBusinessScore() {
        return businessScore;
    }

    public void setBusinessScore(Integer businessScore) {
        this.businessScore = businessScore;
    }
}

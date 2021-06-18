package com.example.foododeringapp.bean;

import java.util.List;


public class OrderDoneInfoForM {
    private int order_id;

//    private int user_id; //用户编号

    private List<OrderDetails> orderDetailsList; //订单详细信息

//    private int business_id; //商家编号
//    private int rider_id; //骑手编号

    private Address address; //地址信息

    private Rider rider;

    private String orderPrice;//实付金额
    private String orderTime;
    private String arriveTime;
//    private String CommentState;//评论状态

//    private Integer riderScore;
//    private Integer businessScore;


    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public List<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<OrderDetails> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
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

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }
}

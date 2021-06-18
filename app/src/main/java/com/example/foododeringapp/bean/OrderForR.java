package com.example.foododeringapp.bean;

public class OrderForR {
    private Integer order_id;
    private String order_state;
    private String arrive_time;
    private Integer rider_score;

    private Address address;

    private BusinessForR businessInfo;



    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public BusinessForR getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(BusinessForR businessInfo) {
        this.businessInfo = businessInfo;
    }

    public String getArriveTime() {
        return arrive_time;
    }

    public void setArriveTime(String arriveTime) {
        this.arrive_time = arriveTime;
    }

    public Integer getRiderScore() {
        return rider_score;
    }

    public void setRiderScore(Integer riderScore) {
        this.rider_score = riderScore;
    }
}

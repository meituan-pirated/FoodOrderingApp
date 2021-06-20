package com.example.foododeringapp.bean;

public class OrderForR {
    private Integer order_id;
    private String order_state;
    private String arrive_time;
    private String order_time;
    private Integer rider_score;
    private Integer order_price;


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

    public String getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(String arrive_time) {
        this.arrive_time = arrive_time;
    }

    public Integer getRider_score() {
        return rider_score;
    }

    public void setRider_score(Integer rider_score) {
        this.rider_score = rider_score;
    }

    public Integer getOrder_price() {
        return order_price;
    }

    public void setOrder_price(Integer order_price) {
        this.order_price = order_price;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }
}

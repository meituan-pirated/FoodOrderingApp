package com.example.foododeringapp.bean;

import java.util.List;

//!--    order_id order_note order_state
//        order_address order表中没有，需要向address表根据address_id-->
public class UserOrder {
    private int order_id;//订单编号
    private String order_note; //用户编号
    private String order_state; //商家编号
    private String order_address; //骑手编号
//    private double Longitude;    //经度
//    private double Latitude;    //纬度


    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_note() {
        return order_note;
    }

    public void setOrder_note(String order_note) {
        this.order_note = order_note;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getOrder_address() {
        return order_address;
    }

    public void setOrder_address(String order_address) {
        this.order_address = order_address;
    }
}

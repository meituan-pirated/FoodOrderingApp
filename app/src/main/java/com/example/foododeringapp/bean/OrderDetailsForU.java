package com.example.foododeringapp.bean;

public class OrderDetailsForU {
    private Order order;
    private BusinessForR businessInfo;
    private Rider rider;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public BusinessForR getBusiness() {
        return businessInfo;
    }

    public void setBusiness(BusinessForR business) {
        this.businessInfo = business;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }
}

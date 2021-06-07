package com.example.foododeringapp.bean;

public class OrderDetails {
    private int order_details_id;
    private Integer amount;

    //将order与products的多对多关系，转化成中间表和各自的一对多关系
    //orderDetails为一的一方
    private Products product;

    public int getOrder_details_id() {
        return order_details_id;
    }

    public void setOrder_details_id(int order_details_id) {
        this.order_details_id = order_details_id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }
}

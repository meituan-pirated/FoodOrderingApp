package com.example.foododeringapp.bean;

import java.util.ArrayList;
import java.util.List;

public class OrderStaForR {
    private Integer quantity;//今天的单数
    private Integer income;//今日收入
    private List<OrderForR> finishedOrderList;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public List<OrderForR> getFinishedOrderList() {
        return finishedOrderList;
    }

    public void setFinishedOrderList(List<OrderForR> finishedOrderList) {
        this.finishedOrderList = finishedOrderList;
    }
}

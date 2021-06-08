package com.example.foododeringapp.bean;

public class OrderForR {
    private Order oeder;
    //里面有order_id、OrderState、address（包括String ReceivePhone、String AddressName）
    private String merchant_name;
    private String merchant_add;

    public Order getOeder() {
        return oeder;
    }

    public void setOeder(Order oeder) {
        this.oeder = oeder;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getMerchant_add() {
        return merchant_add;
    }

    public void setMerchant_add(String merchant_add) {
        this.merchant_add = merchant_add;
    }
}

package com.example.foododeringapp.bean;

public class Address {
    private int addressId;
    private int userId;     //用户编号
    private String receiveName;//收件人
    private String sex;        //性别
    private int receivePhone;    //联系电话
    private String addressName;    //收货地址
//    private double Longitude;    //经度
//    private double Latitude;    //纬度

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(int receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }
}

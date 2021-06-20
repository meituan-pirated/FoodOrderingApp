package com.example.foododeringapp.bean;

public class Address {
    private int address_id;
    private int UserId;     //用户编号
    private String receiveName;//收件人
    private String sex;        //性别
    private String receivePhone;    //联系电话
    private String addressName;    //收货地址
//    private double Longitude;    //经度
//    private double Latitude;    //纬度
//   收货地址
//    private double Longitude;    //经度
//    private double Latitude;    //纬度


    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
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

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }
}

package com.example.foododeringapp.bean;

public class UserAddress {
    private int addressId;
    private int UserId;     //用户编号
    private String ReceiveName;//收件人
    private String ReceivePhone;    //联系电话
    private String AddressName;    //收货地址
    private double Longitude;    //经度
    private double Latitude;    //纬度

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getReceiveName() {
        return ReceiveName;
    }

    public void setReceiveName(String receiveName) {
        ReceiveName = receiveName;
    }

    public String getReceivePhone() {
        return ReceivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        ReceivePhone = receivePhone;
    }

    public String getAddressName() {
        return AddressName;
    }

    public void setAddressName(String addressName) {
        AddressName = addressName;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }
}

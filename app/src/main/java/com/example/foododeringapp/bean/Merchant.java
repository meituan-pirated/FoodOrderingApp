package com.example.foododeringapp.bean;

public class Merchant {
    private Integer id;
    private String name;
    private String description;
    private String phoneNumber;
    private String address;
    private String businessImage;//头像

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessImage() {
        return businessImage;
    }

    public void setBusinessImage(String businessImage) {
        this.businessImage = businessImage;
    }
}

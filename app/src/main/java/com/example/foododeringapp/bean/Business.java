package com.example.foododeringapp.bean;

public class Business {
    private Integer business_id;
    private String business_name;
    private String refectory;
    private String descriptions;
    private String business_image;
//    private String Name;
//    private String Email;
//    private String PhoneNumber;
//    private String Address;
//    private String Nickname;
//    private String Sex;
//    private String Avatar;//头像


    public Integer getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(Integer business_id) {
        this.business_id = business_id;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getRefectory() {
        return refectory;
    }

    public void setRefectory(String refectory) {
        this.refectory = refectory;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getBusiness_image() {
        return business_image;
    }

    public void setBusiness_image(String business_image) {
        this.business_image = business_image;
    }
}

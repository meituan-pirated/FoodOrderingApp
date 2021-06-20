package com.example.foododeringapp.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

//将类序列化之后可以在Activity之间传输数据

/**
 * LitePal库对数据库进行增删查改
 */
public class Foods extends LitePalSupport implements Serializable{
    private int id;
    private String name;
    private String imageUrl;//图片
    private double price;//价格
    private String descriptions;//简介
    public int count;//购买数量

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String description) {
        this.descriptions = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

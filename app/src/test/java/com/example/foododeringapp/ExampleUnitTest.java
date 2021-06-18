package com.example.foododeringapp;

import android.util.Log;

import com.example.foododeringapp.bean.Order;
import com.example.foododeringapp.bean.RestFulBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void gson_order_test() throws UnsupportedEncodingException {
        assertEquals(4, 2 + 2);

        String Str = "{'status':0,\"data\":[{\"order_id\":123124,\"user_id\":201821101,\"business_id\":12345678,\"rider_id\":0,'orderNote':'送到楼下',\"orderState\":\"WAIT\",\"address\":{\"address_id\":1231232,\"receiveName\":\"lily\",\"receivePhone\":null,\"addressName\":\"临湖五栋\"},\"orderDetailsList\":[{\"order_details_id\":5680,\"amount\":3,\"product\":{\"product_id\":123123,\"productName\":\"重庆小面\",\"descriptions\":\"重庆特产\",\"salePrice\":5,\"deliveryPrice\":1,\"image\":\"1.png\",\"business\":null,\"attributeList\":null}},{\"order_details_id\":5681,\"amount\":1,\"product\":{\"product_id\":123124,\"productName\":\"酸辣粉\",\"descriptions\":\"重庆特产\",\"salePrice\":5,\"deliveryPrice\":1,\"image\":\"2.png\",\"business\":null,\"attributeList\":null}}],\"orderPrice\":\"30\",\"orderTime\":\"2021-06-01 20:52:26\",\"riderScore\":null,\"businessScore\":null},{\"order_id\":123125,\"user_id\":2018211302,\"business_id\":12345678,\"rider_id\":0,\"orderNote\":null,\"orderState\":\"WAIT\",\"address\":{\"address_id\":1231231,\"receiveName\":\"lily\",\"receivePhone\":null,\"addressName\":\"临湖四栋\"},\"orderDetailsList\":[{\"order_details_id\":5682,\"amount\":3,\"product\":{\"product_id\":123123,\"productName\":\"重庆小面\",\"descriptions\":\"重庆特产\",\"salePrice\":5,\"deliveryPrice\":1,\"image\":\"1.png\",\"business\":null,\"attributeList\":null}},{\"order_details_id\":5683,\"amount\":1,\"product\":{\"product_id\":123124,\"productName\":\"酸辣粉\",\"descriptions\":\"重庆特产\",\"salePrice\":5,\"deliveryPrice\":1,\"image\":\"2.png\",\"business\":null,\"attributeList\":null}}],\"orderPrice\":\"12\",\"orderTime\":\"2021-06-01 20:52:26\",\"riderScore\":null,\"businessScore\":null}],\"msg\":\"订单列表\"}";
                String jsonStr = new String(Str.getBytes(), "UTF-8");
        RestFulBean<List<Order>> test = new Gson().fromJson(jsonStr, new TypeToken<RestFulBean<List<Order>>>() {
        }.getType());

//        Log.i("test:",test.getData().get(0).getOrderNote());
        System.out.println("test orderNote:" + test.getData().get(0).getOrderNote());
        System.out.println("test address:" + test.getData().get(0).getAddress().getReceiveName());
        System.out.println("test orderPrice:" + test.getData().get(0).getOrderPrice());

        System.out.println("test orderDetails0:" + test.getData().get(0).getOrderDetailsList().get(0).getProduct().getProductName());
        System.out.println("test orderDetails1:" + test.getData().get(0).getOrderDetailsList().get(1).getProduct().getProductName());


    }

    @Test
    public void gson_change_test(){
        String postForm = "{\"status\":0,\"data\":\"\",\"msg\":\"成功修改订单状况\"}";
        Gson gson = new Gson();
        RestFulBean<String> restFulBean = gson.fromJson(postForm, new TypeToken<RestFulBean<String>>() {
        }.getType());

        System.out.println("test msg: "+restFulBean.getMsg());
    }
}
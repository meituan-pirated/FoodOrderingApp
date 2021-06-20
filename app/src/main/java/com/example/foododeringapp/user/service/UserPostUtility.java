package com.example.foododeringapp.user.service;

import com.example.foododeringapp.bean.RestFulBean;
import com.example.foododeringapp.bean.orderProducts;
import com.example.foododeringapp.service.RequestManager;
import com.example.foododeringapp.service.Requests;
import com.example.foododeringapp.util.Util;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class UserPostUtility {
    private static RequestManager requestManager = new Requests();
    private static String requestUrl = Util.Url;


    /**
     * 插入订单
     *insertOrder(userID, merchantID, addressID, orderNote, order_price, remarkContent, orderState, getTime(),selectedList);
     * @return
     */
  /*  public static int insertOrder(int userID, int merchantID, int addressID, String orderNote, double order_price, String remarkContent, String orderState, String orderTime, List<orderProducts> selectedList){
        String postForm = null;
        try{
            postForm = requestManager.postForm(requestUrl+"UserOpera/insertOrder","userID",userID+"",
                    "merchantID",merchantID+"","addressID",addressID+"","orderNote",orderNote,"orderPrice",
                    order_price+"","remarkContent",remarkContent,"orderState",orderState,"orderTime",orderTime,
                    "selectedList",selectedList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return postForm;
    }*/
}

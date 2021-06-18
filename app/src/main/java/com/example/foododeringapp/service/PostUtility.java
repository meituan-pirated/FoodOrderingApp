package com.example.foododeringapp.service;

import com.example.foododeringapp.bean.Login;
import com.example.foododeringapp.bean.Order;
import com.example.foododeringapp.bean.OrderDetailsForR;
import com.example.foododeringapp.bean.OrderForR;
import com.example.foododeringapp.bean.OrderStaForR;
import com.example.foododeringapp.bean.RestFulBean;
import com.example.foododeringapp.bean.Rider;
import com.example.foododeringapp.util.Util;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过ruquestUrl获取数据
 *      其中requestManager接口通过requests类的实现处理了后端传来的数据
 *
 */
public class PostUtility {
    private static RequestManager requestManager = new Requests();

    private static List<Order> ordersList = new ArrayList<>();
    private static List<OrderForR> ordersListForR = new ArrayList<>();
    private static OrderDetailsForR orderDetailsForR;
    private static Integer res;
    private static OrderStaForR orderStaInfo;
    private static Rider rider;

    private static String requestUrl = Util.Url;
    private  static Login login;

    public static String LoginByPhone(String phone, String pwd, Integer type){
        String postForm = null;
        try{
            postForm = requestManager.postForm(requestUrl+"LoginOpera/loginByPhone","phone",phone,
            "password",pwd,"user_type",type+"");
        }catch (Exception e){
            e.printStackTrace();
        }
        return postForm;
    }

    public static String LoginById(String id, String pwd, Integer type){
        String postForm = null;
        Integer user_id = Integer.valueOf(id);
        try{
            postForm = requestManager.postForm(requestUrl+"LoginOpera/loginById","user_id",user_id+"",
                    "password",pwd,"user_type",type+"");
        }catch (Exception e){
            e.printStackTrace();
        }
        return postForm;
    }








//    /**
//     * 获取待处理订单
//     * @param UserId
//     * @return
//     */
//    public static List<Order> getWaitOrders(String UserId) {
//        try {
//            ordersList = requestManager.request(requestUrl + "MerchantOpera/WaitOrders?userId=" + UserId, new TypeToken<List<Order>>() {
//            }.getType());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ordersList;
//    }
}

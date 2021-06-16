package com.example.foododeringapp.rider.service;

import com.example.foododeringapp.bean.Order;
import com.example.foododeringapp.bean.OrderDetailsForR;
import com.example.foododeringapp.bean.OrderForR;
import com.example.foododeringapp.bean.RestFulBean;
import com.example.foododeringapp.service.RequestManager;
import com.example.foododeringapp.service.Requests;
import com.example.foododeringapp.util.Util;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class RiderRequestUtility {
    private static RequestManager requestManager = new Requests();

    private static String requestUrl = Util.Url;

    private static List<OrderForR> ordersListForR = new ArrayList<>();
    private static OrderDetailsForR orderDetailsForR;

    public static List<Order> getRiderNewOrders(String userId){
        return null;
    }

    public static List<OrderForR> getUnderwayOrdersForR(String rider_id) {
        try {
            RestFulBean<List<OrderForR>> restFulBean = requestManager.request(requestUrl + "RiderOpera/getUnderwayOrderList?rider_id=" + rider_id, new TypeToken<RestFulBean<List<OrderForR>>>() {
            }.getType());
            ordersListForR = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ordersListForR;
    }

    public static OrderDetailsForR getOrderDetailsForR(String order_id) {
//        try {
//            OrderDetailsForR restFulBean = requestManager.request(requestUrl + "RiderOpera/getOrderDetails?order_id=" + order_id, new TypeToken<RestFulBean<OrderDetailsForR>>() {
//            }.getType());
//            orderDetailsForR = restFulBean.getData();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return orderDetailsForR;

        return null;
    }
}

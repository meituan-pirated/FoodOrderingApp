package com.example.foododeringapp.user.service;

import com.example.foododeringapp.bean.OrderDetailsForR;
import com.example.foododeringapp.bean.OrderDetailsForU;
import com.example.foododeringapp.bean.OrderForR;
import com.example.foododeringapp.bean.OrderStaForR;
import com.example.foododeringapp.bean.RestFulBean;
import com.example.foododeringapp.bean.Rider;
import com.example.foododeringapp.service.Requests;
import com.example.foododeringapp.util.Util;
import com.google.gson.reflect.TypeToken;
import com.example.foododeringapp.service.RequestManager;

import java.util.ArrayList;
import java.util.List;

public class UserRequestUtility {
    private static RequestManager requestManager = new Requests();
    private static String requestUrl = Util.Url;
    private static Integer res;
    private static List<OrderForR> ordersListForR = new ArrayList<>();
    private static OrderDetailsForU orderDetailsForU;

    public static List<OrderForR> getIngOrdersForU(String user_id) {
        try {
            RestFulBean<List<OrderForR>> restFulBean = requestManager.request(requestUrl + "UserOpera/getIngOrderList?user_id=" + user_id, new TypeToken<RestFulBean<List<OrderForR>>>() {
            }.getType());
            ordersListForR = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ordersListForR;
    }

    public static List<OrderForR> getFinishedOrdersForU(String user_id) {
        try {
            RestFulBean<List<OrderForR>> restFulBean = requestManager.request(requestUrl + "UserOpera/getFinishedOrderList?user_id=" + user_id, new TypeToken<RestFulBean<List<OrderForR>>>() {
            }.getType());
            ordersListForR = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ordersListForR;
    }

    public static OrderDetailsForU getOrderDetailsForU(String order_id) {

        try {
            RestFulBean<OrderDetailsForU> restFulBean= requestManager.request(requestUrl + "UserOpera/getOrderDetails?order_id=" + order_id, new TypeToken<RestFulBean<OrderDetailsForU>>() {
            }.getType());
            orderDetailsForU = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderDetailsForU;

    }
}

package com.example.foododeringapp.service;

import com.example.foododeringapp.bean.Order;
import com.example.foododeringapp.bean.OrderDetailsForR;
import com.example.foododeringapp.bean.OrderForR;
import com.example.foododeringapp.bean.RestFulBean;
import com.example.foododeringapp.util.Util;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过ruquestUrl获取数据
 *      其中requestManager接口通过requests类的实现处理了后端传来的数据
 *
 */
public class RequestUtility {
    private static RequestManager requestManager = new Requests();

    private static List<Order> ordersList = new ArrayList<>();
    private static List<OrderForR> ordersListForR = new ArrayList<>();
    private static OrderDetailsForR orderDetailsForR;

    private static String requestUrl = Util.Url;


    /**
     * 获取全部待处理订单
     *
     * @param business_id
     * @return
     */
    public static List<Order> getWaitOrders(Integer business_id) {
        try {
            RestFulBean<List<Order>> restFulBean = requestManager.request(requestUrl + "MerchantOpera/getWaitOrderList?business_id=" + business_id, new TypeToken<RestFulBean<List<Order>>>() {
            }.getType());
            ordersList = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ordersList;
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
        try {
            OrderDetailsForR restFulBean = requestManager.request(requestUrl + "RiderOpera/getOrderDetails?order_id=" + order_id, new TypeToken<RestFulBean<OrderDetailsForR>>() {
            }.getType());
            orderDetailsForR = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderDetailsForR;
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

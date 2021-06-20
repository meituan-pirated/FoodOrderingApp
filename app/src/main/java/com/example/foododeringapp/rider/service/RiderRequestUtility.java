package com.example.foododeringapp.rider.service;

import com.example.foododeringapp.bean.Order;
import com.example.foododeringapp.bean.OrderDetailsForR;
import com.example.foododeringapp.bean.OrderForR;
import com.example.foododeringapp.bean.OrderStaForR;
import com.example.foododeringapp.bean.RestFulBean;
import com.example.foododeringapp.bean.Rider;
import com.example.foododeringapp.service.RequestManager;
import com.example.foododeringapp.service.Requests;
import com.example.foododeringapp.util.Util;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class RiderRequestUtility {
    private static RequestManager requestManager = new Requests();

    private static String requestUrl = Util.Url;
    private static Rider rider;
    private static Integer res;
    private static OrderStaForR orderSta;
    private static List<OrderForR> ordersListForR = new ArrayList<>();
    private static OrderDetailsForR orderDetailsForR;

    public static List<OrderForR> getNewOrdersForR(){

        try {
            RestFulBean<List<OrderForR>> restFulBean = requestManager.request(requestUrl + "RiderOpera/getNewOrderList", new TypeToken<RestFulBean<List<OrderForR>>>() {
            }.getType());
            ordersListForR = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ordersListForR;
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
            RestFulBean<OrderDetailsForR> restFulBean= requestManager.request(requestUrl + "RiderOpera/getOrderDetails?order_id=" + order_id, new TypeToken<RestFulBean<OrderDetailsForR>>() {
            }.getType());
            orderDetailsForR = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderDetailsForR;

    }

    public static Rider GetRiderInfo(String rider_id){
        try {
            RestFulBean<Rider> restFulBean= requestManager.request(requestUrl + "RiderOpera/getRiderInfo?rider_id=" + rider_id, new TypeToken<RestFulBean<Rider>>() {
            }.getType());
            rider = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rider;
    }



    public static OrderStaForR GetOrderStaInfo(String rider_id) {
        try {
            RestFulBean<OrderStaForR> restFulBean= requestManager.request(requestUrl + "RiderOpera/getOrderSta?rider_id=" + rider_id, new TypeToken<RestFulBean<OrderStaForR>>() {
            }.getType());
            orderSta = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderSta;
    }


    public static Integer AcceptOrder(String order_id, String rider_id) {
        try {
            RestFulBean<Integer> restFulBean= requestManager.request(requestUrl + "RiderOpera/acceptOrder?order_id=" + order_id + "&rider_id="+rider_id, new TypeToken<RestFulBean<Integer>>() {
            }.getType());
            res = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Integer ChangeOrderStateForR(String order_id, String next_state) {
        try {
            RestFulBean<Integer> restFulBean= requestManager.request(requestUrl + "RiderOpera/changeOrderState?order_id=" + order_id + "&next_state="+next_state, new TypeToken<RestFulBean<Integer>>() {
            }.getType());
            res = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Integer MarkOrderArriveTime(String order_id) {
        try {
            RestFulBean<Integer> restFulBean= requestManager.request(requestUrl + "RiderOpera/markOrderArrive?order_id=" + order_id, new TypeToken<RestFulBean<Integer>>() {
            }.getType());
            res = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


}

package com.example.foododeringapp.merchant.service;

import android.util.Log;

import com.example.foododeringapp.bean.Business;
import com.example.foododeringapp.bean.Order;
import com.example.foododeringapp.bean.OrderDoneBriefForM;
import com.example.foododeringapp.bean.OrderDoneInfoForM;
import com.example.foododeringapp.bean.OrderIngForM;
import com.example.foododeringapp.bean.ProductBriefForM;
import com.example.foododeringapp.bean.Products;
import com.example.foododeringapp.bean.RestFulBean;
import com.example.foododeringapp.service.RequestManager;
import com.example.foododeringapp.service.Requests;
import com.example.foododeringapp.util.Util;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过ruquestUrl获取数据
 *      其中requestManager接口通过requests类的实现处理了后端传来的数据
 *
 */
public class MerchantRequestUtility {
    private static RequestManager requestManager = new Requests();

    private static String requestUrl = Util.Url;

    private static List<Order> ordersList = new ArrayList<>();
    private static List<OrderIngForM> ordersForIngList = new ArrayList<>();
    private static List<OrderDoneBriefForM> ordersDoneForMList = new ArrayList<>();
    private static OrderDoneInfoForM orderDoneInfoForM = new OrderDoneInfoForM();
    private static List<ProductBriefForM> productBriefForMList = new ArrayList<>();
    private static Business business = new Business();
    private static Products products = new Products();




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


    /**
     * 获取全部进行中订单
     *
     * @param business_id
     * @return
     */
    public static List<OrderIngForM> getIngOrders(Integer business_id) {
        try {
            RestFulBean<List<OrderIngForM>> restFulBean = requestManager.request(requestUrl + "MerchantOpera/getIngOrderList?business_id=" + business_id, new TypeToken<RestFulBean<List<OrderIngForM>>>() {
            }.getType());
            ordersForIngList = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ordersForIngList;
    }

    /**
     * 获取全部进行中订单
     *
     * @param business_id
     * @return
     */
    public static List<OrderDoneBriefForM> getDoneOrders(Integer business_id) {
        try {
            RestFulBean<List<OrderDoneBriefForM>> restFulBean = requestManager.request(requestUrl + "MerchantOpera/getDoneBriefOrderList?business_id=" + business_id, new TypeToken<RestFulBean<List<OrderDoneBriefForM>>>() {
            }.getType());
            ordersDoneForMList = restFulBean.getData();
            Log.i("done",ordersDoneForMList.get(0).getOrderPrice()+"");
//            Log.i("rider", ordersForIngList.get(0).getRider().getRider_name());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ordersDoneForMList;
    }

    public static OrderDoneInfoForM getOrderDoneInfo(Integer order_id){
        try {
            RestFulBean<OrderDoneInfoForM> restFulBean = requestManager.request(requestUrl + "MerchantOpera/getDoneInfo?order_id=" + order_id, new TypeToken<RestFulBean<OrderDoneInfoForM>>() {
            }.getType());
            orderDoneInfoForM = restFulBean.getData();
//            Log.i("done",ordersDoneForMList.get(0).getOrderPrice()+"");
//            Log.i("rider", ordersForIngList.get(0).getRider().getRider_name());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderDoneInfoForM;
    }

    /**
     * 获取商品简介列表
     * @param business_id
     * @return
     */
    public static List<ProductBriefForM> getProductForM(Integer business_id){
        try{
            RestFulBean<List<ProductBriefForM>> restFulBean = requestManager.request(requestUrl + "MerchantOpera/getProductBriefForMList?business_id=" + business_id, new TypeToken<RestFulBean<List<ProductBriefForM>>>() {
            }.getType());
            productBriefForMList = restFulBean.getData();
        }catch (Exception e){
            e.printStackTrace();
        }
        return productBriefForMList;

    }


    /**
     * 获取商品简介列表
     * @param product_id
     * @return
     */
    public static Products getProductInfo(Integer product_id){
        try{
            RestFulBean<Products> restFulBean = requestManager.request(requestUrl + "MerchantOpera/getProductInfo?product_id=" + product_id, new TypeToken<RestFulBean<Products>>() {
            }.getType());
            products = restFulBean.getData();
        }catch (Exception e){
            e.printStackTrace();
        }
        return products;

    }


    /**
     * 获取店铺信息
     * @param business_id
     * @return
     */
    public static Business getBusinessInfo(Integer business_id){
        try{
            RestFulBean<Business> restFulBean = requestManager.request(requestUrl + "MerchantOpera/getBusiness?business_id=" + business_id, new TypeToken<RestFulBean<Business>>() {
            }.getType());
            business= restFulBean.getData();
        }catch (Exception e){
            e.printStackTrace();
        }
        return business;
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

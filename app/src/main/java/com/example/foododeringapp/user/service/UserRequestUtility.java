package com.example.foododeringapp.user.service;

import com.example.foododeringapp.bean.Address;

import com.example.foododeringapp.bean.Foods;
import com.example.foododeringapp.bean.Merchant;
import com.example.foododeringapp.bean.OrderDetailsForU;
import com.example.foododeringapp.bean.OrderForR;
import com.example.foododeringapp.bean.RestFulBean;
import com.example.foododeringapp.bean.User;
import com.example.foododeringapp.bean.UserOrder;
import com.example.foododeringapp.bean.orderProducts;
import com.example.foododeringapp.service.RequestManager;
import com.example.foododeringapp.service.Requests;
import com.example.foododeringapp.util.Util;
import com.google.gson.reflect.TypeToken;

import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.List;

public class UserRequestUtility {
    /**
     *      通过ruquestUrl获取数据
     *      其中requestManager接口通过requests类的实现处理了后端传来的数据
     *
     */
    private static Boolean resultOrder;
    private static int result;
    private static User userInfo;
    private static Address address;

    private static List<Merchant> allMerchantsList = new ArrayList<>();
    private static List<Foods> allFoodsList = new ArrayList<>();
    private static List<Address> allAddressList = new ArrayList<>();
    private static List<UserOrder> allUserOrders = new ArrayList<>();
    private static ArrayList<Foods> selectedList;

    private static RequestManager requestManager = new Requests();
    private static String requestUrl = Util.Url;

    /**
     * 获取全部商家
     *
     * @return
     */
    public static List<Merchant> getAllMerchants() {
        try {
            RestFulBean<List<Merchant>> restFulBean = requestManager.request(requestUrl + "UserOpera/getAllMerchants", new TypeToken<RestFulBean<List<Merchant>>>() {
            }.getType());
            allMerchantsList = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allMerchantsList;
    }


    public static List<Merchant> getMerchantsByLook(String keyword) {
        try {
            RestFulBean<List<Merchant>> restFulBean = requestManager.request(requestUrl + "UserOpera/getMerchantsByLook?keyword="+keyword, new TypeToken<RestFulBean<List<Merchant>>>() {
            }.getType());
            allMerchantsList = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allMerchantsList;

    }

    /**
     * 获取用户全部订单
     *
     * @return
     */
    public static List<UserOrder> getAllUserOrders(int userID){
        try{
            RestFulBean<List<UserOrder>> restFulBean = requestManager.request(requestUrl + "UserOpera/getAllUserOrders?user_id=" + userID, new TypeToken<RestFulBean<List<Foods>>>() {
            }.getType());
            allUserOrders = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allUserOrders;
    }

    /**
     * 获取商家全部商品
     *
     * @return
     */
    public static List<Foods> getAllFoods(int merchant_id) {
        try {
            RestFulBean<List<Foods>> restFulBean = requestManager.request(requestUrl + "UserOpera/getAllFoods?merchant_id=" + merchant_id, new TypeToken<RestFulBean<List<Foods>>>() {
            }.getType());
            allFoodsList = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allFoodsList;
    }

    /**
     * 根据用户ID查找全部地址
     *
     * @return
     */
    public static List<Address> getAddressListById(int userID){
        try{
            RestFulBean<List<Address>> restFulBean = requestManager.request(requestUrl + "UserOpera/getAddressListById?userID=" + userID, new TypeToken<RestFulBean<List<Address>>>() {
            }.getType());
            allAddressList = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allAddressList;
    }

    /**
     * 插入订单
     *insertOrder(userID, merchantID, addressID, orderNote, order_price, remarkContent, orderState, getTime(),selectedList);
     * @return
     */
    public static int insertOrder(int userID, int merchantID, int addressID, String orderNote, double order_price, String remarkContent, String orderState, String orderTime){
        try{
            RestFulBean<Integer> restFulBean = requestManager.request(requestUrl + "UserOpera/insertOrder?userID="+userID
                    +"&merchantID="+merchantID+"&addressID="+addressID+"&orderNote="+orderNote+"&orderPrice="+order_price
                    +"&remarkContent=" +remarkContent +"&orderState="+orderState+"&orderTime=" +orderTime
                    , new TypeToken<RestFulBean<Integer>>() {}.getType());
            result = restFulBean.getData();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int insertOrderDetails(int order_id, int product_id, int amount){
        try{
            RestFulBean<Integer> restFulBean = requestManager.request(requestUrl + "UserOpera/insertOrderDetails?order_id="+
            order_id + "&product_id="+ product_id+"&amount="+amount, new TypeToken<RestFulBean<Integer>>() {}.getType());
            result = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据用户ID获取用户信息
     *
     * @return
     */
    public static User getUserInfoByID(int userID){
        try{
            RestFulBean<User> restFulBean = requestManager.request(requestUrl + "UserOpera/getUserInfoByID?userID=" + userID, new TypeToken<RestFulBean<User>>() {
            }.getType());
            userInfo = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    /**
     * 根据地址ID获取地址
     *
     * @return
     */
    public static Address getAddressByID(int addressID){
        try{
            RestFulBean<Address> restFulBean = requestManager.request(requestUrl + "UserOpera/getAddressByID?addressID=" + addressID, new TypeToken<RestFulBean<Address>>() {
            }.getType());
            address = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }

    /**
     * 根据用户ID获取用户全部地址
     *
     * @return
     */
    public static List<Address> getAllAddress(int userID){
        try{
            RestFulBean<List<Address>> restFulBean = requestManager.request(requestUrl + "UserOpera/getAllAddress?userID=" + userID, new TypeToken<RestFulBean<List<Address>>>() {
            }.getType());
            allAddressList = restFulBean.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allAddressList;
    }

    /**
     * 增加地址
     *
     * @return
     */
    public static int addAddress(int userId, String receiveName, String sex, String receivePhone, String addressName){
        try{
            RestFulBean<Integer> restFulBean = requestManager.request(requestUrl + "UserOpera/addAddress?userId="+userId
                            +"&receiveName="+receiveName+"&sex="+sex+"&receivePhone="+receivePhone+"&addressName="+addressName
                    , new TypeToken<RestFulBean<Integer>>() {}.getType());
            result = restFulBean.getData();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 修改地址
     *
     * @return
     */
    public static int editAddress(int addressID, String receiveName, String sex, String receivePhone, String addressName){
        try{
            RestFulBean<Integer> restFulBean = requestManager.request(requestUrl + "UserOpera/editAddress?addressID="+addressID
                            +"&receiveName="+receiveName+"&sex="+sex+"&receivePhone="+receivePhone+"&addressName="+addressName
                    , new TypeToken<RestFulBean<Integer>>() {}.getType());
            result = restFulBean.getData();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除地址
     *
     * @return
     */
    public static int deleteUserAddress(int addressID){
        try{
            RestFulBean<Integer> restFulBean = requestManager.request(requestUrl + "UserOpera/deleteUserAddress?addressID="+addressID, new TypeToken<RestFulBean<Integer>>() {}.getType());
            result = restFulBean.getData();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

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

